package org.ruanwei.demo.springframework.web.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;

public class TraceDeferredResultProcessingInterceptor implements DeferredResultProcessingInterceptor {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public <T> void beforeConcurrentHandling(NativeWebRequest request, DeferredResult<T> task) throws Exception {
		logger.debug("beforeConcurrentHandling[2]================== task=" + task);
		//long startTime = (long) request.getAttribute("startTime", RequestAttributes.SCOPE_REQUEST);
		logger.debug("RequestAttributes="/* + startTime*/);
	}

	@Override
	public <T> void preProcess(NativeWebRequest request, DeferredResult<T> task) throws Exception {
		logger.debug("preProcess[4async]================== task=" + task);
	}

	@Override
	public <T> void postProcess(NativeWebRequest request, DeferredResult<T> task, Object concurrentResult)
			throws Exception {
		logger.debug("postProcess[5async]================== task=" + task + " concurrentResult=" + concurrentResult);
	}

	@Override
	public <T> boolean handleTimeout(NativeWebRequest request, DeferredResult<T> task) throws Exception {
		logger.debug("handleTimeout================== task=" + task);
		return true;
	}

	@Override
	public <T> void afterCompletion(NativeWebRequest request, DeferredResult<T> task) throws Exception {
		logger.debug("afterCompletion[11]====================" + task);
	}
}
