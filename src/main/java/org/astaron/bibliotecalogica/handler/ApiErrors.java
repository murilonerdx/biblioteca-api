package org.astaron.bibliotecalogica.handler;

import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiErrors {
	private List<String> errors;

	public ApiErrors(BindingResult bindingResult){
		this.errors = new ArrayList<>();
		bindingResult.getAllErrors().forEach(error->this.errors.add(error.getDefaultMessage()));
	}

	public ApiErrors(Exception e) {
		this.errors = (Collections.singletonList(e.getMessage()));
	}

	public ApiErrors(IllegalArgumentException e) {
		this.errors = (Collections.singletonList(e.getMessage()));
	}

	public ApiErrors(ResponseStatusException e) {
		this.errors = Collections.singletonList(e.getReason());
	}

	public List<String> getErrors() {
		return errors;
	}
}
