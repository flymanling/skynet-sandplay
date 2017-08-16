package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="roundset")
public class RoundSet extends BaseEntity{

	private static final long serialVersionUID = -6200757596830282898L;

	private String year;
	
	private int housePrice;
	
	private int etfPrice;
	
	private int hkPrice;
	
	private int bankPrice;
	
	private Double financeRate;
	
	private Double houseRate;
	
	private Double creditRate;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	public int getEtfPrice() {
		return etfPrice;
	}

	public void setEtfPrice(int etfPrice) {
		this.etfPrice = etfPrice;
	}

	public int getHkPrice() {
		return hkPrice;
	}

	public void setHkPrice(int hkPrice) {
		this.hkPrice = hkPrice;
	}

	public int getBankPrice() {
		return bankPrice;
	}

	public void setBankPrice(int bankPrice) {
		this.bankPrice = bankPrice;
	}

	public Double getFinanceRate() {
		return financeRate;
	}

	public void setFinanceRate(Double financeRate) {
		this.financeRate = financeRate;
	}

	public Double getHouseRate() {
		return houseRate;
	}

	public void setHouseRate(Double houseRate) {
		this.houseRate = houseRate;
	}

	public Double getCreditRate() {
		return creditRate;
	}

	public void setCreditRate(Double creditRate) {
		this.creditRate = creditRate;
	}
	
}
