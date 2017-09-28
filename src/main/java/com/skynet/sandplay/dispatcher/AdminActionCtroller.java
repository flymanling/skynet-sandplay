package com.skynet.sandplay.dispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionId;
import com.skynet.sandplay.annotation.ServiceId;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.service.interfaces.IBaseService;
import com.skynet.sandplay.service.interfaces.IRoundSetService;
import com.skynet.sandplay.util.StringUtil;

@ServiceId(2)
@Component("adminActionCtroller")
public class AdminActionCtroller extends ActionCtroller {
	
	@Resource(name="roundSetService")
	private IRoundSetService roundSetService;
	
	@ActionId(1)
	public String setCurrentStep(BaseMsg msg) {
		int step = msg.getAsInt("step", -1);
		if(step>=0) {
			RoundPlay.currentRound = step;
		}
		int grade = msg.getAsInt("grade", 0);
		if(grade > 0) {
			RoundPlay.currentGrade = grade;
		}
		String lockPwd = msg.getAsString("lockPwd", null);
		if(lockPwd != null) {
			RoundPlay.lockPwd = lockPwd;
		}
		return retSuccess();
	}
	
	@ActionId(2)
	public String addRoundSet(BaseMsg msg) {
		RoundSet roundSet = gson.fromJson(msg.content, RoundSet.class);
		if(roundSet.getId() != 0) {
			boolean rs = roundSetService.update(roundSet);
			return retByRs(rs);
		} else {
			
			Integer id = roundSetService.save(roundSet); 
			return retSuccess(id);
		}
	}
	
	@ActionId(3)
	public String getRoundSetList(BaseMsg msg) {
		List<RoundSet> list = roundSetService.list("from roundset", null);
		return retSuccess(list);
	}
	
	@ActionId(4)
	public String getCurrentStep(BaseMsg msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentStep", RoundPlay.currentRound);
		map.put("currentGrade", RoundPlay.currentGrade);
		map.put("lockPwd", RoundPlay.lockPwd);
		return retSuccess(map);
	}
	
	@ActionId(5)
	public String deleteRoundSet(BaseMsg msg) {
		Integer id = msg.getAsInt("id", 0);
		if(id > 0) {
			boolean rs = roundSetService.delete(id);
			return retByRs(rs);
		} 
		return retFail("操作失败");
	}
}
