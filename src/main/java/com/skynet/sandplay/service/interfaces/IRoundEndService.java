package com.skynet.sandplay.service.interfaces;

import com.skynet.sandplay.model.RoundEnd;

public interface IRoundEndService extends IBaseService<RoundEnd, Integer>{

	public RoundEnd getRoundEndByRound(int uerId, int round);
	
}
