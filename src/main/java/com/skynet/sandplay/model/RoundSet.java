package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="roundset")
public class RoundSet extends BaseEntity{

	private static final long serialVersionUID = -6200757596830282898L;

	/**
	 * 轮次
	 */
	private int round;
	/**
	 * 保险
	 */
	private double insurePrice;
	/**
	 * 银行理财产品
	 */
	private double bankPrice;
	/**
	 * 黄金
	 */
	private double goldPrice;
	/**
	 * 香港
	 */
	private double hkPrice;
	/**
	 * etf
	 */
	private double etfPrice;
	/**
	 * 浦发银行
	 */
	private double pufaPrice;
	/**
	 * 信托产品
	 */
	private double trustPrice;
	/**
	 * 住宅
	 */
	private double housePrice;
	/**
	 * 住宅首付
	 */
	private double housePayPrice;
	/**
	 * 商业地产
	 */
	private double landPrice;
	/**
	 * 商业地产首付
	 */
	private double landPayPrice;
	
	/**
	 * 银行理财利率
	 */
	private double bankRate;
	/**
	 * 住房贷款利率
	 */
	private double houseRate;
	/**
	 * 商业贷款利率
	 */
	private double landRate;
	/**
	 * 信用贷款利率
	 */
	private double creditRate;
	/**
	 * 是否会失业
	 */
	private boolean lostJob;
	/**
	 * 商业用地租金利率
	 */
	private double rentRate;
	
	
	public double getHousePayPrice() {
		return housePayPrice;
	}
	public void setHousePayPrice(double housePayPrice) {
		this.housePayPrice = housePayPrice;
	}
	public double getLandPayPrice() {
		return landPayPrice;
	}
	public void setLandPayPrice(double landPayPrice) {
		this.landPayPrice = landPayPrice;
	}
	
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public double getInsurePrice() {
		return insurePrice;
	}
	public void setInsurePrice(double insurePrice) {
		this.insurePrice = insurePrice;
	}
	public double getBankPrice() {
		return bankPrice;
	}
	public void setBankPrice(double bankPrice) {
		this.bankPrice = bankPrice;
	}
	public double getGoldPrice() {
		return goldPrice;
	}
	public void setGoldPrice(double goldPrice) {
		this.goldPrice = goldPrice;
	}
	public double getHkPrice() {
		return hkPrice;
	}
	public void setHkPrice(double hkPrice) {
		this.hkPrice = hkPrice;
	}
	public double getEtfPrice() {
		return etfPrice;
	}
	public void setEtfPrice(double etfPrice) {
		this.etfPrice = etfPrice;
	}
	public double getPufaPrice() {
		return pufaPrice;
	}
	public void setPufaPrice(double pufaPrice) {
		this.pufaPrice = pufaPrice;
	}
	public double getTrustPrice() {
		return trustPrice;
	}
	public void setTrustPrice(double trustPrice) {
		this.trustPrice = trustPrice;
	}
	public double getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(double housePrice) {
		this.housePrice = housePrice;
	}
	public double getLandPrice() {
		return landPrice;
	}
	public void setLandPrice(double landPrice) {
		this.landPrice = landPrice;
	}
	public double getBankRate() {
		return bankRate;
	}
	public void setBankRate(double bankRate) {
		this.bankRate = bankRate;
	}
	public double getHouseRate() {
		return houseRate;
	}
	public void setHouseRate(double houseRate) {
		this.houseRate = houseRate;
	}
	public double getLandRate() {
		return landRate;
	}
	public void setLandRate(double landRate) {
		this.landRate = landRate;
	}
	public double getCreditRate() {
		return creditRate;
	}
	public void setCreditRate(double creditRate) {
		this.creditRate = creditRate;
	}
	public boolean isLostJob() {
		return lostJob;
	}
	public void setLostJob(boolean lostJob) {
		this.lostJob = lostJob;
	}
	public double getRentRate() {
		return rentRate;
	}
	public void setRentRate(double rentRate) {
		this.rentRate = rentRate;
	}
	
}
