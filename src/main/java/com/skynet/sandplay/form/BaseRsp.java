package com.skynet.sandplay.form;

import com.skynet.sandplay.model.RoundPlay;

public class BaseRsp {

	public int status;
	public String msg;
	public Object data;
	public int grade;
	
	public BaseRsp() {
		this.grade = RoundPlay.currentGrade;
	}
}
