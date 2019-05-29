package org.net5ijy.commons.web.springmvc;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 时间格式转换器
 *
 * @author xuguofeng
 * @date 2019/5/27 12:55
 */
public class DateConverter implements Converter<String, Date> {

	private DateTimeFormatter ymdhms = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss");

	private Pattern ymdPattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
	private Pattern ymdhmsPattern = Pattern
			.compile("^\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}$");

	private ZoneId zoneId = ZoneId.systemDefault();

	@Override
	public Date convert(String source) {
		try {
			if (ymdPattern.matcher(source).find()) {
				LocalDateTime dateTime = LocalDateTime.parse(source
						+ " 00:00:00", ymdhms);
				return localDatetimeToDate(dateTime);
			}
			if (ymdhmsPattern.matcher(source).find()) {
				LocalDateTime dateTime = LocalDateTime.parse(source, ymdhms);
				return localDatetimeToDate(dateTime);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		throw new IllegalArgumentException("时间格式不正确");
	}

	private Date localDatetimeToDate(LocalDateTime dateTime) {
		ZonedDateTime zdt = dateTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}
}
