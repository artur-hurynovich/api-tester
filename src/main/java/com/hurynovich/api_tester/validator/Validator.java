package com.hurynovich.api_tester.validator;

import com.hurynovich.api_tester.model.validation.ValidationResult;

public interface Validator<T> {

	ValidationResult validate(T t);

}
