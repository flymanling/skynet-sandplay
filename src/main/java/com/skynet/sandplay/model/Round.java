package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="round")
public class Round extends BaseEntity{

	private static final long serialVersionUID = 3035450235169761428L;
	
	public static int INCOME = 20;

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
	 * 状态 1锁定 0未锁定
	 */
	private int status;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 保险
	 */
	private double insure;
	private double insureAfter;
	private double insureChange;
	private int insureNum;
	private int insureNumAfter;
	/**
	 * 银行理财
	 */
	private double bank;
	private double bankAfter;
	private double bankChange;
	private int bankNum;
	private int bankNumAfter;
	
	/**
	 * 黄金
	 */
	private double gold;
	private double goldAfter;
	private double goldChange;
	private int goldNum;
	private int goldNumAfter;
	
	/**
	 * 中国香港
	 */
	private double hk;
	private double hkAfter;
	private double hkChange;
	private int hkNum;
	private int hkNumAfter;
	
	/**
	 * etf
	 */
	private double etf;
	private double etfAfter;
	private double etfChange;
	private int etfNum;
	private int etfNumAfter;
	
	/**
	 * 浦发银行
	 */
	private double pufa;
	private double pufaAfter;
	private double pufaChange;
	private int pufaNum;
	private int pufaNumAfter;
	
	/**
	 * 信托
	 */
	private double trust;
	private double trustAfter;
	private double trustChange;
	private int trustNum;
	private int trustNumAfter;
	
	/**
	 * 住宅
	 */
	private double house;
	private double houseAfter;
	private double houseChange;
	private int houseNum;
	private int houseNumAfter;
	
	/**
	 * 商业用地
	 */
	private double land;
	private double landAfter;
	private double landChange;
	private int landNum;
	private int landNumAfter;
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
	private double cash;
	private double cashChange;
	private double cashAfter = 180;
	/**
	 * 家庭收入结余
	 */
	private double surplus = 20;
	private double totalSurplus = 20;
	/**
	 * 信用贷款利息支出
	 */
	private double creditLoanInterest;
	private double creditLoanInterestAfter;
	private double creditLoanInterestSurplus;
	
	/**
	 * 住宅利息支出
	 */
	private double houseInterest;
	private double houseInterestAfter;
	private double houseInterestSurplus;
	/**
	 * 商业用地利息支出
	 */
	private double landInterest;
	private double landInterestAfter;
	private double landInterestSurplus;
	/**
	 * 信用贷款
	 */
	private double creditLoan;
	private double creditLoanChange;
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
	private double totalAsset;
	private double totalAssetAfter = 200;
	
	/**
	 * 负债总额
	 */
	private double totalDebt;
	private double totalDebtAfter;
	/**
	 * 净资产
	 */
	private double netAsset;
	private double netAssetAfter = 200;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public double getInsureChange() {
		return insureChange;
	}

	public void setInsureChange(double insureChange) {
		this.insureChange = insureChange;
	}

	public int getInsureNum() {
		return insureNum;
	}

	public void setInsureNum(int insureNum) {
		this.insureNum = insureNum;
	}

	public double getBank() {
		return bank;
	}

	public void setBank(double bank) {
		this.bank = bank;
	}

	public double getBankChange() {
		return bankChange;
	}

	public void setBankChange(double bankChange) {
		this.bankChange = bankChange;
	}

	public int getBankNum() {
		return bankNum;
	}

	public void setBankNum(int bankNum) {
		this.bankNum = bankNum;
	}

	public double getGold() {
		return gold;
	}

	public void setGold(double gold) {
		this.gold = gold;
	}

	public double getGoldChange() {
		return goldChange;
	}

	public void setGoldChange(double goldChange) {
		this.goldChange = goldChange;
	}

	public int getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(int goldNum) {
		this.goldNum = goldNum;
	}

	public double getHk() {
		return hk;
	}

	public void setHk(double hk) {
		this.hk = hk;
	}

	public double getHkChange() {
		return hkChange;
	}

	public void setHkChange(double hkChange) {
		this.hkChange = hkChange;
	}

	public int getHkNum() {
		return hkNum;
	}

	public void setHkNum(int hkNum) {
		this.hkNum = hkNum;
	}

	public double getEtf() {
		return etf;
	}

	public void setEtf(double etf) {
		this.etf = etf;
	}

	public double getEtfChange() {
		return etfChange;
	}

	public void setEtfChange(double etfChange) {
		this.etfChange = etfChange;
	}

	public int getEtfNum() {
		return etfNum;
	}

	public void setEtfNum(int etfNum) {
		this.etfNum = etfNum;
	}

	public double getPufa() {
		return pufa;
	}

	public void setPufa(double pufa) {
		this.pufa = pufa;
	}

	public double getPufaChange() {
		return pufaChange;
	}

	public void setPufaChange(double pufaChange) {
		this.pufaChange = pufaChange;
	}

	public int getPufaNum() {
		return pufaNum;
	}

	public void setPufaNum(int pufaNum) {
		this.pufaNum = pufaNum;
	}

	public double getTrust() {
		return trust;
	}

	public void setTrust(double trust) {
		this.trust = trust;
	}

	public double getTrustChange() {
		return trustChange;
	}

	public void setTrustChange(double trustChange) {
		this.trustChange = trustChange;
	}

	public int getTrustNum() {
		return trustNum;
	}

	public void setTrustNum(int trustNum) {
		this.trustNum = trustNum;
	}

	public double getHouse() {
		return house;
	}

	public void setHouse(double house) {
		this.house = house;
	}

	public double getHouseChange() {
		return houseChange;
	}

	public void setHouseChange(double houseChange) {
		this.houseChange = houseChange;
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

	public double getLandChange() {
		return landChange;
	}

	public void setLandChange(double landChange) {
		this.landChange = landChange;
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

	public double getCreditLoanInterest() {
		return creditLoanInterest;
	}

	public void setCreditLoanInterest(double creditLoanInterest) {
		this.creditLoanInterest = creditLoanInterest;
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

	public double getCreditLoan() {
		return creditLoan;
	}

	public void setCreditLoan(double creditLoan) {
		this.creditLoan = creditLoan;
	}

	public double getCreditLoanChange() {
		return creditLoanChange;
	}

	public void setCreditLoanChange(double creditLoanChange) {
		this.creditLoanChange = creditLoanChange;
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

	public double getInsureAfter() {
		return insureAfter;
	}

	public void setInsureAfter(double insureAfter) {
		this.insureAfter = insureAfter;
	}

	public double getBankAfter() {
		return bankAfter;
	}

	public void setBankAfter(double bankAfter) {
		this.bankAfter = bankAfter;
	}

	public double getGoldAfter() {
		return goldAfter;
	}

	public void setGoldAfter(double goldAfter) {
		this.goldAfter = goldAfter;
	}

	public double getHkAfter() {
		return hkAfter;
	}

	public void setHkAfter(double hkAfter) {
		this.hkAfter = hkAfter;
	}

	public double getEtfAfter() {
		return etfAfter;
	}

	public void setEtfAfter(double etfAfter) {
		this.etfAfter = etfAfter;
	}

	public double getPufaAfter() {
		return pufaAfter;
	}

	public void setPufaAfter(double pufaAfter) {
		this.pufaAfter = pufaAfter;
	}

	public double getTrustAfter() {
		return trustAfter;
	}

	public void setTrustAfter(double trustAfter) {
		this.trustAfter = trustAfter;
	}

	public double getHouseAfter() {
		return houseAfter;
	}

	public void setHouseAfter(double houseAfter) {
		this.houseAfter = houseAfter;
	}

	public double getLandAfter() {
		return landAfter;
	}

	public void setLandAfter(double landAfter) {
		this.landAfter = landAfter;
	}

	public double getCashAfter() {
		return cashAfter;
	}

	public void setCashAfter(double cashAfter) {
		this.cashAfter = cashAfter;
	}

	public double getTotalAssetAfter() {
		return totalAssetAfter;
	}

	public void setTotalAssetAfter(double totalAssetAfter) {
		this.totalAssetAfter = totalAssetAfter;
	}

	public double getCreditLoanInterestAfter() {
		return creditLoanInterestAfter;
	}

	public void setCreditLoanInterestAfter(double creditLoanInterestAfter) {
		this.creditLoanInterestAfter = creditLoanInterestAfter;
	}

	public double getHouseInterestAfter() {
		return houseInterestAfter;
	}

	public void setHouseInterestAfter(double houseInterestAfter) {
		this.houseInterestAfter = houseInterestAfter;
	}

	public double getLandInterestAfter() {
		return landInterestAfter;
	}

	public void setLandInterestAfter(double landInterestAfter) {
		this.landInterestAfter = landInterestAfter;
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

	public double getTotalDebtAfter() {
		return totalDebtAfter;
	}

	public void setTotalDebtAfter(double totalDebtAfter) {
		this.totalDebtAfter = totalDebtAfter;
	}

	public double getNetAssetAfter() {
		return netAssetAfter;
	}

	public void setNetAssetAfter(double netAssetAfter) {
		this.netAssetAfter = netAssetAfter;
	}

	public int getInsureNumAfter() {
		return insureNumAfter;
	}

	public void setInsureNumAfter(int insureNumAfter) {
		this.insureNumAfter = insureNumAfter;
	}

	public int getBankNumAfter() {
		return bankNumAfter;
	}

	public void setBankNumAfter(int bankNumAfter) {
		this.bankNumAfter = bankNumAfter;
	}

	public int getGoldNumAfter() {
		return goldNumAfter;
	}

	public void setGoldNumAfter(int goldNumAfter) {
		this.goldNumAfter = goldNumAfter;
	}

	public int getHkNumAfter() {
		return hkNumAfter;
	}

	public void setHkNumAfter(int hkNumAfter) {
		this.hkNumAfter = hkNumAfter;
	}

	public int getEtfNumAfter() {
		return etfNumAfter;
	}

	public void setEtfNumAfter(int etfNumAfter) {
		this.etfNumAfter = etfNumAfter;
	}

	public int getPufaNumAfter() {
		return pufaNumAfter;
	}

	public void setPufaNumAfter(int pufaNumAfter) {
		this.pufaNumAfter = pufaNumAfter;
	}

	public int getTrustNumAfter() {
		return trustNumAfter;
	}

	public void setTrustNumAfter(int trustNumAfter) {
		this.trustNumAfter = trustNumAfter;
	}

	public int getHouseNumAfter() {
		return houseNumAfter;
	}

	public void setHouseNumAfter(int houseNumAfter) {
		this.houseNumAfter = houseNumAfter;
	}

	public int getLandNumAfter() {
		return landNumAfter;
	}

	public void setLandNumAfter(int landNumAfter) {
		this.landNumAfter = landNumAfter;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public double getCashChange() {
		return cashChange;
	}

	public void setCashChange(double cashChange) {
		this.cashChange = cashChange;
	}

	public double getTotalSurplus() {
		return totalSurplus;
	}

	public void setTotalSurplus(double totalSurplus) {
		this.totalSurplus = totalSurplus;
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
	
	
}
