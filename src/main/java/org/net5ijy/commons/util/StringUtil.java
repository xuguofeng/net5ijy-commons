package org.net5ijy.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串、时间工具类
 * 
 * @author 创建人：xuguofeng
 * @version 创建于：2018年7月3日 上午11:48:36
 */
public class StringUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * 首字母转大写
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月3日 上午11:49:02
	 * @param str
	 * @return
	 */
	public static String captureName(String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		char[] cs = str.toCharArray();
		if (cs[0] > 'z' || cs[0] < 'a') {
			return str;
		}
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	/**
	 * 验证字符串是否为null或去除前后空白后长度为0
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年5月19日 下午1:03:03
	 * @param str
	 * @return true - 为空<br />
	 *         false - 不为空
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 把字符串转为Integer, 当转型失败时返回defaultVal
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月4日 上午10:44:06
	 * @param str
	 *            - 字符串
	 * @param defaultVal
	 *            - 默认值，数值转换失败时返回
	 * @return
	 */
	public static Integer getInteger(String str, Integer defaultVal) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}

	/**
	 * 把字符串转为Integer, 当转型失败时返回0
	 * 
	 * @author 创建人：administrator
	 * @version 创建于：2018年11月16日 下午1:11:10
	 * @param str
	 * @return
	 */
	public static Integer getInteger(String str) {
		return getInteger(str, 0);
	}

	/**
	 * 使用指定的格式格式化时间
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月5日 上午8:28:35
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDatetime(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 使用yyyy-MM-dd HH:mm:ss格式格式化时间
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月5日 上午8:28:35
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return formatDatetime(date, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 使用yyyy-MM-dd格式格式化日期
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月5日 上午8:29:28
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDatetime(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 使用HH:mm:ss格式格式化时间
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月5日 上午8:30:12
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return formatDatetime(date, DEFAULT_TIME_FORMAT);
	}

	/**
	 * 根据指定的格式把字符串转为时间
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年10月13日 上午8:59:40
	 * @param formatDate
	 * @param format
	 * @return
	 */
	public static Date parseDateTime(String formatDate, String format) {
		try {
			return new SimpleDateFormat(format).parse(formatDate);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 根据yyyy-MM-dd HH:mm:ss格式把字符串转为时间
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年10月13日 上午9:02:37
	 * @param formatDate
	 * @return
	 */
	public static Date parseDateTime(String formatDate) {
		return parseDateTime(formatDate, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 根据yyyy-MM-dd格式把字符串转为日期
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年10月13日 上午9:02:37
	 * @param formatDate
	 * @return
	 */
	public static Date parseDate(String formatDate) {
		return parseDateTime(formatDate, DEFAULT_DATE_FORMAT);
	}
}
