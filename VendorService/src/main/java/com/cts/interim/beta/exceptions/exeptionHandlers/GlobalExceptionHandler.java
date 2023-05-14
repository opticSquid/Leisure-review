package com.cts.interim.beta.exceptions.exeptionHandlers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.interim.beta.exceptions.DataCouldNotbeSavedException;
import com.cts.interim.beta.exceptions.UserNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(UserNotValidException.class)
	public ProblemDetail UserNotValidHandler(UserNotValidException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
				ex.getCause().getMessage());
		problem.setTitle(ex.getErrorMessage());
		return problem;
	}
	@ExceptionHandler(DataCouldNotbeSavedException.class)
	public ProblemDetail DataCouldNotbeSavedHandler(DataCouldNotbeSavedException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
				ex.getCause().getMessage());
		problem.setTitle(ex.getErrorMessage());
		return problem;
	}
}
