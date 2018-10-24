package com.gcit.lms.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Genre already exists in database.")
public class GenreAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 5936995542768806252L;
}
