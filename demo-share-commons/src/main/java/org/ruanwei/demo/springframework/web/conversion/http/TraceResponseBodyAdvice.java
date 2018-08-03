package org.ruanwei.demo.springframework.web.conversion.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.util.Counter;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class TraceResponseBodyAdvice implements ResponseBodyAdvice<Object> {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public boolean supports(MethodParameter returnType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		logger.debug("supports[7]==================" + Counter.getCount()
				+ " returnType=" + returnType + " converterType="
				+ converterType);
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
			MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		logger.debug("beforeBodyWrite[8]==================" + Counter.getCount()
				+ " returnType=" + returnType + " selectedContentType="
				+ selectedContentType + " selectedConverterType="
				+ selectedConverterType);
		return body;
	}

}
