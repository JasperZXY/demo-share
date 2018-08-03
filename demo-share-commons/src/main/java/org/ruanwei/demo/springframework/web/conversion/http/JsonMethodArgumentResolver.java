package org.ruanwei.demo.springframework.web.conversion.http;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.core.exception.InvalidArgumentException;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import com.alibaba.fastjson.JSON;

public class JsonMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		logger.debug("supportsParameter================== parameter=" + parameter);
		if (!parameter.hasParameterAnnotation(JsonParam.class)) {
			return false;
		}
		if (Map.class.isAssignableFrom(parameter.nestedIfOptional().getNestedParameterType())) {
			JsonParam jsonParam = parameter.getParameterAnnotation(JsonParam.class);
			return (jsonParam != null && StringUtils.hasText(jsonParam.value()));
		}
		return true;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		JsonParam ann = parameter.getParameterAnnotation(JsonParam.class);
		return (ann != null ? new JsonParamNamedValueInfo(ann) : new JsonParamNamedValueInfo());
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		logger.debug("resolveName================== name=" + name + parameter);
		Class<?> paramType = parameter.getParameterType();
		String json = request.getParameter(name);
		if (StringUtils.isEmpty(json)) {
			return paramType.newInstance();
		}

		try {
			Object arg = JSON.parseObject(json, paramType);
			if (arg != null) {
				return arg;
			}
		} catch (Exception e) {
			logger.error("parse " + json + " to " + paramType + " failed.", e);
			throw new InvalidArgumentException("参数解析失败");
		}

		return paramType.newInstance();
	}

	private static class JsonParamNamedValueInfo extends NamedValueInfo {

		public JsonParamNamedValueInfo() {
			super("", false, ValueConstants.DEFAULT_NONE);
		}

		public JsonParamNamedValueInfo(JsonParam annotation) {
			super(annotation.name(), annotation.required(), annotation.defaultValue());
		}
	}

}
