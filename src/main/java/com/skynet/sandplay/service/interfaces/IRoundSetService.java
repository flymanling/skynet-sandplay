package com.skynet.sandplay.service.interfaces;

import com.skynet.sandplay.model.RoundSet;

public interface IRoundSetService extends IBaseService<RoundSet, Integer> {

	public RoundSet getRoundSetByRound(int round);
}
