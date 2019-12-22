package com.hurynovich.api_tester.model.validation;

import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ValidationResult {

	private ValidationResultType type;

	private List<String> descriptions;

	public ValidationResultType getType() {
		return type;
	}

	public void setType(final ValidationResultType type) {
		this.type = type;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public String getDescriptionsAsString() {
		if (!CollectionUtils.isEmpty(descriptions)) {
			return String.join("; ", descriptions);
		} else {
			return "";
		}
	}

	public void setDescriptions(final List<String> descriptions) {
		this.descriptions = descriptions;
	}

}
