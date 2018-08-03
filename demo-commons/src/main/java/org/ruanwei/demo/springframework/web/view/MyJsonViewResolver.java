package org.ruanwei.demo.springframework.web.view;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Component
public class MyJsonViewResolver implements ViewResolver {
	private static Log logger = LogFactory.getLog(MyJsonViewResolver.class);

	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		logger.debug("resolveViewName==================viewName="+viewName);
		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setPrettyPrint(true); // Lay the JSON out to be nicely readable
		return view;
	}

}
