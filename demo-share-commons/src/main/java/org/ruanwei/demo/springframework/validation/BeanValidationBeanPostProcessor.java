package org.ruanwei.demo.springframework.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.util.Counter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;

/**
 * @see org.springframework.validation.beanvalidation.BeanValidationPostProcessor
 * 
 * @author ruanwei
 *
 */
@Component
public class BeanValidationBeanPostProcessor implements BeanPostProcessor,
		Ordered, InitializingBean {
	private static final Logger logger = LogManager.getLogger();

	@Nullable
	private Validator validator;

	private boolean afterInitialization = false;

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		this.validator = validatorFactory.getValidator();
	}

	/**
	 * Choose whether to perform validation after bean initialization (i.e.
	 * after init methods) instead of before (which is the default).
	 * <p>
	 * Default is "false" (before initialization). Switch this to "true" (after
	 * initialization) if you would like to give init methods a chance to
	 * populate constrained fields before they get validated.
	 */
	public void setAfterInitialization(boolean afterInitialization) {
		this.afterInitialization = afterInitialization;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.validator == null) {
			this.validator = Validation.buildDefaultValidatorFactory()
					.getValidator();
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		logger.debug("postProcessBeforeInitialization=================="
				+ Counter.getCount() + beanName + "=" + bean);
		if (bean instanceof HandlerAdapter) {
			HandlerAdapter handlerAdapter = (HandlerAdapter) bean;
			logger.debug("handlerAdapter==================" + handlerAdapter);
			// 在这里定制RequestMappingHandlerAdapter，如webBindingInitializer、messageConverters等
		} else if (bean instanceof HandlerMapping) {
			HandlerMapping handlerMapping = (HandlerMapping) bean;
			logger.debug("handlerMapping==================" + handlerMapping);
			// 在这里定制RequestMappingHandlerMapping，如interceptors等
		} else if (bean instanceof HandlerExceptionResolver) {
			HandlerExceptionResolver handlerExceptionResolver = (HandlerExceptionResolver) bean;
			logger.debug("handlerExceptionResolver=================="
					+ handlerExceptionResolver);
		} else if (bean instanceof ViewResolver) {
			ViewResolver viewResolver = (ViewResolver) bean;
			logger.debug("viewResolver==================" + viewResolver);
		} else if (bean instanceof LocaleResolver) {
			LocaleResolver localeResolver = (LocaleResolver) bean;
			logger.debug("localeResolver==================" + localeResolver);
		} else if (bean instanceof ThemeResolver) {
			ThemeResolver themeResolver = (ThemeResolver) bean;
			logger.debug("themeResolver==================" + themeResolver);
		} else if (bean instanceof MultipartResolver) {
			MultipartResolver multipartResolver = (MultipartResolver) bean;
			logger.debug("multipartResolver=================="
					+ multipartResolver);
		} else if (bean instanceof FlashMapManager) {
			FlashMapManager flashMapManager = (FlashMapManager) bean;
			logger.debug("flashMapManager==================" + flashMapManager);
		} else if (bean instanceof FormattingConversionServiceFactoryBean) {
			FormattingConversionServiceFactoryBean conversionService = (FormattingConversionServiceFactoryBean) bean;
			logger.debug("conversionService=================="
					+ conversionService);
		} else if (bean instanceof ConversionService) {
			ConversionService conversionService = (ConversionService) bean;
			logger.debug("conversionService=================="
					+ conversionService);
		} else if (bean instanceof HandlerInterceptor) {
			HandlerInterceptor handlerInterceptor = (HandlerInterceptor) bean;
			logger.debug("handlerInterceptor=================="
					+ handlerInterceptor);
		} else if (bean instanceof WebRequestInterceptor) {
			WebRequestInterceptor webRequestInterceptor = (WebRequestInterceptor) bean;
			logger.debug("webRequestInterceptor=================="
					+ webRequestInterceptor);
		} else if (bean instanceof CallableProcessingInterceptor) {
			CallableProcessingInterceptor callableProcessingInterceptor = (CallableProcessingInterceptor) bean;
			logger.debug("callableProcessingInterceptor=================="
					+ callableProcessingInterceptor);
		}

		if (!this.afterInitialization) {
			doValidate(bean);
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		logger.debug("postProcessAfterInitialization=================="
				+ Counter.getCount() + beanName + "=" + bean);

		if (this.afterInitialization) {
			doValidate(bean);
		}
		return bean;
	}

	/**
	 * Perform validation of the given bean.
	 * 
	 * @param bean
	 *            the bean instance to validate
	 * @see javax.validation.Validator#validate
	 */
	protected void doValidate(Object bean) {
		Assert.state(this.validator != null, "No Validator set");
		Set<ConstraintViolation<Object>> result = this.validator.validate(bean);
		if (!result.isEmpty()) {
			throw new ConstraintViolationException(result);
		}
	}

	@Override
	public int getOrder() {
		logger.info("getOrder()");
		return 0;
	}

}
