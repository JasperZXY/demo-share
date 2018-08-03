package org.ruanwei.demo.springframework.web.interceptor;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

public class TraceCallableProcessingInterceptor implements CallableProcessingInterceptor {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Invoked before the start of concurrent handling in the original thread in
	 * which the Callable is submitted for concurrent handling.
	 */
	@Override
	public <T> void beforeConcurrentHandling(NativeWebRequest request,
			Callable<T> task) throws Exception {
		logger.debug("beforeConcurrentHandling[2]================== task="
				+ task);
		//long startTime = (long)request.getAttribute("startTime", RequestAttributes.SCOPE_REQUEST);
		logger.debug("RequestAttributes="/*+startTime*/);
	}

	/**
	 * Invoked after the start of concurrent handling in the async thread in
	 * which the Callable is executed and before the actual invocation of the
	 * Callable.
	 */
	@Override
	public <T> void preProcess(NativeWebRequest request, Callable<T> task)
			throws Exception {
		logger.debug("preProcess[4async]================== task=" + task);
	}

	/**
	 * Invoked after the Callable has produced a result in the async thread in
	 * which the Callable is executed. This method may be invoked later than
	 * afterTimeout or afterCompletion depending on when the Callable finishes
	 * processing.
	 */
	@Override
	public <T> void postProcess(NativeWebRequest request, Callable<T> task,
			Object concurrentResult) throws Exception {
		logger.debug("postProcess[5async]================== task=" + task
				+ " concurrentResult=" + concurrentResult);
	}

	/**
	 * Invoked from a container thread when the async request times out before
	 * the Callable task completes.
	 */
	@Override
	public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task)
			throws Exception {
		logger.debug("handleTimeout================== task=" + task);
		return RESULT_NONE;
	}

	/**
	 * Invoked from a container thread when async processing completes for any
	 * reason including timeout or network error.
	 */
	@Override
	public <T> void afterCompletion(NativeWebRequest request, Callable<T> task)
			throws Exception {
		logger.debug("afterCompletion[11]====================" + task);
	}
}
