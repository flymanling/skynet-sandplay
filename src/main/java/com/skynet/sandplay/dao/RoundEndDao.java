package com.skynet.sandplay.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.skynet.sandplay.dao.interfaces.IRoundEndDao;
import com.skynet.sandplay.model.RoundEnd;
import com.skynet.sandplay.model.RoundPlay;

@Repository("roundEndDao")
public class RoundEndDao extends BaseDaoImpl<RoundEnd, Integer> implements IRoundEndDao{

	@Override
	public RoundEnd getRoundEndByRound(int userId, int round) {
		String hql = "from roundEnd r where r.round=:round and r.userId=:userId and grade=:grade";
		Query query = getSession().createQuery(hql);
		query.setInteger("round", round);
		query.setInteger("userId", userId);
		query.setInteger("grade", RoundPlay.currentGrade);
		List<RoundEnd> list = query.list();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
