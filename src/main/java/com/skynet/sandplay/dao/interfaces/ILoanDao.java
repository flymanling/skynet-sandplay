package com.skynet.sandplay.dao.interfaces;

import java.util.List;

import com.skynet.sandplay.model.Loan;

public interface ILoanDao extends IBaseDao<Loan, Integer>{

	public List<Loan> getLoanList(int userId, int grade, int status, int type);
}
