package org.ruanwei.demo.springframework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.util.Counter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TraceHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Called after HandlerMapping determined an appropriate handler object, but
	 * before HandlerAdapter invokes the handler.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// ServletRequestUtils.getIntParameter(request, name, defaultVal);
		logger.debug("preHandle[1,6]=================="
				+ request.getRequestURL());

		// 注意这里会调用两次，正好说明了异步的过程
		if (request.getAttribute("startTime") == null) {
			long startTime = System.currentTimeMillis();
			request.setAttribute("startTime", startTime);
		}

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			logger.debug("HandlerMapping has determined an appropriate handler object===:"
					+ handlerMethod);
		}

		return true;
	}

	/**
	 * Called after HandlerAdapter actually invoked the handler, but before the
	 * DispatcherServlet renders the view.Can expose additional model objects to
	 * the view via the given ModelAndView.
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("postHandle[9]================== url="
				+ request.getRequestURL());

		long midTime = System.currentTimeMillis();
		request.setAttribute("midTime", midTime);
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			logger.debug("HandlerAdapter has invoked the handler===:"
					+ handlerMethod + " modelAndView=" + modelAndView);
		}
	}

	/**
	 * Callback after completion of request processing, that is, after rendering
	 * the view.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("afterCompletion[10]=================="
				+ Counter.getCount() + " url=" + request.getRequestURL());

		long startTime = (Long) request.getAttribute("startTime");
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			logger.debug("handlerMethod=" + handlerMethod + " ex=" + ex);
		}
		logger.debug("completion of request processing===:"
				+ (System.currentTimeMillis() - startTime));
	}

	/**
	 * Called instead of postHandle and afterCompletion, when the handler is
	 * being executed concurrently.Invoked after the start of asynchronous
	 * request handling.
	 */
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("afterConcurrentHandlingStarted[3]================== url="
				+ request.getRequestURL());
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			logger.debug("handlerMethod=" + handlerMethod);
		}
	}
}
