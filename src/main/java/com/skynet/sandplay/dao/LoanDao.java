package com.skynet.sandplay.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.skynet.sandplay.dao.interfaces.ILoanDao;
import com.skynet.sandplay.model.Loan;
import com.skynet.sandplay.model.RoundPlay;

@Repository("loanDao")
public class LoanDao extends BaseDaoImpl<Loan, Integer> implements ILoanDao {

	@Override
	public List<Loan> getLoanList(int userId, int grade, int status, int type) {
		String hql = "from loan l where l.status=:status and l.type=:type "
				+ "and l.grade=:grade and l.userId=:userId order by l.round ";
		Query query = getSession().createQuery(hql);
		query.setInteger("type", type);
		query.setInteger("status", status);
		query.setInteger("userId", userId);
		query.setInteger("grade", RoundPlay.currentGrade);
		return  query.list();
	}

}
