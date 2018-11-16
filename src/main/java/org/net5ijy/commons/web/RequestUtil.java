package org.net5ijy.commons.web;

import javax.servlet.http.HttpServletRequest;

/**
 * http请求工具类
 * 
 * @author 创建人：xuguofeng
 * @version 创建于：2018年9月17日 下午2:00:39
 */
public class RequestUtil {

	/**
	 * X-Requested-With请求头的key
	 */
	public static final String X_REQUESTED_WITH = "X-Requested-With";

	/**
	 * XMLHttpRequest
	 */
	public static final String XML_HTTP_REQUEST = "XMLHttpRequest";

	/**
	 * 判断请求是否是AJAX请求<br />
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月25日 上午9:56:36
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		// 获取X-Requested-With请求头
		String XMLHttpRequest = request.getHeader(X_REQUESTED_WITH);
		return XMLHttpRequest != null
				&& XML_HTTP_REQUEST.equals(XMLHttpRequest);
	}
}
