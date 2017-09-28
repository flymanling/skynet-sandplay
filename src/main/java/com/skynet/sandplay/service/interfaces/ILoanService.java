package com.skynet.sandplay.service.interfaces;

import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.Loan;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.service.LoanCash;

public interface ILoanService extends IBaseService<Loan, Integer>{

	public String handleRoundLoan(Round round, Round oldRound, RoundForm req, RoundSet roundSet, LoanCash loanCash);
		
}
