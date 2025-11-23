package com.authh.springJwt.common.exception;


import com.authh.springJwt.common.message.CustomMessageSource;
import com.authh.springJwt.common.response.GlobalApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.PropertyValueException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.*;

import static com.authh.springJwt.common.constant.ErrorConstantValue.*;
import static com.authh.springJwt.common.constant.FieldErrorConstant.*;


@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_MESSAGE = "Error";
    private final CustomMessageSource customMessageSource;

    public CustomControllerAdvice(CustomMessageSource customMessageSource) {
        this.customMessageSource = customMessageSource;
    }

    // ------------------- Helper method -------------------
    private ResponseEntity<Object> buildResponse(String message, List<String> errors, HttpStatus status) {
        GlobalApiResponse apiResponse = new GlobalApiResponse(false, errors.get(0), null, null);
        return new ResponseEntity<>(apiResponse, new HttpHeaders(), status);
    }

    private ResponseEntity<Object> buildResponse(String message, String error, HttpStatus status) {
        return buildResponse(message, Collections.singletonList(error), status);
    }

    // ------------------- Validation -------------------
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String fieldName = error.getField().toLowerCase();
            String defaultMessage = error.getDefaultMessage();
            String param = getParameterValidation(error);
            String message = defaultMessage != null ? mapFieldError(defaultMessage, fieldName, param) : "Invalid field";
            errors.add(message);
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        return buildResponse("Validation failed", errors, HttpStatus.BAD_REQUEST);
    }

    private String getParameterValidation(FieldError error) {
        if (error != null && error.getArguments() != null) {
            int len = error.getArguments().length;
            if (len > 0) {
                return error.getArguments()[len - 1].toString();
            }
        }
        return "";
    }

    private String mapFieldError(String defaultMessage, String fieldName, String param) {
        return switch (defaultMessage) {
            case NOT_NULL -> customMessageSource.get(NOT_NULL, customMessageSource.get(fieldName));
            case NOT_BLANK -> customMessageSource.get(NOT_BLANK, customMessageSource.get(fieldName));
            case MIN_LENGTH -> customMessageSource.get(MIN_LENGTH, customMessageSource.get(fieldName), param);
            case MAX_LENGTH -> customMessageSource.get(MAX_LENGTH, customMessageSource.get(fieldName), param);
            case MIN_VALUE -> customMessageSource.get(MIN_VALUE, customMessageSource.get(fieldName), param);
            case MAX_VALUE -> customMessageSource.get(MAX_VALUE, customMessageSource.get(fieldName), param);
            case PATTERN -> customMessageSource.get(PATTERN, customMessageSource.get(fieldName), param);
            case EMAIL_VALIDATION -> customMessageSource.get(EMAIL_VALIDATION, customMessageSource.get(fieldName));
            case SIZE_VALIDATION -> customMessageSource.get(SIZE_VALIDATION, customMessageSource.get(fieldName));
            case PASSWORD_DID_NOT_MATCH ->
                    customMessageSource.get(PASSWORD_DID_NOT_MATCH, customMessageSource.get(fieldName));
            case EMAIL_ALREADY_USED -> customMessageSource.get(EMAIL_ALREADY_USED, customMessageSource.get(fieldName));
            case WEAK_PASSWORD -> customMessageSource.get(WEAK_PASSWORD, customMessageSource.get(fieldName));
            case OTP_NOT_FOUND_FOR_EMAIL ->
                    customMessageSource.get(OTP_NOT_FOUND_FOR_EMAIL, customMessageSource.get(fieldName));
            case INVALID_OTP -> customMessageSource.get(INVALID_OTP, customMessageSource.get(fieldName));
            case OTP_EXPIRED -> customMessageSource.get(OTP_EXPIRED, customMessageSource.get(fieldName));
            case USER_NOT_FOUND -> customMessageSource.get(USER_NOT_FOUND, customMessageSource.get(fieldName));
            case EMAIL_ALREADY_VERIFIED ->
                    customMessageSource.get(EMAIL_ALREADY_VERIFIED, customMessageSource.get(fieldName));
            case ERROR_NOT_FOUND -> customMessageSource.get(ERROR_NOT_FOUND, customMessageSource.get(fieldName));
            case ERROR_REQUIRED -> customMessageSource.get(ERROR_REQUIRED, customMessageSource.get(fieldName));
            case ERROR_EMAIL_NOT_VERIFIED ->
                    customMessageSource.get(ERROR_EMAIL_NOT_VERIFIED, customMessageSource.get(fieldName));
            case ERROR_INVALID_CREDENTIAL ->
                    customMessageSource.get(ERROR_INVALID_CREDENTIAL, customMessageSource.get(fieldName));
            case ERROR_EMAIL_NOT_FOUND ->
                    customMessageSource.get(ERROR_EMAIL_NOT_FOUND, customMessageSource.get(fieldName));
            case INVALID_REFRESH_TOKEN ->
                    customMessageSource.get(INVALID_REFRESH_TOKEN, customMessageSource.get(fieldName));
            case ERROR_INVALID_CLIENT_CREDENTIAL ->
                    customMessageSource.get(ERROR_INVALID_CLIENT_CREDENTIAL, customMessageSource.get(fieldName));
            case ERROR_AUTHENTICATION ->
                    customMessageSource.get(ERROR_AUTHENTICATION, customMessageSource.get(fieldName));
            case ERROR_VALIDATION -> customMessageSource.get(ERROR_VALIDATION, customMessageSource.get(fieldName));
            case NOT_FOUND -> customMessageSource.get(NOT_FOUND, customMessageSource.get(fieldName));
            case ERROR_ID_NOT_FOUND -> customMessageSource.get(ERROR_ID_NOT_FOUND, customMessageSource.get(fieldName));
            case ERROR_ALREADY_EXIST ->
                    customMessageSource.get(ERROR_ALREADY_EXIST, customMessageSource.get(fieldName));
            case ERROR_LOADING_PRIVATE_KEY ->
                    customMessageSource.get(ERROR_LOADING_PRIVATE_KEY, customMessageSource.get(fieldName));
            case EXPIRED_REFRESH_TOKEN ->
                    customMessageSource.get(EXPIRED_REFRESH_TOKEN, customMessageSource.get(fieldName));
            case ERROR_REQUEST_PARAMETER_MISSING ->
                    customMessageSource.get(ERROR_REQUEST_PARAMETER_MISSING, customMessageSource.get(fieldName));
            case ERROR_REQUEST_PART_MISSING ->
                    customMessageSource.get(ERROR_REQUEST_PART_MISSING, customMessageSource.get(fieldName));
            case ERROR_TYPE_MISMATCH ->
                    customMessageSource.get(ERROR_TYPE_MISMATCH, customMessageSource.get(fieldName));
            case ERROR_METHOD_ARGUMENT_MISMATCH ->
                    customMessageSource.get(ERROR_METHOD_ARGUMENT_MISMATCH, customMessageSource.get(fieldName));
            case ERROR_DUPLICATE_DATA ->
                    customMessageSource.get(ERROR_DUPLICATE_DATA, customMessageSource.get(fieldName));
            case ERROR_NAME_MUST_BE_UNIQUE ->
                    customMessageSource.get(ERROR_NAME_MUST_BE_UNIQUE, customMessageSource.get(fieldName));
            case ERROR_FOREIGN_KEY -> customMessageSource.get(ERROR_FOREIGN_KEY, customMessageSource.get(fieldName));
            case ERROR_CHECK_CONSTRAINT ->
                    customMessageSource.get(ERROR_CHECK_CONSTRAINT, customMessageSource.get(fieldName));
            case ERROR_METHOD_ARGUMENT_NOTNULL ->
                    customMessageSource.get(ERROR_METHOD_ARGUMENT_NOTNULL, customMessageSource.get(fieldName));
            case ERROR_EXPIRED_TOKEN ->
                    customMessageSource.get(ERROR_EXPIRED_TOKEN, customMessageSource.get(fieldName));
            case ERROR_INVALID_TOKEN ->
                    customMessageSource.get(ERROR_INVALID_TOKEN, customMessageSource.get(fieldName));
            case ERROR_BID_NOT_ACCEPTED ->
                    customMessageSource.get(ERROR_BID_NOT_ACCEPTED, customMessageSource.get(fieldName));
            case ERROR_INBOX_NOT_ENABLED ->
                    customMessageSource.get(ERROR_INBOX_NOT_ENABLED, customMessageSource.get(fieldName));

            default -> defaultMessage;
        };
    }

    // ------------------- Type & Parameter Errors -------------------
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = customMessageSource.get(ERROR_TYPE_MISMATCH, ex.getValue(), ex.getPropertyName(), ex.getRequiredType());
        return buildResponse(DEFAULT_MESSAGE, error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = customMessageSource.get(ERROR_REQUEST_PART_MISSING, ex.getRequestPartName());
        return buildResponse(DEFAULT_MESSAGE, error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = customMessageSource.get(ERROR_REQUEST_PARAMETER_MISSING, ex.getParameterName());
        return buildResponse(DEFAULT_MESSAGE, error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = customMessageSource.get(ERROR_METHOD_ARGUMENT_MISMATCH, ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getName());
        return buildResponse(DEFAULT_MESSAGE, error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<Object> handleDuplicateData(DuplicateDataException ex) {
        String error = customMessageSource.get(ERROR_DUPLICATE_DATA, ex.getFieldName());
        return buildResponse(DEFAULT_MESSAGE, error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return buildResponse("Validation failed", errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        Throwable cause = e.getCause();
        String message = DEFAULT_MESSAGE;
        if (cause instanceof PropertyValueException ex) {
            String violation = determineViolationType(ex.getMessage());
            message = determineMessage(violation, ex);
        }
        return buildResponse(message, message, HttpStatus.CONFLICT);
    }

    private String determineViolationType(String violation) {
        if (violation.contains("unique")) return "Unique Constraint Violation";
        if (violation.contains("foreign key")) return "Foreign Key Constraint Violation";
        if (violation.contains("check constraint")) return "Check Constraint Violation";
        if (violation.contains("null")) return "Not Null Constraint Violation";
        return "Unknown Constraint Violation";
    }

    private String determineMessage(String violation, PropertyValueException ex) {
        return switch (violation) {
            case "Unique Constraint Violation" ->
                    customMessageSource.get(ERROR_NAME_MUST_BE_UNIQUE, ex.getPropertyName());
            case "Foreign Key Constraint Violation" -> customMessageSource.get(ERROR_FOREIGN_KEY, ex.getPropertyName());
            case "Check Constraint Violation" -> customMessageSource.get(ERROR_CHECK_CONSTRAINT, ex.getPropertyName());
            case "Not Null Constraint Violation" ->
                    customMessageSource.get(ERROR_METHOD_ARGUMENT_NOTNULL, ex.getPropertyName());
            default -> "Unknown Constraint Violation";
        };
    }

    // ------------------- HTTP & Common -------------------
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        return buildResponse(DEFAULT_MESSAGE, error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod()).append(" method is not supported. Supported: ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));
        return buildResponse(DEFAULT_MESSAGE, builder.toString(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType()).append(" media type is not supported. Supported: ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));
        return buildResponse(DEFAULT_MESSAGE, builder.toString(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleConflict(RuntimeException ex) {
        return buildResponse(DEFAULT_MESSAGE, "Conflict occurred", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException e) {
        if (e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException cve) {
            return handleConstraintViolation(cve);
        }
        return buildResponse(DEFAULT_MESSAGE, e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IOException.class, ServiceException.class, RuntimeException.class})
    public ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
        return buildResponse(DEFAULT_MESSAGE, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        return buildResponse(DEFAULT_MESSAGE, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbidden(ForbiddenException ex) {
        return buildResponse(DEFAULT_MESSAGE, ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InboxNotEnabledException.class)
    public ResponseEntity<Object> handleInboxNotEnabled(ForbiddenException ex) {
        return buildResponse(DEFAULT_MESSAGE, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // ------------------- Other Custom Exceptions -------------------
    @ExceptionHandler({
            FailedToMoveFileException.class,
            SessionException.class,
            AlreadyExistsException.class,
            AlreadyVerifiedException.class,
            ValidationFailedException.class,
            InvalidRequestBodyException.class,
            InvalidFileTypeException.class,
            BadRequestException.class,
            ParseException.class
    })
    public ResponseEntity<Object> handleCustomExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof SessionException) status = HttpStatus.UNAUTHORIZED;
        else if (ex instanceof BadRequestException || ex instanceof InvalidRequestBodyException || ex instanceof InvalidFileTypeException)
            status = HttpStatus.BAD_REQUEST;
        else if (ex instanceof ValidationFailedException)
            status = HttpStatus.UNAUTHORIZED;
        else if (ex instanceof AlreadyExistsException || ex instanceof AlreadyVerifiedException)
            status = HttpStatus.CONFLICT;

        return buildResponse(DEFAULT_MESSAGE, ex.getMessage(), status);
    }

    @ExceptionHandler(CustomIllegalArgumentException2.class)
    public ResponseEntity<Object> handleCustomIllegalArgument(CustomIllegalArgumentException2 ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        GlobalApiResponse apiResponse = new GlobalApiResponse(
                false,
                ex.getMessage(),
                null,
                null
        );

        return new ResponseEntity<>(apiResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler(InvalidJobSalaryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidJobSalaryException(InvalidJobSalaryException ex) {
        final GlobalApiResponse apiError = new GlobalApiResponse(
                false,
                ex.getMessage(),
                null,
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JobUpdateNotAllowedException.class)
    public ResponseEntity<Object> handleJobUpdateNotAllowed(JobUpdateNotAllowedException ex) {
        final GlobalApiResponse apiError = new GlobalApiResponse(
                false,
                ex.getMessage(),
                null,
                null
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JobDeleteNotAllowedException.class)
    public ResponseEntity<Object> handleJobDeleteNotAllowed(JobDeleteNotAllowedException ex) {
        final GlobalApiResponse apiError = new GlobalApiResponse(
                false,
                ex.getMessage(),
                null,
                null
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(CardException.class)
//    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
//    public ResponseEntity<Object> handleCardException(Exception ex) {
//        Throwable cause = ex instanceof TransactionSystemException ? ex.getCause() : ex;
//
//        if (cause instanceof CardException cardEx) {
//            String messageKey = getMessageKeyFromException(cardEx);
//
//            String userMessage = customMessageSource.get(messageKey);
//            return buildResponse("Payment failed", userMessage, HttpStatus.PAYMENT_REQUIRED);
//        }
//
//        // fallback for anything unexpected
//        return buildResponse("Payment failed", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    private static String getMessageKeyFromException(CardException cardEx) {
//        String declineCode = cardEx.getDeclineCode();
//        return switch (declineCode) {
//            case "insufficient_funds" -> CARD_DECLINED_INSUFFICIENT;
//            case "expired_card" -> CARD_DECLINED_EXPIRED;
//            case "incorrect_cvc" -> CARD_DECLINED_CVC;
//            case "card_not_supported" -> CARD_DECLINED_NOT_SUPPORTED;
//            case "processing_error" -> CARD_DECLINED_PROCESSING;
//            default -> CARD_DECLINED_GENERIC;
//        };
//    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)      // 500 – server config problem
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        String msg = customMessageSource.get(AUTHENTICATION_FAILED);
        return buildResponse("Stripe authentication error", msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @ExceptionHandler(ApiConnectionException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)      // 500 – network / Stripe downtime
//    public ResponseEntity<Object> handleApiConnectionException(ApiConnectionException ex) {
//        String msg = customMessageSource.get(API_CONNECTION);
//        return buildResponse("Stripe connection error", msg, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @ExceptionHandler(JobApplicationStatusUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidStatusChange(JobApplicationStatusUpdateException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", false);
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BidAlreadyAcceptedException.class)
    public ResponseEntity<Object> handleBidAlreadyAccepted(BidAlreadyAcceptedException ex) {
        GlobalApiResponse apiResponse = new GlobalApiResponse(
                false,
                ex.getMessage(),
                null,
                null
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

}

