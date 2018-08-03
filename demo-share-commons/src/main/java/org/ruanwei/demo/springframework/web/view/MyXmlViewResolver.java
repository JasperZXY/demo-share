package org.ruanwei.demo.springframework.web.view;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

@Component
public class MyXmlViewResolver implements ViewResolver {
	private static Log logger = LogFactory.getLog(MyXmlViewResolver.class);

	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		logger.debug("resolveViewName==================viewName="+viewName);
		
		MappingJackson2XmlView view = new MappingJackson2XmlView();
		view.setPrettyPrint(true); // Lay the XML out to be nicely readable
		return view;
	}

}
