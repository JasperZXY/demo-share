package org.ruanwei.demo.springframework.web.conversion.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Classes annotated with @ControllerAdvice or @Controller can contain
 *
 * @author ruanwei
 */
@ControllerAdvice
// @RestControllerAdvice
public class JsonpResponseBodyAdvice extends AbstractJsonpResponseBodyAdvice { // extends
	// ResponseEntityExceptionHandler

	private static final Logger logger = LogManager.getLogger();

	public JsonpResponseBodyAdvice() {
		super("callback");
	}

	public JsonpResponseBodyAdvice(String callback) {
		super(callback);
	}

}
