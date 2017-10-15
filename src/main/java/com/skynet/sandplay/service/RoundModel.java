package com.skynet.sandplay.service;

import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.service.interfaces.ILoanService;

/**
 * 每轮数据计算模型
 * @author air
 *
 */
public class RoundModel {

	private Round round;//本轮数据
	private Round oldRound;//上轮数据
	private RoundSet roundSet;//本轮价格
	private RoundSet nextRoundSet;//下轮价格
	private RoundForm req;//用户提交数据
	private BaseMsg msg;//请求信息封装
	private ILoanService loanService;
	private LoanCash loanCash = new LoanCash();
	StringBuffer errMsg = new StringBuffer();
	
	public RoundModel(Round round, Round oldRound, 
			RoundSet roundSet, RoundSet nextRoundSet, 
			RoundForm req, BaseMsg msg, ILoanService loanService) {
		this.round = round;
		this.oldRound = oldRound;
		this.roundSet = roundSet;
		this.nextRoundSet = nextRoundSet;
		this.req = req;
		this.msg = msg;
		this.loanService = loanService;
	}
	
	//计算主流程
	public String process() {
		
		if(!checkForm()) {
			return errMsg.toString();
		}
		
		//家庭收入结余：本轮是否失业
		round.setSurplus(roundSet.isLostJob() ? -12:20);
		
		//注入基础信息
		setRoundBase();
		
		//计算当前总值
		setRoundValue();
		
		//记录流入流出
		setRoundChange();
		
		//记录资产配置数量
		setRoundNum();
		
		//计算配置后总值
		setRoundValueAfter();
		
		
		//计算贷款(要放在现金、资产、负债前计算)
		loanService.handleRoundLoan(round, oldRound, req, roundSet, loanCash);

		//计算利息支出
		setRoundInterest();
		
		//计算现金
		setRoundCash();
		
		//计算资产
		setRoundAsset();
		
		//计算总负债
		double debt = round.getCreditLoan() + round.getHouseLoan() + round.getLandLoan();
		round.setTotalDebt(debt);
		
		//计算净资产
		double netAsset = round.getTotalAsset() - debt;
		round.setNetAsset(netAsset);
		double netAssetAfter = round.getTotalAssetAfter() - debt;
		round.setNetAssetAfter(netAssetAfter);
		
		round.setDeptToAsset(round.getNetAssetAfter()/round.getTotalAssetAfter());
		
		checkAfter();
		
		return errMsg.length() > 0?errMsg.toString():null;
	}
	
	private boolean checkForm() {
		double bankLimit = oldRound.getNetAssetAfter() * 0.1;
		if(bankLimit > 100) {
			bankLimit = 100;
		}
		if(req.getCreditLoanChange() != null) {
			if((req.getCreditLoanChange() + oldRound.getCreditLoan()) > bankLimit) {
				errMsg.append("你已超出银行授信额度，当前额度为：" + bankLimit);
				return false;
			}
		}
		return true;
	}
	/**
	 * 最后的检查
	 */
	private void checkAfter() {
		//净总比
		if(round.getDeptToAsset() < 0.5) {
			errMsg.append("净总比不能小于50%，当前配置净总比：" + (round.getDeptToAsset()*100 + "%"));
		}
		
	}
	
	/**
	 * 计算资产
	 */
	private void setRoundAsset() {
		double asset = round.getCash()
				+ round.getInsure() 
				+ round.getBank()
				+ round.getGold()
				+ round.getHk()
				+ round.getEtf()
				+ round.getPufa()
				+ round.getTrust()
				+ round.getHouse()
				+ round.getLand()
				+ (roundSet.isLostJob()?0:20);
		
		round.setTotalAsset(asset);
		
		//计算总资产
		double assetAfter = round.getCashAfter() 
				+ round.getInsureAfter() 
				+ round.getBankAfter()
				+ round.getGoldAfter()
				+ round.getHkAfter()
				+ round.getEtfAfter()
				+ round.getPufaAfter()
				+ round.getTrustAfter()
				+ round.getHouseAfter()
				+ round.getLandAfter()
				+ (nextRoundSet.isLostJob() ? 0:20)
				;
		round.setTotalAssetAfter(assetAfter);
	}
	
	/**
	 * 计算现金
	 */
	private void setRoundCash() {
		//计算其他资产的总值
		double newCashNow = oldRound.getCashAfter();
		
		//现金余额计算：把所有的买入卖出统计
//		newCashNow += round.getSurplus();
		newCashNow -= req.getInsureChange() != null?req.getInsureChange():0;
		newCashNow -= req.getBankChange() != null?req.getBankChange():0;
		newCashNow -= req.getGoldChange() != null?req.getGoldChange():0;
		newCashNow -= req.getHkChange() != null?req.getHkChange():0;
		newCashNow -= req.getEtfChange() != null?req.getEtfChange():0;
		newCashNow -= req.getPufaChange() != null?req.getPufaChange():0;
		newCashNow -= req.getTrustChange() != null?req.getTrustChange():0;
		if(req.getHouseChange() != null){
			if(req.getHouseChange() > 0) {
				newCashNow -= req.getHouseChange()*roundSet.getHousePayPrice();
			} else {
				newCashNow -= req.getHouseChange()*roundSet.getHousePrice();
				//还贷款
				newCashNow -= loanCash.houseCash;
			}
		}
		if(req.getLandChange() != null) {
			if(req.getLandChange() > 0) {
				newCashNow -= req.getLandChange()*roundSet.getLandPayPrice();
			} else {
				newCashNow -= req.getLandChange()*roundSet.getLandPrice();
				//还贷款
				newCashNow -= loanCash.landCash;
			}
		}
		
		//信用贷款新增
		newCashNow += req.getCreditLoanChange() != null?req.getCreditLoanChange():0;

		round.setCashChange(newCashNow - oldRound.getCashAfter());
		round.setCash(newCashNow);
		
		double newCash = newCashNow;
		
		newCash += nextRoundSet.isLostJob()?0:20;
		
		//住宅利息支出
		newCash -= round.getHouseInterestAfter();
		//商业用地利息支出
		newCash -= round.getLandInterestAfter();
		//信用贷款利息支出
		newCash -= round.getCreditLoanInterestAfter();
		//商业地产租金收入
		newCash += round.getRent();
		
		round.setCashAfter(newCash);
	}
	
	/**
	/信用贷款利息支出：信用贷款*信用贷款利率
	**/
	private void setRoundInterest() {
		round.setCreditLoanInterest(oldRound.getCreditLoanInterestAfter());
		double creditLoanInterest = round.getCreditLoan()*roundSet.getCreditRate();
		round.setCreditLoanInterestAfter(creditLoanInterest);
		round.setCreditLoanInterestSurplus(round.getCreditLoanInterestAfter() + creditLoanInterest);
		
		//房贷利息支出：房贷*房贷利率
		round.setHouseInterest(oldRound.getHouseInterestAfter());
		double houseInterest = round.getHouseLoan()*roundSet.getHouseRate();
		round.setHouseInterestAfter(houseInterest);
		round.setHouseInterestSurplus(round.getHouseInterestAfter() + houseInterest);
		
		round.setLandInterest(oldRound.getLandInterestAfter());
		double landInterest = round.getLandLoan()*roundSet.getLandRate();
		round.setLandInterestAfter(landInterest);
		round.setLandInterestSurplus(round.getLandInterestAfter() + landInterest);
		
		round.setRent(round.getLand()*roundSet.getRentRate());
		
	}
	
	/**
	 * 初始化基本信息
	 */
	private void setRoundBase() {
		round.setUserId(msg.userId);
		round.setUserName(msg.userName);
		round.setRound(req.getRound());
		round.setStatus(1);//锁定
		round.setGrade(RoundPlay.currentGrade);
	}
	
	/**
	 * 通过流入流出计算当前各项流动资产的总值
	 */
	private void setRoundValue() {
		round.setInsure(oldRound.getInsureAfter() + (req.getInsureChange() != null?req.getInsureChange():0));
		round.setBank(oldRound.getBankAfter() + (req.getBankChange() != null?req.getBankChange():0));
		round.setGold(oldRound.getGoldAfter() + (req.getGoldChange() != null?req.getGoldChange():0));
		round.setHk(oldRound.getHkAfter() + (req.getHkChange()!=null?req.getHkChange():0));
		round.setEtf(oldRound.getEtfAfter() + (req.getEtfChange()!=null?req.getEtfChange():0));
		round.setPufa(oldRound.getPufaAfter() + (req.getPufaChange()!=null?req.getPufaChange():0));
		round.setTrust(oldRound.getTrustAfter() + (req.getTrustChange()!=null?req.getTrustChange():0));
		//信用贷款
		round.setCreditLoan(oldRound.getCreditLoan() + (req.getCreditLoanChange() != null?req.getCreditLoanChange():0));
		
		//通过流动的首付款计算固定资产总值，要注意卖出的时候要还清贷款
		//变动的数量
		double houseChange = (int) (req.getHouseChange()!=null?req.getHouseChange()*roundSet.getHousePrice():0);
		double landChange = (int) (req.getLandChange()!=null?req.getLandChange()*roundSet.getLandPrice():0);
		//计算总值
		round.setHouse(oldRound.getHouseAfter() + houseChange);
		round.setLand(oldRound.getLandAfter() + landChange);
	}
	
	private void setRoundValueAfter() {
		if(nextRoundSet != null) {
			//计算下一轮变动后的总值
			round.setInsureAfter(round.getInsureNumAfter() * nextRoundSet.getInsurePrice());
			round.setBankAfter(round.getBankNumAfter() * nextRoundSet.getBankPrice());
			round.setGoldAfter(round.getGoldNumAfter() * nextRoundSet.getGoldPrice());
			round.setHkAfter(round.getHkNumAfter() * nextRoundSet.getHkPrice());
			round.setEtfAfter(round.getEtfNumAfter() * nextRoundSet.getEtfPrice());
			round.setPufaAfter(round.getPufaNumAfter() * nextRoundSet.getPufaPrice());
			round.setTrustAfter(round.getTrustNumAfter() * nextRoundSet.getTrustPrice());
			round.setHouseAfter(round.getHouseNumAfter() * nextRoundSet.getHousePrice());
			round.setLandAfter(round.getLandNumAfter() * nextRoundSet.getLandPrice());
		} else {
			//如果没有设置下一轮的价格，就取本轮数据
			round.setInsureAfter(round.getInsure());
			round.setBankAfter(round.getBank());
			round.setGoldAfter(round.getGold());
			round.setHkAfter(round.getHk());
			round.setEtfAfter(round.getEtf());
			round.setPufaAfter(round.getPufa());
			round.setTrustAfter(round.getTrust());
			round.setHouseAfter(round.getHouse());
			round.setLandAfter(round.getLand());
		}
	}
	
	/**
	 * 计算各项资产的数量
	 */
	private void setRoundNum() {
		round.setInsureNum(oldRound.getInsureNumAfter());
		round.setBankNum(oldRound.getBankNumAfter());
		round.setGoldNum(oldRound.getGoldNumAfter());
		round.setHkNum(oldRound.getHkNumAfter());
		round.setEtfNum(oldRound.getEtfNumAfter());
		round.setPufaNum(oldRound.getPufaNumAfter());
		round.setTrustNum(oldRound.getTrustNumAfter());
		round.setHouseNum(oldRound.getHouseNumAfter());
		round.setLandNum(oldRound.getLandNumAfter());
		
		round.setInsureNumAfter(new Double(round.getInsure()/roundSet.getInsurePrice()).intValue());
		round.setBankNumAfter(new Double(round.getBank()/roundSet.getBankPrice()).intValue());
		round.setGoldNumAfter(new Double(round.getGold()/roundSet.getGoldPrice()).intValue());
		round.setHkNumAfter(new Double(round.getHk()/roundSet.getHkPrice()).intValue());
		round.setEtfNumAfter(new Double(round.getEtf()/roundSet.getEtfPrice()).intValue());
		round.setPufaNumAfter(new Double(round.getPufa()/roundSet.getPufaPrice()).intValue());
		round.setTrustNumAfter(new Double(round.getTrust()/roundSet.getTrustPrice()).intValue());
		round.setHouseNumAfter(new Double(round.getHouse()/roundSet.getHousePrice()).intValue());
		round.setLandNumAfter(new Double(round.getLand()/roundSet.getLandPrice()).intValue());
	}
	
	/**
	 * 保存各项资产的流动数据
	 */
	private void setRoundChange() {
		round.setInsureChange(req.getInsureChange() != null?req.getInsureChange():0);
		round.setBankChange(req.getBankChange() != null?req.getBankChange():0);
		round.setGoldChange(req.getGoldChange() != null?req.getGoldChange():0);
		round.setHkChange(req.getHkChange() != null?req.getHkChange():0);
		round.setEtfChange(req.getEtfChange() != null?req.getEtfChange():0);
		round.setPufaChange(req.getPufaChange() != null?req.getPufaChange():0);
		round.setTrustChange(req.getTrustChange() != null?req.getTrustChange():0);
		round.setHouseChange(req.getHouseChange() != null?req.getHouseChange():0);
		round.setLandChange(req.getLandChange() != null?req.getLandChange():0);
	}
}
