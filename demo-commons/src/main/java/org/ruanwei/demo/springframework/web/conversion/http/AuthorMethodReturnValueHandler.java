package org.ruanwei.demo.springframework.web.conversion.http;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthorMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		logger.debug("supportsReturnType================== returnType=" + returnType);
		return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), Author.class)
				|| returnType.hasMethodAnnotation(Author.class));
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		logger.debug("handleReturnValue================== returnType=" + returnType + returnValue);
		mavContainer.setRequestHandled(true);

		Author author = returnType.getMethodAnnotation(Author.class);
		String name = StringUtils.isEmpty(author.name()) ? author.defaultValue() : author.name();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Author", name);
		headers.add("Made_in_China", "Yes");

		HttpServletResponse servletResponse = webRequest.getNativeResponse(HttpServletResponse.class);
		ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(servletResponse);
		outputMessage.getHeaders().putAll(headers);
		outputMessage.getBody(); // flush headers
	}

}
