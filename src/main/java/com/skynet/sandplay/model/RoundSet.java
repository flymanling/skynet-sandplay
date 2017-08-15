package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="roundset")
public class RoundSet extends BaseEntity{

	private static final long serialVersionUID = -6200757596830282898L;

	private String year;
	
	private Integer housePrice;
	
	private Integer etfPrice;
	
	private Integer hkPrice;
	
	private Integer bankPrice;
	
	private Double financeRate;
	
	private Double houseRate;
	
	private Double creditRate;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(Integer housePrice) {
		this.housePrice = housePrice;
	}

	public Integer getEtfPrice() {
		return etfPrice;
	}

	public void setEtfPrice(Integer etfPrice) {
		this.etfPrice = etfPrice;
	}

	public Integer getHkPrice() {
		return hkPrice;
	}

	public void setHkPrice(Integer hkPrice) {
		this.hkPrice = hkPrice;
	}

	public Integer getBankPrice() {
		return bankPrice;
	}

	public void setBankPrice(Integer bankPrice) {
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
