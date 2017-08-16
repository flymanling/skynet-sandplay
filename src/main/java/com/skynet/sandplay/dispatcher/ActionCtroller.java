package com.skynet.sandplay.dispatcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.skynet.sandplay.annotation.ActionMark;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.IBaseService;

@Component("actionCtroller")
public class ActionCtroller {

	protected static Gson gson = new Gson();
	

	
	public String getRoundStep(BaseMsg msg) {
		return null;
	}
}
