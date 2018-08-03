package org.ruanwei.demo.springframework.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.core.exception.DataAccessException;
import org.ruanwei.demo.core.exception.InvalidArgumentException;
import org.ruanwei.demo.core.exception.InvalidLogicException;
import org.ruanwei.demo.core.exception.RemoteAccessException;
import org.ruanwei.demo.core.exception.ServiceException;
import org.ruanwei.demo.springframework.web.core.ResponseCode;
import org.ruanwei.demo.springframework.web.core.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ErrorController {
	private static final Logger logger = LogManager.getLogger();

	@GetMapping("testException/{type}")
	public void testException(@PathVariable int type) throws Throwable {
		logger.debug("testException=" + type);
		if (type == 1) {
			// throw new WebException("Web Exception, type=",
			// HttpStatus.BAD_REQUEST);
		} else if (type == 2) {
			throw new ServiceException("Service Exception, type=", type);
		} else if (type == 3) {
			throw new DataAccessException("Data Access Exception, type=", type);
		} else if (type == 4) {
			throw new RemoteAccessException("Remote Access Exception, type=", type);
		} else if (type == 5) {
			throw new NullPointerException("NullPointer Exception, type=" + type);
		} else if (type == 6) {
			throw new RuntimeException("Runtime(Unchecked) Exception, type=" + type);
		} else if (type == 7) {
			throw new IOException("IOException, type=" + type);
		} else if (type == 8) {
			throw new Exception("(Checked) Exception, type=" + type);
		} else if (type == 9) {
			throw new Error("Error, type=" + type);
		} else if (type == 10) {
			throw new InvalidArgumentException("不支持的参数类型");
		} else if (type == 11) {
			throw new InvalidLogicException("登录态失效");
		}
		throw new Throwable("Throwable, type=" + type);
	}

	// 1.来自Servlet容器的异常：包含404和500(例如JSP异常)
	// 2.来自Spring的异常：包含用户在框架中抛出的异常(例如@ExceptionHandler)
	//@RequestMapping(path = "/error", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	//@ResponseBody
	public Map<String, Object> error(HttpServletRequest request) {
		logger.error("error");

		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");

		int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
		String message = (String) request.getAttribute("javax.servlet.error.message");

		Class<?> exceptionType = (Class<?>) request.getAttribute("javax.servlet.error.exception_type");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		logger.error("servletName=" + servletName + " requestUri=" + requestUri + " statusCode=" + statusCode
				+ " message=[" + message + "]" + " exceptionType=" + exceptionType, throwable);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", -1);
		map.put("reason", message);
		map.put("statusCode", statusCode);

		return map;
	}

	@RequestMapping(path = "/error")
	public String error2(HttpServletRequest request, Model model) {
		Object statusCode = request.getAttribute("javax.servlet.error.status_code");
		Object reason = request.getAttribute("javax.servlet.error.message");
		Object code = request.getAttribute("code");
		Object message = request.getAttribute("message");

		if (statusCode != null && "404".equals(statusCode.toString())) {
			code = ResponseCode.URL_NO_FOUND.getCode();
			message = ResponseCode.URL_NO_FOUND.getMessage();
		}

		if (code == null) {
			code = ResponseCode.ERROR.getCode();
		}
		if (message == null) {
			message = ResponseCode.ERROR.getMessage();
		}

		model.addAttribute("success", false);
		model.addAttribute("statusCode", statusCode);
		model.addAttribute("code", code);
		model.addAttribute("reason", reason);
		model.addAttribute("message", message);

		Object result = request.getAttribute("result");
		if (result != null && result instanceof Result) {
			Result retResult = (Result)result;
			model.addAttribute("code", retResult.getCode());
			model.addAttribute("message", retResult.getMessage());
		}

		return "generic_error";
	}
}
