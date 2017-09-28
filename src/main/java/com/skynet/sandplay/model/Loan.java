package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="loan")
public class Loan extends BaseEntity {

	private static final long serialVersionUID = -5290558729534165057L;

	private int userId;
	
	private String userName;
	
	private int grade;
	
	private int round;
	
	/**
	 * 1、住宅 2、商业用地 3、信用贷款
	 */
	private int type;
	/**
	 * 单位贷款数额
	 */
	private double perLoan;
	/**
	 * 还款状态 0 未还 1、已还
	 */
	private int status;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public double getPerLoan() {
		return perLoan;
	}
	public void setPerLoan(double perLoan) {
		this.perLoan = perLoan;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
