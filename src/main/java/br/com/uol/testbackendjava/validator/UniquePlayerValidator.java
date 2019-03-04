package br.com.uol.testbackendjava.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

import br.com.uol.testbackendjava.service.PlayerService;

public class UniquePlayerValidator implements ConstraintValidator<Unique, String> {
	@Autowired
	private PlayerService service;

	@Override
	public void initialize(Unique unique) {
		unique.message();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		ConstraintValidatorContextImpl contextImpl = (ConstraintValidatorContextImpl) context;
		String field = contextImpl.getConstraintViolationCreationContexts().get(0).getPath().toString();
		if(service != null && !StringUtils.isEmptyOrWhitespace(value) && service.exists(field, value)) {
			return false;
		}
		return true;
	}
}
