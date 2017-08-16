package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="round")
public class Round extends BaseEntity{

	private static final long serialVersionUID = 3035450235169761428L;

	private String name;
	
	private int asset;
	
	private int house;
	
	private int houseChange;
	
	private int etf;
	
	private int eftChange;
	
	private int hk;
	
	private int hkChange;
	
	private int bank;
	
	private int bankChange;
	
	private int status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAsset() {
		return asset;
	}

	public void setAsset(int asset) {
		this.asset = asset;
	}

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	public int getHouseChange() {
		return houseChange;
	}

	public void setHouseChange(int houseChange) {
		this.houseChange = houseChange;
	}

	public int getEtf() {
		return etf;
	}

	public void setEtf(int etf) {
		this.etf = etf;
	}

	public int getEftChange() {
		return eftChange;
	}

	public void setEftChange(int eftChange) {
		this.eftChange = eftChange;
	}

	public int getHk() {
		return hk;
	}

	public void setHk(int hk) {
		this.hk = hk;
	}

	public int getHkChange() {
		return hkChange;
	}

	public void setHkChange(int hkChange) {
		this.hkChange = hkChange;
	}

	public int getBank() {
		return bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public int getBankChange() {
		return bankChange;
	}

	public void setBankChange(int bankChange) {
		this.bankChange = bankChange;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
