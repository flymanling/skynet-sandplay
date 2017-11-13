package com.skynet.sandplay.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Round extends BaseEntity {

	private static final long serialVersionUID = 4110225876004953683L;

	/**
	 * 用户id
	 */
	private int userId;
	private String userName;
	/**
	 * 班级
	 */
	private int grade;
	
	/**
	 * 轮次
	 */
	private int round;

	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 保险
	 */
	private double insure;
	private double insureNum;
	/**
	 * 银行理财
	 */
	private double bank;
	private double bankNum;
	
	/**
	 * 黄金
	 */
	private double gold;
	private double goldNum;
	
	/**
	 * 中国香港
	 */
	private double hk;
	private double hkNum;
	
	/**
	 * etf
	 */
	private double etf;
	private double etfNum;
	
	/**
	 * 浦发银行
	 */
	private double pufa;
	private double pufaNum;
	
	/**
	 * 信托
	 */
	private double trust;
	private double trustNum;
	
	/**
	 * 住宅
	 */
	private double house;
	private int houseNum;
	
	/**
	 * 商业用地
	 */
	private double land;
	private int landNum;
	private double rent;
	
	/**
	 * 银行授信额度上限
	 */
	private double bankLimit;
	/**
	 * 净总比
	 */
	private double deptToAsset = 1;
	
	/**
	 * 现金金额
	 */
	private double cash = 200;
	/**
	 * 家庭收入结余
	 */
	private double surplus = 20;
	private double totalSurplus = 20;
	
	/**
	 * 住宅利息支出
	 */
	private double houseInterest;

	/**
	 * 商业用地利息支出
	 */
	private double landInterest;
	
	/**
	 * 信用利息支出
	 */
	private double creditInterest;
	
	/**
	 * 信用贷款利息支出累计
	 */
	private double creditLoanInterestSurplus;
	
	/**
	 * 住宅利息支出累计
	 */
	private double houseInterestSurplus;
	/**
	 * 商业用地利息支出累计
	 */
	private double landInterestSurplus;
	
	/**
	 * 信用贷款
	 */
	private double creditLoan;
	/**
	 * 住宅贷款
	 */
	private double houseLoan;
	/**
	 * 商业用地贷款
	 */
	private double landLoan;
	
	/**
	 * 资产总额
	 */
	private double totalAsset = 200;
	
	/**
	 * 负债总额
	 */
	private double totalDebt;
	/**
	 * 净资产
	 */
	private double netAsset = 200;

	
	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public double getTotalSurplus() {
		return totalSurplus;
	}

	public void setTotalSurplus(double totalSurplus) {
		this.totalSurplus = totalSurplus;
	}
	
	public double getHouseInterest() {
		return houseInterest;
	}
	public void setHouseInterest(double houseInterest) {
		this.houseInterest = houseInterest;
	}
	public double getLandInterest() {
		return landInterest;
	}
	public void setLandInterest(double landInterest) {
		this.landInterest = landInterest;
	}
	
	public double getCreditInterest() {
		return creditInterest;
	}
	public void setCreditInterest(double creditInterest) {
		this.creditInterest = creditInterest;
	}

	public double getCreditLoanInterestSurplus() {
		return creditLoanInterestSurplus;
	}

	public void setCreditLoanInterestSurplus(double creditLoanInterestSurplus) {
		this.creditLoanInterestSurplus = creditLoanInterestSurplus;
	}

	public double getHouseInterestSurplus() {
		return houseInterestSurplus;
	}

	public void setHouseInterestSurplus(double houseInterestSurplus) {
		this.houseInterestSurplus = houseInterestSurplus;
	}

	public double getLandInterestSurplus() {
		return landInterestSurplus;
	}

	public void setLandInterestSurplus(double landInterestSurplus) {
		this.landInterestSurplus = landInterestSurplus;
	}

	public double getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}

	public double getNetAsset() {
		return netAsset;
	}

	public void setNetAsset(double netAsset) {
		this.netAsset = netAsset;
	}

	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getInsure() {
		return insure;
	}

	public void setInsure(double insure) {
		this.insure = insure;
	}

	public double getInsureNum() {
		return insureNum;
	}

	public void setInsureNum(double insureNum) {
		this.insureNum = insureNum;
	}

	public double getBank() {
		return bank;
	}

	public void setBank(double bank) {
		this.bank = bank;
	}

	public double getBankNum() {
		return bankNum;
	}

	public void setBankNum(double bankNum) {
		this.bankNum = bankNum;
	}

	public double getGold() {
		return gold;
	}

	public void setGold(double gold) {
		this.gold = gold;
	}

	public double getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(double goldNum) {
		this.goldNum = goldNum;
	}

	public double getHk() {
		return hk;
	}

	public void setHk(double hk) {
		this.hk = hk;
	}

	public double getHkNum() {
		return hkNum;
	}

	public void setHkNum(double hkNum) {
		this.hkNum = hkNum;
	}

	public double getEtf() {
		return etf;
	}

	public void setEtf(double etf) {
		this.etf = etf;
	}

	public double getEtfNum() {
		return etfNum;
	}

	public void setEtfNum(double etfNum) {
		this.etfNum = etfNum;
	}

	public double getPufa() {
		return pufa;
	}

	public void setPufa(double pufa) {
		this.pufa = pufa;
	}

	public double getPufaNum() {
		return pufaNum;
	}

	public void setPufaNum(double pufaNum) {
		this.pufaNum = pufaNum;
	}

	public double getTrust() {
		return trust;
	}

	public void setTrust(double trust) {
		this.trust = trust;
	}

	public double getTrustNum() {
		return trustNum;
	}

	public void setTrustNum(double trustNum) {
		this.trustNum = trustNum;
	}

	public double getHouse() {
		return house;
	}

	public void setHouse(double house) {
		this.house = house;
	}

	public int getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}

	public double getLand() {
		return land;
	}

	public void setLand(double land) {
		this.land = land;
	}

	public int getLandNum() {
		return landNum;
	}

	public void setLandNum(int landNum) {
		this.landNum = landNum;
	}

	public double getBankLimit() {
		return bankLimit;
	}

	public void setBankLimit(double bankLimit) {
		this.bankLimit = bankLimit;
	}

	public double getDeptToAsset() {
		return deptToAsset;
	}

	public void setDeptToAsset(double deptToAsset) {
		this.deptToAsset = deptToAsset;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}

	public double getCreditLoan() {
		return creditLoan;
	}

	public void setCreditLoan(double creditLoan) {
		this.creditLoan = creditLoan;
	}

	

	public double getHouseLoan() {
		return houseLoan;
	}

	public void setHouseLoan(double houseLoan) {
		this.houseLoan = houseLoan;
	}

	public double getLandLoan() {
		return landLoan;
	}

	public void setLandLoan(double landLoan) {
		this.landLoan = landLoan;
	}

	public double getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(double totalAsset) {
		this.totalAsset = totalAsset;
	}

}
