package com.skynet.sandplay.form;

public class RoundForm {

	private int id;
	
	private int round;
	/**
	 * 是否预览
	 */
	private int view;
	
	/**
	 * 保险
	 */
	private Double insureChange;
	/**
	 * 银行理财
	 */
	private Double bankChange;
	/**
	 * 黄金
	 */
	private Double goldChange;
	/**
	 * 中国香港
	 */
	private Double hkChange;
	/**
	 * etf
	 */
	private Double etfChange;
	/**
	 * 浦发银行
	 */
	private Double pufaChange;
	/**
	 * 信托
	 */
	private Double trustChange;
	/**
	 * 住宅
	 */
	private Integer houseChange;
	/**
	 * 商业用地
	 */
	private Integer landChange;
	/**
	 * 信用贷款
	 */
	private Double creditLoanChange;
	
	public int getRound() {
		return round;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public Double getInsureChange() {
		return insureChange;
	}
	public void setInsureChange(Double insureChange) {
		this.insureChange = insureChange;
	}
	public Double getBankChange() {
		return bankChange;
	}
	public void setBankChange(Double bankChange) {
		this.bankChange = bankChange;
	}
	public Double getGoldChange() {
		return goldChange;
	}
	public void setGoldChange(Double goldChange) {
		this.goldChange = goldChange;
	}
	public Double getHkChange() {
		return hkChange;
	}
	public void setHkChange(Double hkChange) {
		this.hkChange = hkChange;
	}
	public Double getEtfChange() {
		return etfChange;
	}
	public void setEtfChange(Double etfChange) {
		this.etfChange = etfChange;
	}
	public Double getPufaChange() {
		return pufaChange;
	}
	public void setPufaChange(Double pufaChange) {
		this.pufaChange = pufaChange;
	}
	public Double getTrustChange() {
		return trustChange;
	}
	public void setTrustChange(Double trustChange) {
		this.trustChange = trustChange;
	}
	public Integer getHouseChange() {
		return houseChange;
	}
	public void setHouseChange(Integer houseChange) {
		this.houseChange = houseChange;
	}
	public Integer getLandChange() {
		return landChange;
	}
	public void setLandChange(Integer landChange) {
		this.landChange = landChange;
	}
	public Double getCreditLoanChange() {
		return creditLoanChange;
	}
	public void setCreditLoanChange(Double creditLoanChange) {
		this.creditLoanChange = creditLoanChange;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
}
