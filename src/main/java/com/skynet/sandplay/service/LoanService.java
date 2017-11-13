package com.skynet.sandplay.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.dao.interfaces.ILoanDao;
import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.Loan;
import com.skynet.sandplay.model.RoundEnd;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.model.RoundStart;
import com.skynet.sandplay.service.interfaces.ILoanService;

@Service("loanService")
public class LoanService extends BaseServiceImpl<Loan, Integer> implements ILoanService{

	@Resource(name="loanDao")
	private ILoanDao loanDao;
	
	@Resource(name="roundPlayDao")
    public void setBaseDao(IBaseDao<Loan, Integer> baseDao) {  
        super.setBaseDao(baseDao);
    }  
	
	@Override
	public String handleRoundLoan(RoundStart roundStart, RoundEnd roundEnd, RoundStart nextRoundStart, RoundForm req, RoundSet roundSet, LoanCash loanCash) {
		if(req.getHouseChange() != null) {
			if(req.getHouseChange() > 0) {
				//新增贷款
				//修改贷款总额，取本轮的价格
				double currentLoan = roundStart.getHouseLoan();
				currentLoan += req.getHouseChange()*(roundSet.getHousePrice()-roundSet.getHousePayPrice());
				roundEnd.setHouseLoan(currentLoan);
				//插入贷款记录
				for(int i=0;i<req.getHouseChange();i++) {
					Loan loan = new Loan();
					loan.setGrade(RoundPlay.currentGrade);
					loan.setPerLoan(roundSet.getHousePrice() - roundSet.getHousePayPrice());
					loan.setRound(roundStart.getRound());
					loan.setStatus(0);
					loan.setType(1);
					loan.setUserId(roundStart.getUserId());
					loan.setUserName(roundStart.getUserName());
					if(req.getView() > 0) {
						continue;
					}
					loanDao.save(loan);
				}
			} else {
				//还贷款，从最早的贷款记录开始
				//
				List<Loan> loanList = loanDao.getLoanList(roundStart.getUserId(), RoundPlay.currentGrade, 0, 1);
				
				double currentLoan = roundStart.getHouseLoan();
				if(loanList != null && loanList.size() > 0) {
					for(int i=0;i<-req.getHouseChange();i++) {
						Loan loan = loanList.get(i);
						loan.setStatus(1);
						currentLoan -= loan.getPerLoan();
						loanCash.houseCash += loan.getPerLoan();
						if(req.getView() > 0) {
							continue;
						}
						loanDao.update(loan);
					}
				}
				roundEnd.setHouseLoan(currentLoan);
			}
		} else {
			roundEnd.setHouseLoan(roundStart.getHouseLoan());
		}
		
		
		
		if(req.getLandChange() != null) {
			if(req.getLandChange() > 0) {
				//新增贷款
				//修改贷款总额，取本轮的价格
				double currentLoan = roundStart.getLandLoan();
				currentLoan += req.getLandChange()*(roundSet.getLandPrice()-roundSet.getLandPayPrice());
				roundEnd.setLandLoan(currentLoan);
				//插入贷款记录
				for(int i=0;i<req.getLandChange();i++) {
					Loan loan = new Loan();
					loan.setGrade(RoundPlay.currentGrade);
					loan.setPerLoan(roundSet.getLandPrice() - roundSet.getLandPayPrice());
					loan.setRound(roundStart.getRound());
					loan.setStatus(0);
					loan.setType(2);
					loan.setUserId(roundStart.getUserId());
					loan.setUserName(roundStart.getUserName());
					if(req.getView() > 0) {
						continue;
					}
					loanDao.save(loan);
				}
			} else {
				//还贷款，从最早的贷款记录开始
				//
				List<Loan> loanList = loanDao.getLoanList(roundStart.getUserId(), RoundPlay.currentGrade, 0, 2);
				
				double currentLoan = roundStart.getLandLoan();
				if(loanList != null && loanList.size() > 0) {
					for(int i=0;i<-req.getLandChange();i++) {
						Loan loan = loanList.get(i);
						loan.setStatus(1);
						currentLoan -= loan.getPerLoan();
						loanCash.landCash += loan.getPerLoan();
						if(req.getView() > 0) {
							continue;
						}
						loanDao.update(loan);
					}
				}
				roundEnd.setLandLoan(currentLoan);
			}
		} else {
			roundEnd.setLandLoan(roundStart.getLandLoan());
		}
		
		
		if(req.getCreditLoanChange() != null) {
			List<Loan> loanList = loanDao.getLoanList(roundStart.getUserId(), RoundPlay.currentGrade, 0, 3);
			if(loanList != null && loanList.size() > 0) {
				//已有贷款记录，只修改数量和状态
				Loan loan = loanList.get(0);
				loan.setPerLoan(loan.getPerLoan() + req.getCreditLoanChange());
				if(loan.getPerLoan() <= 0) {
					loan.setStatus(1);
				}
				if(req.getView() == 0) {
					loanDao.update(loan);
				}
				roundEnd.setCreditLoan(loan.getPerLoan());
			} else {
				//新增贷款记录
				Loan loan = new Loan();
				loan.setGrade(RoundPlay.currentGrade);
				loan.setPerLoan(req.getCreditLoanChange());
				loan.setRound(roundStart.getRound());
				loan.setStatus(0);
				loan.setType(3);
				loan.setUserId(roundStart.getUserId());
				loan.setUserName(roundStart.getUserName());
				if(req.getView() == 0) {
					loanDao.save(loan);
				}
				roundEnd.setCreditLoan(loan.getPerLoan());
			}
			
		} else {
			roundEnd.setCreditLoan(roundStart.getCreditLoan());
		}
		
		nextRoundStart.setHouseLoan(roundEnd.getHouseLoan());
		nextRoundStart.setLandLoan(roundEnd.getLandLoan());
		nextRoundStart.setCreditLoan(roundEnd.getCreditLoan());
		
		return null;
	}

}
