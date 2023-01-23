package com.nikitades.carres.entrypoint.error;

import com.nikitades.carres.application.exception.BadRequestException;
import com.nikitades.carres.application.exception.ForbiddenException;
import com.nikitades.carres.application.exception.NotFoundException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private Map<Class<? extends RuntimeException>, HttpStatus> statuses = Map.of(
    BadRequestException.class,
    HttpStatus.BAD_REQUEST,
    ForbiddenException.class,
    HttpStatus.FORBIDDEN,
    NotFoundException.class,
    HttpStatus.NOT_FOUND
  );

  @ExceptionHandler(value = { RuntimeException.class })
  protected ResponseEntity<ApiError> handle(RuntimeException e, WebRequest request) {
    return new ResponseEntity<>(new ApiError(e.getMessage()), statuses.get(e.getClass()));
  }
}
