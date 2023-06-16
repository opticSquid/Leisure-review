package com.cts.interim_project.Reviews.and.Streaks.Exceptions.handlers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.interim_project.Reviews.and.Streaks.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Reviews.and.Streaks.Exceptions.UserNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(UserNotValidException.class)
	public ProblemDetail UserNotValidHandler(UserNotValidException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
				ex.getCause().getMessage());
		problem.setTitle(ex.getErrorMessage());
		return problem;
	}

	@ExceptionHandler(ProviderNotFoundException.class)
	public ProblemDetail ProviderNotFoundHandler(ProviderNotFoundException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
		problem.setTitle("Vendor not found");
		return problem;
	}
}
