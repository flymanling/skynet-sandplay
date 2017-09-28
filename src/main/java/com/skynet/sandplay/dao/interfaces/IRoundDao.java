package com.skynet.sandplay.dao.interfaces;

import com.skynet.sandplay.model.Round;

public interface IRoundDao extends IBaseDao<Round, Integer>{

	public Round getRoundByRound(int userId, int round);
}
