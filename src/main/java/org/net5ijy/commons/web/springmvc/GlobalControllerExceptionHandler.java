package org.net5ijy.commons.web.springmvc;

import org.net5ijy.commons.web.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * org.net5ijy.springcloud.admin.service.controller.GlobalControllerExceptionHandler 类
 *
 * @author xuguofeng
 * @date 2019/5/29 10:12
 */
@RestControllerAdvice(annotations = { RestController.class })
public class GlobalControllerExceptionHandler {

	/**
	 * 全局异常类，默认返回HTTP 500
	 *
	 * @param e
	 *            异常
	 * @return 系统封装返回结构
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseMessage handleException(Exception e) {
		return ResponseMessage.error().addAttribute("errorMessage",
				e.getMessage());
	}
}
