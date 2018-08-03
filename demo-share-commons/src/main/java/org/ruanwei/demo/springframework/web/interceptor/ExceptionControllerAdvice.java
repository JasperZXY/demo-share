package org.ruanwei.demo.springframework.web.interceptor;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.core.exception.InvalidArgumentException;
import org.ruanwei.demo.core.exception.InvalidLogicException;
import org.ruanwei.demo.core.exception.ServiceException;
import org.ruanwei.demo.springframework.web.core.ResponseCode;
import org.ruanwei.demo.springframework.web.core.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classes annotated with @ControllerAdvice or @Controller can contain
 *
 * @author ruanwei
 * @ExceptionHandler,@InitBinder, and @ModelAttribute annotated methods, and
 *                                these methods will apply to @RequestMapping
 *                                methods across all controller hierarchies as
 *                                opposed to the controller hierarchy within
 *                                which they are declared.<br/>
 *
 *                                For controllers relying on view resolution,
 *                                JSONP is automatically enabled when the
 *                                request has a query parameter named jsonp or
 *                                callback. Those names can be customized
 *                                through jsonpParameterNames property.
 */
@ControllerAdvice
// @RestControllerAdvice
public class ExceptionControllerAdvice {
	private static final Logger logger = LogManager.getLogger();

	protected String errorPath = "generic_error";

	public String getErrorPath() {
		return errorPath;
	}

	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

	/**
	 * 1.Through Spring’s WebDataBinder, you can: <li>use @InitBinder-annotated
	 * methods within your controller, <li>use @InitBinder methods within an @ControllerAdvice
	 * class, <li>or provide a custom WebBindingInitializer for
	 * RequestMappingHandlerAdapter.
	 *
	 * 2.Registering Formatters with the FormattingConversionService.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest webRequest) {
		logger.debug("initBinder================== req=" + webRequest);
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// dateFormat.setLenient(false);
		// binder.registerCustomEditor(Date.class, new
		// CustomDateEditor(dateFormat, false));
		// binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
		// binder.addValidators(validators);
	}

	@ModelAttribute("cities")
	public List<String> modelAttribute(Model model) {
		logger.debug("modelAttribute==================");
		List<String> cityList = new ArrayList<String>();
		cityList.add("a");
		cityList.add("b");
		cityList.add("c");
		model.addAttribute("cities2", cityList);
		return cityList;
	}

	@ExceptionHandler(Exception.class)
	// handled by ExceptionHandlerExceptionResolver
	public String handleSpringException(Throwable e,
			HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes attr) throws Exception {
		// logger.error("handleSpringException===================" +
		// request.getRequestURL(), e);
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(request.getRequestURI());
		if (request.getMethod().equalsIgnoreCase("GET")) {
			String queryStr = request.getQueryString();
			if (!StringUtils.isEmpty(queryStr)) {
				urlBuilder.append("?").append(queryStr);
			}
		} else if (request.getParameterNames() != null) {
			urlBuilder.append(" param:{");
			Enumeration<String> enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String name = enumeration.nextElement();
				urlBuilder.append(name).append(":")
						.append(request.getParameter(name)).append(",");
			}
			urlBuilder.deleteCharAt(urlBuilder.length() - 1);
			urlBuilder.append("}");
		}

		String url = urlBuilder.toString();

		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (Exception ex) {
			logger.info("URLDecoder error:" + ex.getMessage());
		}

		Result result = new Result();
		if (e instanceof InvalidArgumentException) {
			logger.warn(
					"handleSpringException InvalidArgumentException url:{}, msg:{}",
					url, e.getMessage());
			result.setError(ResponseCode.PARAM_ERROR.getCode(),
					"参数异常：" + e.getMessage());
		} else if (e instanceof InvalidLogicException) {
			logger.warn(
					"handleSpringException InvalidLogicException url:{}, msg:{}",
					url, e.getMessage());
			result.setError(ResponseCode.SESSION_INVALID.getCode(),
					"登录异常：" + e.getMessage());
		} else if (e instanceof ServiceException) {
			ServiceException re = (ServiceException) e;
			logger.warn(
					"handleSpringException ServiceException url:{}, code:{} msg:{}",
					url, re.getCode(), e.getMessage());
			result.setError(re.getCode(), re.getMessage());
		} else if (e instanceof BindException) {
			BindException be = (BindException) e;
			StringBuilder errorBuilder = new StringBuilder("参数异常：");
			for (ObjectError oe : be.getAllErrors()) {
				if (oe instanceof FieldError) {
					FieldError fe = (FieldError) oe;
					// errorBuilder.append("\n[").append(fe.getField()).append("]").append(fe.getDefaultMessage());
					errorBuilder.append(oe.getDefaultMessage()).append("；");
				} else {
					errorBuilder.append(oe.getDefaultMessage()).append("；");
				}
			}
			errorBuilder.deleteCharAt(errorBuilder.length() - 1);
			logger.warn("handleSpringException BindException url:{}, msg:{}",
					url, e.getMessage());
			result.setError(ResponseCode.PARAM_ERROR.getCode(),
					errorBuilder.toString());
		} else if (e instanceof ConstraintViolationException) {
			ConstraintViolationException ce = (ConstraintViolationException) e;
			StringBuilder errorBuilder = new StringBuilder("参数异常：");
			ce.getConstraintViolations().forEach(
					ev -> errorBuilder.append(ev.getMessage()).append("；"));
			errorBuilder.deleteCharAt(errorBuilder.length() - 1);
			result.setError(ResponseCode.PARAM_ERROR.getCode(),
					errorBuilder.toString());
		} else {
			logger.error("handleSpringException " + url, e);
			result.setError(ResponseCode.ERROR);
		}

		model.addAttribute("success", result.isSuccess());
		model.addAttribute("code", result.getCode());
		model.addAttribute("message", result.getMessage());
		// model.addAttribute("data", result);
		return errorPath;
		// attr.addAttribute("result", result);
		// return "redirect:/generic_error";

		/*
		 * // 1.From
		 * BeanValidationBeanPostProcessor/MethodValidationPostProcessor. if (e
		 * instanceof ConstraintViolationException) { // ValidationException
		 * throw new InvalidArgumentException("ConstraintViolationException",
		 * e); } // 2.From @Valid @RequestBody else if (e instanceof
		 * MethodArgumentNotValidException) { throw new
		 * InvalidArgumentException("MethodArgumentNotValidException", e); } //
		 * 3.BindException by default(if no BindingResult argument). else if (e
		 * instanceof BindException) { throw new
		 * InvalidArgumentException("BindException", e); } // 4. From Web Layer.
		 * else if (e instanceof WebException) { throw new
		 * InvalidLogicException("WebException", e); } // 5. From Service Layer.
		 * else if (e instanceof ServiceException) { throw new
		 * InvalidLogicException("ServiceException", e); } // 6. From
		 * Integration Layer(DataAccess). else if (e instanceof
		 * DataAccessException) { throw new
		 * InvalidStateException("DataAccessException", e); } // 7. From
		 * Integration Layer(Remoting). else if (e instanceof
		 * RemoteAccessException) { throw new
		 * InvalidStateException("RemoteAccessException", e); } // 8.From
		 * Unknown. else if (e instanceof NullPointerException) { throw new
		 * IllegalStateException("NullPointerException", e); } // 9.From Unknown
		 * except NPE. else if (e instanceof RuntimeException) { throw new
		 * IllegalStateException("RuntimeException", e); } //
		 * ServletException-ServletRequestBindingException: //
		 * MissingPathVariableException/MissingServletRequestParameterException/
		 * UnsatisfiedServletRequestParameterException else if (e instanceof
		 * ServletException) { // JasperException throw new
		 * IllegalStateException("ServletException", e); } else { throw new
		 * IllegalStateException("IllegalStateException", e); }
		 */
	}
}
