package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="roundplay")
public class RoundPlay extends BaseEntity{

	private static final long serialVersionUID = 1361486812248783663L;

	private String name;
	
	private String setId;
	
	private int rank;
	
	private int status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSetId() {
		return setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
