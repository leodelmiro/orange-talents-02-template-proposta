package com.leodelmiro.proposal.common.exception;

import com.leodelmiro.proposal.common.validation.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @Autowired
    MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> fieldErrorsList = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = String.format("Campo %s: %s", e.getField(), messageSource.getMessage(e, LocaleContextHolder.getLocale()));
            fieldErrorsList.add(message);
        });

        return new ErrorMessage(fieldErrorsList);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErrorMessage> handleApiErrorException(ApiErrorException exception) {
        List<String> fieldErrorsList = new ArrayList<>();
        fieldErrorsList.add(exception.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(fieldErrorsList);
        return ResponseEntity.status(exception.getHttpStatus()).body(errorMessage);
    }

}
