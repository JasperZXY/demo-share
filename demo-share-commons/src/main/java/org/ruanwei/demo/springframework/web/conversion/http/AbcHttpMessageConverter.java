package org.ruanwei.demo.springframework.web.conversion.http;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class AbcHttpMessageConverter extends AbstractHttpMessageConverter<Abc> {
	private static final Logger logger = LogManager.getLogger();

	@Override
	protected boolean supports(Class<?> clazz) {
		logger.debug("supports===" + clazz);
		return Abc.class.isAssignableFrom(clazz);
	}

	@Override
	protected Abc readInternal(Class<? extends Abc> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		logger.debug("readInternal===" + clazz + inputMessage);
		return new Abc();
	}

	@Override
	protected void writeInternal(Abc t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		logger.debug("writeInternal===" + t + outputMessage);
	}

}
