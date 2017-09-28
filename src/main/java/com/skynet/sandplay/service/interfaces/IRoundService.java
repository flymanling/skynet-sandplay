package com.skynet.sandplay.service.interfaces;

import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.RoundSet;

public interface IRoundService extends IBaseService<Round, Integer>{

	public Round getRoundByRound(int uerId, int round);
	
	public String saveRound(BaseMsg msg, RoundForm req, Round round, Round oldRound, RoundSet roundSet, RoundSet nextRoundSet);
}
