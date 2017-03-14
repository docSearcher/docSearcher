package com.ustc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
	public static String convertDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(date);
		return dateString;
	}
	
	public static String convertDate(Date date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = sdf.format(date);
		return dateString;
	}
}
