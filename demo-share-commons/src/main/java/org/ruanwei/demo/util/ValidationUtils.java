package org.ruanwei.demo.util;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

public class ValidationUtils {
	private static Log log = LogFactory.getLog(ValidationUtils.class);

	public static <T> void validate1(T t, Validator validator) {

		if (validator == null) {
			return;
		}

		Set<ConstraintViolation<T>> constraintViolations = validator
				.validate(t);
		for (ConstraintViolation<T> violation : constraintViolations) {
			log.info("validation1==========" + violation.getMessage());
		}
	}

	public static <T> void validate2(T t, ValidatorFactory validatorFactory) {
		if (validatorFactory == null) {
			// validatorFactory = Validation
			// .byProvider(HibernateValidator.class)
			// .configure()
			// .messageInterpolator(
			// new ResourceBundleMessageInterpolator(
			// new PlatformResourceBundleLocator(
			// "message/validate")))
			// .failFast(false).buildValidatorFactory();
		}

		if (validatorFactory == null) {
			validatorFactory = Validation.buildDefaultValidatorFactory();
		}

		Validator validator = validatorFactory.getValidator();

		validate1(t, validator);
	}

	public static void validateBySpring(Object target,
			MessageSource messageSource,
			MessageCodesResolver messageCodesResolver,
			org.springframework.validation.Validator... validators) {
		DataBinder dataBinder = new DataBinder(target);

		// dataBinder.setConversionService(conversionService);
		// dataBinder.bind(pvs);

		dataBinder.setMessageCodesResolver(messageCodesResolver);
		dataBinder.addValidators(validators);
		dataBinder.validate();

		BindingResult bindingResult = dataBinder.getBindingResult();
		if (bindingResult.hasGlobalErrors()) {
			for (ObjectError error : bindingResult.getGlobalErrors()) {
				log.info("globalError==========" + error);
			}
		}

		if (bindingResult.hasFieldErrors()) {
			List<FieldError> FieldErrorList = bindingResult.getFieldErrors();
			for (FieldError fieldError : FieldErrorList) {
				log.info("fieldError==========" + fieldError);
				log.info("object==========" + fieldError.getObjectName());
				log.info("field==========" + fieldError.getField());
				log.info("rejectedValue=========="
						+ fieldError.getRejectedValue());
				log.info("code==========" + fieldError.getCode());

				for (Object arg : fieldError.getArguments()) {
					log.info("argument==========" + arg);
				}

				log.info("defaultMessage=========="
						+ fieldError.getDefaultMessage());
				String message = messageSource.getMessage(fieldError.getCode(),
						fieldError.getArguments(),
						fieldError.getDefaultMessage(), Locale.US);
				log.info("message==========" + message);
				String[] msgCodes = bindingResult.resolveMessageCodes(
						fieldError.getCode(), fieldError.getField());
			}
		}
	}

	public static void validateBySpring2(Object target,
			MessageSource messageSource,
			org.springframework.validation.Validator validator) {

		Errors errors = new BeanPropertyBindingResult(target, "People");
		validator.validate(target, errors);
		org.springframework.validation.ValidationUtils.invokeValidator(
				validator, target, errors);
	}

	// ===========以下挪到AOP===============
	/*
	 * Method method = null; try { method = People2.class.getMethod("sayHello",
	 * String.class); } catch (NoSuchMethodException | SecurityException e) {
	 * e.printStackTrace(); }
	 * 
	 * ExecutableValidator executableValidator = validator.forExecutables();
	 * Set<ConstraintViolation<People2>> constraintViolations =
	 * executableValidator.validateParameters(people, method, new Object[] { ""
	 * }); for (ConstraintViolation<People2> violation : constraintViolations) {
	 * log.info("validation2==========" + violation.getMessage()); Path path =
	 * violation.getPropertyPath(); Iterator<Node> nodes = path.iterator();
	 * MethodNode methodNode = nodes.next().as(MethodNode.class);
	 * log.info("methodName==========" + methodNode.getName()); while
	 * (nodes.hasNext()) { ParameterNode parameterNode =
	 * nodes.next().as(ParameterNode.class); log.info("parameterName=========="
	 * + parameterNode.getName()); } }
	 * 
	 * constraintViolations = executableValidator.validateReturnValue(people,
	 * method, new Object[] { null }); for (ConstraintViolation<People2>
	 * violation : constraintViolations) { log.info("validation3==========" +
	 * violation.getMessage()); }
	 */
	// ===========以上挪到AOP===============
}
