package org.pshow.ecm.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

public class DateConvert implements Converter {
	private static String dateFormatStr = "yyyy/MM/dd";
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			dateFormatStr);

	private static String dateLongFormatStr = dateFormatStr + " HH:mm:ss";
	private static SimpleDateFormat dateTimeLongFormat = new SimpleDateFormat(
			dateLongFormatStr);

	@SuppressWarnings("rawtypes")
	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		}
		String className = value.getClass().getName();
		// java.sql.Timestamp
		if ("java.sql.Timestamp".equalsIgnoreCase(className)) {
			try {
				SimpleDateFormat df = new SimpleDateFormat(dateFormatStr
						+ " HH:mm:ss");
				return df.parse(dateTimeLongFormat.format(value));
			} catch (Exception e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
					return df.parse(dateTimeFormat.format(value));
				} catch (ParseException ex) {
					e.printStackTrace();
					return null;
				}
			}
		} else {// java.util.Date,java.sql.Date
			String p = value.toString();
			if (p == null || p.trim().length() == 0) {
				return null;
			}
			try {
				SimpleDateFormat df = new SimpleDateFormat(dateFormatStr
						+ " HH:mm:ss");
				return df.parse(p.trim());
			} catch (Exception e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
					return df.parse(p.trim());
				} catch (ParseException ex) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	public static String formatDateTime(Object obj) {
		if (obj != null)
			return dateTimeFormat.format(obj);
		else
			return "";
	}

	public static String formatLongDateTime(Object obj) {
		if (obj != null)
			return dateTimeLongFormat.format(obj);
		else
			return "";
	}

}