package com.skynet.sandplay.dispatcher;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.form.BaseRsp;

@Component("actionCtroller")
public class ActionCtroller {

	protected static Gson gson = new Gson();
	
	protected String retSuccess(Object obj) {
		BaseRsp rsp = new BaseRsp();
		rsp.data = obj;
		rsp.status = 1;
		rsp.msg = "操作成功";
		return gson.toJson(rsp);
	}
	
	protected String retSuccess() {
		BaseRsp rsp = new BaseRsp();
		rsp.status = 1;
		rsp.msg = "操作成功";
		return gson.toJson(rsp);
	}
	
	protected String retFail(String msg) {
		BaseRsp rsp = new BaseRsp();
		rsp.status = 2;
		rsp.msg = msg;
		return gson.toJson(rsp);
	}
	
	public String retByRs(boolean rs) {
		return rs?retSuccess():retFail("操作失败");
	}
	
	public String getRoundStep(BaseMsg msg) {
		return null;
	}
}
