package org.ruanwei.demo.springframework.web.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.AsyncWebRequestInterceptor;
import org.springframework.web.context.request.WebRequest;

public class TraceWebRequestInterceptor implements AsyncWebRequestInterceptor {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void preHandle(WebRequest request) throws Exception {
		logger.debug("preHandle================== url=" + request);
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		logger.debug("postHandle================== url=" + request);
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex)
			throws Exception {
		logger.debug("afterCompletion================== url=" + request);
	}

	@Override
	public void afterConcurrentHandlingStarted(WebRequest request) {
		logger.debug("afterConcurrentHandlingStarted================== url=" + request);
	}
}
