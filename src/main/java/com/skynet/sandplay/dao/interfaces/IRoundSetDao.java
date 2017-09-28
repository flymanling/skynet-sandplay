package com.skynet.sandplay.dao.interfaces;

import com.skynet.sandplay.model.RoundSet;

public interface IRoundSetDao extends IBaseDao<RoundSet, Integer>{

	public RoundSet getRoundSetByRound(int round);
}
