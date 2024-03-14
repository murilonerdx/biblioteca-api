package org.astaron.bibliotecalogica.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class RestControllerHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationExceptions(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		return new ApiErrors(bindingResult);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleBusinessException(BusinessException e) {
		return new ApiErrors(e);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleCannotBeNull(IllegalArgumentException e) {
		return new ApiErrors(e);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(
						new Date(),
						ex.getMessage(),
						request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
