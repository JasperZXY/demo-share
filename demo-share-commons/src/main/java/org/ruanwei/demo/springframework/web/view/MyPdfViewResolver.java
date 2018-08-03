package org.ruanwei.demo.springframework.web.view;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

@Component
public class MyPdfViewResolver implements ViewResolver {
	private static Log logger = LogFactory.getLog(MyPdfViewResolver.class);

	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		logger.debug("resolveViewName==================viewName="+viewName);
		
		MyPdfView view = new MyPdfView();
		return view;
	}

}
