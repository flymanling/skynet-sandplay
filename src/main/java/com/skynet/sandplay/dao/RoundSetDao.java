package com.skynet.sandplay.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.skynet.sandplay.dao.interfaces.IRoundSetDao;
import com.skynet.sandplay.model.RoundSet;

@Repository("roundSetDao")
public class RoundSetDao extends BaseDaoImpl<RoundSet, Integer> implements IRoundSetDao{

	@Override
	public RoundSet getRoundSetByRound(int round) {
		String hql = "from roundset r where r.round=:round";
		Query query = getSession().createQuery(hql);
		query.setInteger("round", round);
		List<RoundSet> list = query.list();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
