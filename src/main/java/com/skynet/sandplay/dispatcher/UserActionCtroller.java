package com.skynet.sandplay.dispatcher;

import static com.skynet.sandplay.dispatcher.ActionCtroller.gson;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionId;
import com.skynet.sandplay.annotation.ServiceId;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.IBaseService;
import com.skynet.sandplay.util.StringUtil;

@ServiceId(1)
@Component("userActionCtroller")
public class UserActionCtroller extends ActionCtroller{

	@Resource(name="userService")
	private IBaseService<User, String> userService;
	@Resource(name="roundService")
	private IBaseService<Round, String> roundService;
	
	@ActionId(1)
	public String getUser(BaseMsg msg) {
//		String id = "402881e65de12d3f015de12d41a00000";
		String id = msg.getAsString("id", null);
		if(!StringUtil.isNotEmpty(id)) {
			return "id not found";
		}
		User user = userService.get(id);
		return retSuccess(user);
	}
	
	@ActionId(2)
	public String getCurrentStep(BaseMsg msg) {
		return retSuccess(RoundPlay.currentRound);
	}
}
