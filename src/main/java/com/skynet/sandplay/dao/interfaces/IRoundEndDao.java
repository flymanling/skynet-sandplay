package com.skynet.sandplay.dao.interfaces;

import com.skynet.sandplay.model.RoundEnd;

public interface IRoundEndDao extends IBaseDao<RoundEnd, Integer>{

	public RoundEnd getRoundEndByRound(int userId, int round);
}
