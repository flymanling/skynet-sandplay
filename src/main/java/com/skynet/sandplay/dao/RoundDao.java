package com.skynet.sandplay.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.skynet.sandplay.dao.interfaces.IRoundDao;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;

@Repository("roundDao")
public class RoundDao extends BaseDaoImpl<Round, Integer> implements IRoundDao{

	@Override
	public Round getRoundByRound(int userId, int round) {
		String hql = "from round r where r.round=:round and r.userId=:userId and grade=:grade";
		Query query = getSession().createQuery(hql);
		query.setInteger("round", round);
		query.setInteger("userId", userId);
		query.setInteger("grade", RoundPlay.currentGrade);
		List<Round> list = query.list();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
