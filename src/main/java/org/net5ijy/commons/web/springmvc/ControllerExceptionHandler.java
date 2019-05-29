package org.net5ijy.commons.web.springmvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net5ijy.commons.web.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * org.net5ijy.springcloud.admin.web.exception.ControllerExceptionHandler 类
 *
 * @author xuguofeng
 * @date 2019/5/29 11:19
 */
@RestControllerAdvice(annotations = { RestController.class })
public class ControllerExceptionHandler {

	private static final String X_REQUESTED_WITH = "X-Requested-With";

	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseMessage handleControllerException(
			HttpServletRequest request, HttpServletResponse response,
			Exception ex) throws ServletException, IOException {

		String ajaxFlag = request.getHeader(X_REQUESTED_WITH);

		// 如果是AJAX请求，返回ResponseMessage
		if (XML_HTTP_REQUEST.equals(ajaxFlag)) {
			return ResponseMessage.error().addAttribute("errorMessage",
					ex.getMessage());
		}
		// 如果不是AJAX请求，转发到error50x页面
		request.getRequestDispatcher("/error50x").forward(request, response);
		return null;
	}
}
