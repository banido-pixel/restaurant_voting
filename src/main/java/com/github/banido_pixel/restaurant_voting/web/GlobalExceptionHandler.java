package com.github.banido_pixel.restaurant_voting.web;

import com.github.banido_pixel.restaurant_voting.error.AppException;
import com.github.banido_pixel.restaurant_voting.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DUPLICATE_MENU_ITEM_NAME = "MenuItem with this name and date already exists";
    public static final String EXCEPTION_DUPLICATE_RESTAURANT_NAME = "Restaurant with this name already exists";
    public static final String EXCEPTION_SECOND_VOTE_PER_DATE = "Today vote has already been spent";

    private static final Map<String, String> CONSTRAINTS_MAP = Map.of(
            "uk_restaurant_name", EXCEPTION_DUPLICATE_RESTAURANT_NAME,
            "uk_restaurant_menu_datetime", EXCEPTION_DUPLICATE_MENU_ITEM_NAME,
            "uk_user_vote", EXCEPTION_SECOND_VOTE_PER_DATE
    );

    private final ErrorAttributes errorAttributes;

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return handleBindingErrors(ex.getBindingResult(), request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return handleBindingErrors(ex.getBindingResult(), request);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appException(WebRequest request, AppException ex) {
        log.error("ApplicationException: {}", ex.getMessage());
        return createResponseEntity(getDefaultBody(request, ex.getOptions(), null), ex.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<?> notFoundException(WebRequest request, Exception ex) {
        log.error("EntityNotFoundException: {}", ex.getMessage());
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(MESSAGE), null), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflictException(WebRequest request, DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        String rootMsg = ValidationUtil.getRootCause(ex).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINTS_MAP.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(MESSAGE), entry.getValue()),
                            HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
        }
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(MESSAGE), null),
                HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> handleBindingErrors(BindingResult result, WebRequest request) {
        String msg = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.defaults(), msg), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private Map<String, Object> getDefaultBody(WebRequest request, ErrorAttributeOptions options, String msg) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, options);
        if (msg != null) {
            body.put("message", msg);
        }
        return body;
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponseEntity(Map<String, Object> body, HttpStatus status) {
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        return (ResponseEntity<T>) ResponseEntity.status(status).body(body);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.error("Exception", ex);
        super.handleExceptionInternal(ex, body, headers, status, request);
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(), ValidationUtil.getRootCause(ex).getMessage()), status);
    }
}
