package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="round")
public class Round extends BaseEntity{

	private static final long serialVersionUID = 3035450235169761428L;

	private String name;
	
	private Integer asset;
	
	private Integer house;
	
	private Integer houseChange;
	
	private Integer etf;
	
	private Integer eftChange;
	
	private Integer hk;
	
	private Integer hkChange;
	
	private Integer bank;
	
	private Integer bankChange;
	
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAsset() {
		return asset;
	}

	public void setAsset(Integer asset) {
		this.asset = asset;
	}

	public Integer getHouse() {
		return house;
	}

	public void setHouse(Integer house) {
		this.house = house;
	}

	public Integer getHouseChange() {
		return houseChange;
	}

	public void setHouseChange(Integer houseChange) {
		this.houseChange = houseChange;
	}

	public Integer getEtf() {
		return etf;
	}

	public void setEtf(Integer etf) {
		this.etf = etf;
	}

	public Integer getEftChange() {
		return eftChange;
	}

	public void setEftChange(Integer eftChange) {
		this.eftChange = eftChange;
	}

	public Integer getHk() {
		return hk;
	}

	public void setHk(Integer hk) {
		this.hk = hk;
	}

	public Integer getHkChange() {
		return hkChange;
	}

	public void setHkChange(Integer hkChange) {
		this.hkChange = hkChange;
	}

	public Integer getBank() {
		return bank;
	}

	public void setBank(Integer bank) {
		this.bank = bank;
	}

	public Integer getBankChange() {
		return bankChange;
	}

	public void setBankChange(Integer bankChange) {
		this.bankChange = bankChange;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
