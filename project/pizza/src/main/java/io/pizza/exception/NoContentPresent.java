package io.pizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class NoContentPresent extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoContentPresent(String message) {
		super(message);
	}

}
