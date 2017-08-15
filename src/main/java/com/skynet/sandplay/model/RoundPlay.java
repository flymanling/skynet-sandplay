package com.skynet.sandplay.model;

import javax.persistence.Entity;

@Entity(name="roundplay")
public class RoundPlay extends BaseEntity{

	private static final long serialVersionUID = 1361486812248783663L;

	private String name;
	
	private String setId;
	
	private Integer index;
	
	private Integer status;

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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
