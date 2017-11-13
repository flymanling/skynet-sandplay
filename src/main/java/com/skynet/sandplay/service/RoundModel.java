package com.skynet.sandplay.service;

import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.RoundEnd;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.model.RoundStart;
import com.skynet.sandplay.service.interfaces.ILoanService;
import com.skynet.sandplay.util.NumUtil;

/**
 * 每轮数据计算模型
 * @author air
 *
 */
public class RoundModel {

	private RoundStart nextRoundStart;//下轮期初数据
	private RoundEnd roundEnd;//本轮期末数据
	private RoundStart roundStart;//本轮期初数据
	private RoundSet roundSet;//本轮价格
	private RoundSet nextRoundSet;//下轮价格
	private RoundForm req;//用户提交数据
	private BaseMsg msg;//请求信息封装
	private ILoanService loanService;
	private LoanCash loanCash = new LoanCash();
	StringBuffer errMsg = new StringBuffer();
	
	public RoundModel(RoundStart roundStart, RoundEnd roundEnd, RoundStart nextRoundStart,
			RoundSet roundSet, RoundSet nextRoundSet, 
			RoundForm req, BaseMsg msg, ILoanService loanService) {
		this.roundStart = roundStart;
		this.roundEnd = roundEnd;
		this.nextRoundStart = nextRoundStart;
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
		nextRoundStart.setSurplus(roundSet.isLostJob() ? -12:20);
		
		//注入基础信息
		setRoundBase();
		
		//计算当前总值
		setRoundValue();
		
		//记录流入流出
		setRoundChange();
		
		//记录资产配置数量
		setRoundNum();
		
		//计算贷款(要放在现金、资产、负债前计算)
		loanService.handleRoundLoan(roundStart, roundEnd, nextRoundStart, req, roundSet, loanCash);

		//计算利息支出
//		if(req.getView() == 0) {
			setRoundInterest();
//		}
		
		//计算现金
		setRoundCash();
		
		//计算资产
		setRoundAsset();
		
		//计算总负债
		double debt = roundEnd.getCreditLoan() + roundEnd.getHouseLoan() + roundEnd.getLandLoan();
		roundEnd.setTotalDebt(debt);
		
		
		//计算净资产
		double netAsset = roundEnd.getTotalAsset() - debt;
		roundEnd.setNetAsset(netAsset);
		
		roundEnd.setDeptToAsset(roundEnd.getNetAsset()/roundEnd.getTotalAsset());
		
		setNextRound();
		
		checkAfter();
		
		return errMsg.length() > 0?errMsg.toString():null;
	}
	
	private boolean checkForm() {
		double bankLimit = roundStart.getNetAsset() * 0.1;
		if(bankLimit > 100) {
			bankLimit = 100;
		}
		if(req.getCreditLoanChange() != null) {
			if((req.getCreditLoanChange() + roundStart.getCreditLoan()) > bankLimit) {
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
		if(roundEnd.getDeptToAsset() < 0.5) {
			errMsg.append("净总比不能小于50%，当前配置净总比：" + (roundEnd.getDeptToAsset()*100 + "%"));
		}
		
	}
	
	/**
	 * 计算资产
	 */
	private void setRoundAsset() {
		double asset = roundEnd.getCash()
				+ roundEnd.getInsure() 
				+ roundEnd.getBank()
				+ roundEnd.getGold()
				+ roundEnd.getHk()
				+ roundEnd.getEtf()
				+ roundEnd.getPufa()
				+ roundEnd.getTrust()
				+ roundEnd.getHouse()
				+ roundEnd.getLand();
		
		roundEnd.setTotalAsset(asset);
	}
	
	/**
	 * 计算现金
	 */
	private void setRoundCash() {
		//计算其他资产的总值
		double newCashNow = roundStart.getCash();
		
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

		roundEnd.setCashChange(newCashNow - roundStart.getCash());
		roundEnd.setCash(newCashNow);
		
		
	}
	
	/**
	/信用贷款利息支出：信用贷款*信用贷款利率
	**/
	private void setRoundInterest() {
		roundEnd.setCreditInterest(NumUtil.getDoubleFormat(roundEnd.getCreditLoan()*nextRoundSet.getCreditRate()));
		roundEnd.setCreditLoanInterestSurplus(roundStart.getCreditLoanInterestSurplus() + roundEnd.getCreditInterest());
		
		//房贷利息支出：房贷*房贷利率
		roundEnd.setHouseInterest(NumUtil.getDoubleFormat(roundEnd.getHouseLoan()*nextRoundSet.getHouseRate()));
		roundEnd.setHouseInterestSurplus(roundStart.getHouseInterestSurplus() + roundEnd.getHouseInterest());
		
		roundEnd.setLandInterest(NumUtil.getDoubleFormat(roundEnd.getLandLoan()*nextRoundSet.getLandRate()));
		roundEnd.setLandInterestSurplus(roundStart.getLandInterestSurplus() + roundEnd.getLandInterest());
		
		roundEnd.setRent(roundStart.getRent());
		
		
	}
	
	/**
	 * 初始化基本信息
	 */
	private void setRoundBase() {
		roundEnd.setUserId(msg.userId);
		roundEnd.setUserName(msg.userName);
		roundEnd.setRound(req.getRound());
		roundEnd.setStatus(1);//锁定
		roundEnd.setGrade(RoundPlay.currentGrade);
		
		nextRoundStart.setUserId(msg.userId);
		nextRoundStart.setUserName(msg.userName);
		nextRoundStart.setRound(req.getRound() + 1);
		nextRoundStart.setGrade(RoundPlay.currentGrade);
	}
	
	/**
	 * 通过流入流出计算当前各项流动资产的总值
	 */
	private void setRoundValue() {
		roundEnd.setInsure(roundStart.getInsure() + (req.getInsureChange() != null?req.getInsureChange():0));
		roundEnd.setBank(roundStart.getBank() + (req.getBankChange() != null?req.getBankChange():0));
		roundEnd.setGold(roundStart.getGold() + (req.getGoldChange() != null?req.getGoldChange():0));
		roundEnd.setHk(roundStart.getHk() + (req.getHkChange()!=null?req.getHkChange():0));
		roundEnd.setEtf(roundStart.getEtf() + (req.getEtfChange()!=null?req.getEtfChange():0));
		roundEnd.setPufa(roundStart.getPufa() + (req.getPufaChange()!=null?req.getPufaChange():0));
		roundEnd.setTrust(roundStart.getTrust() + (req.getTrustChange()!=null?req.getTrustChange():0));
		//信用贷款
		roundEnd.setCreditLoan(roundStart.getCreditLoan() + (req.getCreditLoanChange() != null?req.getCreditLoanChange():0));
		
		//通过流动的首付款计算固定资产总值，要注意卖出的时候要还清贷款
		//变动的数量
		double houseChange = (int) (req.getHouseChange()!=null?req.getHouseChange()*roundSet.getHousePrice():0);
		double landChange = (int) (req.getLandChange()!=null?req.getLandChange()*roundSet.getLandPrice():0);
		//计算总值
		roundEnd.setHouse(roundStart.getHouse() + houseChange);
		roundEnd.setLand(roundStart.getLand() + landChange);
	}
	
	/**
	 * 计算各项资产的数量
	 */
	private void setRoundNum() {
		roundEnd.setInsureNum(NumUtil.getDoubleFormat(roundEnd.getInsure()/roundSet.getInsurePrice()));
		roundEnd.setBankNum(NumUtil.getDoubleFormat(roundEnd.getBank()/roundSet.getBankPrice()));
		roundEnd.setGoldNum(NumUtil.getDoubleFormat(roundEnd.getGold()/roundSet.getGoldPrice()));
		roundEnd.setHkNum(NumUtil.getDoubleFormat(roundEnd.getHk()/roundSet.getHkPrice()));
		roundEnd.setEtfNum(NumUtil.getDoubleFormat(roundEnd.getEtf()/roundSet.getEtfPrice()));
		roundEnd.setPufaNum(NumUtil.getDoubleFormat(roundEnd.getPufa()/roundSet.getPufaPrice()));
		roundEnd.setTrustNum(NumUtil.getDoubleFormat(roundEnd.getTrust()/roundSet.getTrustPrice()));
		roundEnd.setHouseNum(new Double(roundEnd.getHouse()/roundSet.getHousePrice()).intValue());
		roundEnd.setLandNum(new Double(roundEnd.getLand()/roundSet.getLandPrice()).intValue());
	}
	
	/**
	 * 保存各项资产的流动数据
	 */
	private void setRoundChange() {
		roundEnd.setInsureChange(req.getInsureChange() != null?req.getInsureChange():0);
		roundEnd.setBankChange(req.getBankChange() != null?req.getBankChange():0);
		roundEnd.setGoldChange(req.getGoldChange() != null?req.getGoldChange():0);
		roundEnd.setHkChange(req.getHkChange() != null?req.getHkChange():0);
		roundEnd.setEtfChange(req.getEtfChange() != null?req.getEtfChange():0);
		roundEnd.setPufaChange(req.getPufaChange() != null?req.getPufaChange():0);
		roundEnd.setTrustChange(req.getTrustChange() != null?req.getTrustChange():0);
		roundEnd.setHouseChange(req.getHouseChange() != null?req.getHouseChange():0);
		roundEnd.setLandChange(req.getLandChange() != null?req.getLandChange():0);
		roundEnd.setCreditLoanChange(req.getCreditLoanChange() != null?req.getCreditLoanChange():0);
	}
	
	private void setNextRound() {
		//数量与上期期末一致
		nextRoundStart.setInsureNum(roundEnd.getInsureNum());
		nextRoundStart.setBankNum(roundEnd.getBankNum());
		nextRoundStart.setGoldNum(roundEnd.getGoldNum());
		nextRoundStart.setHkNum(roundEnd.getHkNum());
		nextRoundStart.setEtfNum(roundEnd.getEtfNum());
		nextRoundStart.setPufaNum(roundEnd.getPufaNum());
		nextRoundStart.setTrustNum(roundEnd.getTrustNum());
		nextRoundStart.setHouseNum(roundEnd.getHouseNum());
		nextRoundStart.setLandNum(roundEnd.getLandNum());
		
		//市值根据下一轮价格计算
		if(nextRoundSet != null) {
			nextRoundStart.setInsure(nextRoundSet.getInsurePrice()*nextRoundStart.getInsureNum());
			nextRoundStart.setBank(nextRoundSet.getBankPrice()*nextRoundStart.getBankNum());
			nextRoundStart.setGold(nextRoundSet.getGoldPrice()*nextRoundStart.getGoldNum());
			nextRoundStart.setHk(nextRoundSet.getHkPrice()*nextRoundStart.getHkNum());
			nextRoundStart.setEtf(nextRoundSet.getEtfPrice()*nextRoundStart.getEtfNum());
			nextRoundStart.setPufa(nextRoundSet.getPufaPrice()*nextRoundStart.getPufaNum());
			nextRoundStart.setTrust(nextRoundSet.getTrustPrice()*nextRoundStart.getTrustNum());
			nextRoundStart.setHouse(nextRoundSet.getHousePrice()*nextRoundStart.getHouseNum());
			nextRoundStart.setLand(nextRoundSet.getLandPrice()*nextRoundStart.getLandNum());
		}else {
			//如果没有设置下一轮的价格，就取本轮数据
			nextRoundStart.setInsure(roundEnd.getInsure());
			nextRoundStart.setBank(roundEnd.getBank());
			nextRoundStart.setGold(roundEnd.getGold());
			nextRoundStart.setHk(roundEnd.getHk());
			nextRoundStart.setEtf(roundEnd.getEtf());
			nextRoundStart.setPufa(roundEnd.getPufa());
			nextRoundStart.setTrust(roundEnd.getTrust());
			nextRoundStart.setHouse(roundEnd.getHouse());
			nextRoundStart.setLand(roundEnd.getLand());
		}
		
		nextRoundStart.setHouseInterest(roundEnd.getHouseInterest());
		nextRoundStart.setLandInterest(roundEnd.getLandInterest());
		nextRoundStart.setCreditInterest(roundEnd.getCreditInterest());
		nextRoundStart.setCreditLoanInterestSurplus(roundEnd.getCreditLoanInterestSurplus());
		nextRoundStart.setHouseInterestSurplus(roundEnd.getHouseInterestSurplus());
		nextRoundStart.setLandInterestSurplus(roundEnd.getLandInterestSurplus());
		
		nextRoundStart.setRent(NumUtil.getDoubleFormat(roundEnd.getLandNum()*nextRoundSet.getLandRent()));
		
		
		double newCash = roundEnd.getCash();
		
		
		
		//住宅利息支出
		newCash -= roundEnd.getHouseInterest();
		//商业用地利息支出
		newCash -= roundEnd.getLandInterest();
		//信用贷款利息支出
		newCash -= roundEnd.getCreditInterest();
		//商业地产租金收入
		newCash += roundEnd.getRent();
		
		if(nextRoundSet.getSick()) {
			double sickFee = 80;
			if(nextRoundStart.getInsure() > 0 && nextRoundStart.getInsure() < 8) {
				sickFee -= nextRoundStart.getInsure()*10;
			} else if(nextRoundStart.getInsure() > 8) {
				sickFee = 0;
			}
			nextRoundStart.setInsure(0);
			newCash -= sickFee;
			newCash -= 12;//同时失业
			nextRoundStart.setSurplus(-12);
		} else {
			newCash += nextRoundSet.isLostJob()?0:20;
		}
		
		nextRoundStart.setCash(newCash);
		
		//总资产，净资产，净总比按照计算后的市值计算
		//计算总资产
		double assetAfter = nextRoundStart.getCash() 
						+ nextRoundStart.getInsure() 
						+ nextRoundStart.getBank()
						+ nextRoundStart.getGold()
						+ nextRoundStart.getHk()
						+ nextRoundStart.getEtf()
						+ nextRoundStart.getPufa()
						+ nextRoundStart.getTrust()
						+ nextRoundStart.getHouse()
						+ nextRoundStart.getLand()
						
						;
		nextRoundStart.setTotalAsset(assetAfter);
		
		//总负债
		nextRoundStart.setTotalDebt(roundEnd.getTotalDebt());
		
		double netAssetAfter = nextRoundStart.getTotalAsset() - nextRoundStart.getTotalDebt();
		nextRoundStart.setNetAsset(netAssetAfter);
		
		nextRoundStart.setDeptToAsset(nextRoundStart.getNetAsset()/nextRoundStart.getTotalAsset());
		
	}
}
