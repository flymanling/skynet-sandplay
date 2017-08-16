package com.skynet.sandplay.dispatcher;

import static com.skynet.sandplay.dispatcher.ActionCtroller.gson;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionMark;
import com.skynet.sandplay.annotation.ServiceMark;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.IBaseService;
import com.skynet.sandplay.util.StringUtil;

@ServiceMark(1)
@Component("userActionCtroller")
public class UserActionCtroller extends ActionCtroller{

	@Resource(name="userService")
	private IBaseService<User, String> userService;
	@Resource(name="roundService")
	private IBaseService<Round, String> roundService;
	
	@ActionMark(1)
	public String getUser(BaseMsg msg) {
//		String id = "402881e65de12d3f015de12d41a00000";
		String id = msg.getAsString("id", null);
		if(!StringUtil.isNotEmpty(id)) {
			return "id not found";
		}
		User user = userService.get(id);
		if(user != null) {
			return gson.toJson(user);
		}
		return "";
	}
}
