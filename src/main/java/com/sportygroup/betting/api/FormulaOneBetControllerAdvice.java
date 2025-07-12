package com.sportygroup.betting.api;

import com.sportygroup.betting.usecase.UnableToPlaceBetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = {FormulaOneResource.class})
public class FormulaOneBetControllerAdvice extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FormulaOneBetControllerAdvice.class);

  @ExceptionHandler(exception = DataIntegrityViolationException.class)
  public ProblemDetail handleDataIntegrityViolationException(final DataIntegrityViolationException exception) {
    final var message = "wallet balance insufficient to place bet";
    LOGGER.atWarn().setMessage(message + ". Exception details: '{}'").addArgument(exception.getMessage()).log();
    return ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, message);
  }

  @ExceptionHandler(exception = UnableToPlaceBetException.class)
  public ProblemDetail handleUnableToPlaceBetException(final UnableToPlaceBetException exception) {
    LOGGER.atWarn().setMessage("Error: '{}'").addArgument(exception.getMessage()).log();
    return ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, exception.getMessage());
  }

  @ExceptionHandler(exception = Exception.class)
  public ProblemDetail handleException(final Exception exception) {
    LOGGER.atError().setMessage("Unhandled exception: Details: '{}'").addArgument(exception.getMessage()).log();
    return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "unable to handle request");
  }
}
