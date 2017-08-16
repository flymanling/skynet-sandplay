package com.skynet.sandplay.dispatcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.skynet.sandplay.annotation.ActionMark;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.IBaseService;

@Component("actionCtroller")
public class ActionCtroller {

	@Resource(name="userService")
	private IBaseService<User, String> userService;
	
	private static Gson gson = new Gson();
	
	@ActionMark(serviceId=1, actionId=1)
	public String getUser(BaseMsg msg) {
//		String id = "402881e65de12d3f015de12d41a00000";
		String id = msg.getAsString("id");
		User user = userService.get(id);
		if(user != null) {
			return gson.toJson(user);
		}
		return "";
	}
}
