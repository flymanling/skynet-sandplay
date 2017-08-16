package com.skynet.sandplay.dispatcher;

import com.skynet.sandplay.form.BaseMsg;

public interface Action {

	public String execute(BaseMsg msg) throws Exception;
}
