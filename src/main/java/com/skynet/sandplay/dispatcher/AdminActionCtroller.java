package com.skynet.sandplay.dispatcher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionId;
import com.skynet.sandplay.annotation.ServiceId;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.service.IBaseService;

@ServiceId(2)
@Component("adminActionCtroller")
public class AdminActionCtroller extends ActionCtroller {
	
	@Resource(name="roudSetService")
	private IBaseService<RoundSet, String> roundSetService;
	
	@ActionId(1)
	public String setCurrentStep(BaseMsg msg) {
		int step = msg.getAsInt("step", 0);
		RoundPlay.currentRound = step;
		return retSuccess();
	}
	
	@ActionId(2)
	public String addRoundSet(BaseMsg msg) {
		RoundSet roundSet = gson.fromJson(msg.content, RoundSet.class);
		String id = roundSetService.save(roundSet);
		return retSuccess(id);
	}
	
	@ActionId(3)
	public String getRoundSetList(BaseMsg msg) {
		List<RoundSet> list = roundSetService.list("from roundset", null);
		return retSuccess(list);
	}
}
