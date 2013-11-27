package org.pshow.ecm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter {

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
		try {
			calendar.setTime(dateFm.parse(reader.getValue()));
		} catch (ParseException e) {
			throw new ConversionException(e.getMessage(), e);
		}
		return calendar.getTime();
	}

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return Date.class == type;
	}
}