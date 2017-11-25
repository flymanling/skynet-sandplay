package com.skynet.sandplay.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="roundStart")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RoundStart extends Round{

	private static final long serialVersionUID = 3035450235169761428L;
	
	public static int INCOME = 20;

	
	
}
