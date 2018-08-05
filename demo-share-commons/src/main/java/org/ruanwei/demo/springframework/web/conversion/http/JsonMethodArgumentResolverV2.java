package org.ruanwei.demo.springframework.web.conversion.http;

import com.alibaba.fastjson.JSON;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.core.exception.InvalidArgumentException;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

import java.util.Map;


public class JsonMethodArgumentResolverV2 extends ModelAttributeMethodProcessor {

    private static final Logger logger = LogManager.getLogger();


    public JsonMethodArgumentResolverV2(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }


    public JsonMethodArgumentResolverV2() {
        super(false);
    }


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
    public boolean supportsReturnType(MethodParameter returnType) {
        return false;
    }


    @Override
    protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        String value = getJsonValueForAttribute(parameter, request);
        if (value != null) {
            Object attribute = createAttributeFromJsonValue(value, attributeName, parameter, binderFactory, request);
            if (attribute != null) {
                return attribute;
            }
        }

        return super.createAttribute(attributeName, parameter, binderFactory, request);
    }


    protected Object createAttributeFromJsonValue(String sourceValue, String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        Object retObj;
        try {
            retObj = JSON.parseObject(sourceValue, parameter.getParameterType());
        } catch (Exception e) {
            logger.warn("parseObject fail. sourceValue:{} msg:{}", sourceValue, e.getMessage());
            throw new InvalidArgumentException("解析失败");
        }

        DataBinder binder = binderFactory.createBinder(request, retObj, attributeName);
        binder.validate(retObj);
        BindingResult bindingResult = binder.getBindingResult();
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return retObj;
    }


    protected String getJsonValueForAttribute(MethodParameter parameter, NativeWebRequest request) {
        JsonParam jsonParam = parameter.getParameterAnnotation(JsonParam.class);
        return request.getParameter(jsonParam.value());
    }

}
