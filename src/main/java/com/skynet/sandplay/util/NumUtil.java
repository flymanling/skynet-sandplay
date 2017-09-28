package com.skynet.sandplay.util;

import java.math.BigDecimal;

public class NumUtil {

	public static Double getDoubleFormat(Double d) {
		BigDecimal b = new BigDecimal(d);
		return b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
	}
	
	public static void main(String[] args) {
		double d = 23.52941176470588;
		System.out.println(getDoubleFormat(d));
	}
}
