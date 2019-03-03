package br.com.uol.testbackendjava.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.uol.testbackendjava.service.PlayerService;

public class PlayerUniqueValidator implements ConstraintValidator<Unique, String> {
	@Autowired
	private PlayerService service;
	
	private String field;

	@Override
	public void initialize(Unique unique) {
		field = unique.field();
		unique.message();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(service != null && service.exists(field, value)) {
			return false;
		}
		return true;
	}
}
