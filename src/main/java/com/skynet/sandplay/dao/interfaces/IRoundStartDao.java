package com.skynet.sandplay.dao.interfaces;

import com.skynet.sandplay.model.RoundStart;

public interface IRoundStartDao extends IBaseDao<RoundStart, Integer>{

	public RoundStart getRoundStartByRound(int userId, int round);
}
