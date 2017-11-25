package com.skynet.sandplay.service.interfaces;

import com.skynet.sandplay.model.RoundStart;

public interface IRoundStartService extends IBaseService<RoundStart, Integer>{

	public RoundStart getRoundStartByRound(int uerId, int round);
}
