package com.skynet.sandplay.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="roundEnd")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RoundEnd extends Round{

	private static final long serialVersionUID = 3035450235169761428L;
	
	/**
	 * 状态 1锁定 0未锁定
	 */
	private int status;
	/**
	 * 保险
	 */
	private double insureChange;
	/**
	 * 银行理财
	 */
	private double bankChange;
	
	/**
	 * 黄金
	 */
	private double goldChange;
	
	/**
	 * 中国香港
	 */
	private double hkChange;
	
	/**
	 * etf
	 */
	private double etfChange;
	
	/**
	 * 浦发银行
	 */
	private double pufaChange;
	
	/**
	 * 信托
	 */
	private double trustChange;
	
	/**
	 * 住宅
	 */
	private double houseChange;
	
	/**
	 * 商业用地
	 */
	private double landChange;
	
	/**
	 * 现金金额
	 */
	private double cashChange;
	
	/**
	 * 信用贷款
	 */
	private double creditLoanChange;
	
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getInsureChange() {
		return insureChange;
	}
	public void setInsureChange(double insureChange) {
		this.insureChange = insureChange;
	}
	public double getBankChange() {
		return bankChange;
	}
	public void setBankChange(double bankChange) {
		this.bankChange = bankChange;
	}
	public double getGoldChange() {
		return goldChange;
	}
	public void setGoldChange(double goldChange) {
		this.goldChange = goldChange;
	}
	public double getHkChange() {
		return hkChange;
	}
	public void setHkChange(double hkChange) {
		this.hkChange = hkChange;
	}
	public double getEtfChange() {
		return etfChange;
	}
	public void setEtfChange(double etfChange) {
		this.etfChange = etfChange;
	}
	public double getPufaChange() {
		return pufaChange;
	}
	public void setPufaChange(double pufaChange) {
		this.pufaChange = pufaChange;
	}
	public double getTrustChange() {
		return trustChange;
	}
	public void setTrustChange(double trustChange) {
		this.trustChange = trustChange;
	}
	public double getHouseChange() {
		return houseChange;
	}
	public void setHouseChange(double houseChange) {
		this.houseChange = houseChange;
	}
	public double getLandChange() {
		return landChange;
	}
	public void setLandChange(double landChange) {
		this.landChange = landChange;
	}
	public double getCashChange() {
		return cashChange;
	}
	public void setCashChange(double cashChange) {
		this.cashChange = cashChange;
	}
	
	
	public double getCreditLoanChange() {
		return creditLoanChange;
	}
	public void setCreditLoanChange(double creditLoanChange) {
		this.creditLoanChange = creditLoanChange;
	}
	
}
