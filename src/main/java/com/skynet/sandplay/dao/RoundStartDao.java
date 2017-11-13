package com.skynet.sandplay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.skynet.sandplay.dao.interfaces.IRoundStartDao;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundStart;

@Repository("roundStartDao")
public class RoundStartDao extends BaseDaoImpl<RoundStart, Integer> implements IRoundStartDao{

	@Override
	public RoundStart getRoundStartByRound(final int userId, final int round) {
		String hql = "from com.skynet.sandplay.model.RoundStart r where r.round=:round and r.userId=:userId and grade=:grade";
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(RoundStart.class,"roundStart");
		criteria.add(Restrictions.eq("round", round)).add(Restrictions.eq("userId", userId)).add(Restrictions.eq("grade", RoundPlay.currentGrade));
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("round", round);
		query.setInteger("userId", userId);
		query.setInteger("grade", RoundPlay.currentGrade);
		
		List<RoundStart> list = criteria.list();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
