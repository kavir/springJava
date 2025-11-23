package com.authh.springJwt.common.controller;


import com.authh.springJwt.common.message.CustomMessageSource;
import com.authh.springJwt.common.response.GlobalApiResponse;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Abstract base controller providing common API response methods for derived controllers.
 * <p>
 * This class offers standardized success and error response builders using {@link GlobalApiResponse}.
 * It also provides access to a custom message source for internationalization or custom messages.
 * </p>
 *
 * <ul>
 *   <li>{@code successResponse(String message, Object data)}: Builds a success response with a message and optional data.</li>
 *   <li>{@code successResponse(String message)}: Builds a success response with a message only.</li>
 *   <li>{@code errorResponse(String message, Object errors)}: Builds an error response with a message and optional error details.</li>
 * </ul>
 *
 * Extend this class in your controllers to ensure consistent API responses.
 */
public abstract class BaseController {

    /**
     * API success and error status constants
     */
    protected static final boolean API_SUCCESS_STATUS = true;
    protected static final boolean API_ERROR_STATUS = false;

    /**
     * Message source instance for internationalization or custom messages
     */
    @Autowired
    protected CustomMessageSource customMessageSource;

    // --- Global API Responses ---

    protected GlobalApiResponse<Object> successResponse(String message, Object data) {
        return GlobalApiResponse.builder()
                .status(API_SUCCESS_STATUS)
                .message(message)
                .data(data)
                .build();
    }

    protected GlobalApiResponse<Object> successResponse(String message) {
        return successResponse(message, null);
    }

    protected GlobalApiResponse<Object> errorResponse(String message, Object errors) {
        return GlobalApiResponse.builder()
                .status(API_ERROR_STATUS)
                .message(errors.toString())
                .data(null)
                .build();
    }
}
