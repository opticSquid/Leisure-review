package com.cts.interim.beta.exceptions.exeptionHandlers;

import java.io.IOException;

import org.hibernate.ResourceClosedException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.interim.beta.exceptions.DataCouldNotbeSavedException;
import com.cts.interim.beta.exceptions.ResourceNotFoundException;
import com.cts.interim.beta.exceptions.UserNotValidException;
import com.cts.interim.beta.exceptions.UserOperationNotPermitted;

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

	@ExceptionHandler(UserOperationNotPermitted.class)
	public ProblemDetail UserOpNotPermittedHandler(UserOperationNotPermitted ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getErrorMsg());
		problem.setTitle("Operation not permitted");
		return problem;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ProblemDetail ResourceNotFoundHandler(ResourceNotFoundException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
		problem.setTitle("Requested resource was not found");
		return problem;
	}

	@ExceptionHandler(IOException.class)
	public ProblemDetail IOExceptionHandler(IOException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),
				"Image could not be uploaded due to some internal error");
		problem.setTitle("Image upload failed");
		return problem;
	}
}
