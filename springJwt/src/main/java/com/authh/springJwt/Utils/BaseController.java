package com.authh.springJwt.Utils;

import org.springframework.beans.factory.annotation.Autowired;


public class BaseController {

    /**
     * Global API Response Instance
     */
    @Autowired
    protected GlobalApiResponse globalApiResponse;


    /**
     * API Success Status
     */
    protected final boolean API_SUCCESS_STATUS = true;

    /**
     * API Error Status
     */
    protected final boolean API_ERROR_STATUS = false;

    /**
     * Message Source Instance
     */
    @Autowired
    protected CustomMessageSource customMessageSource;


    /**
     * Function that sends successful API Response
     *
     * @param message
     * @param data
     * @return
     */

    protected GlobalApiResponse successResponse(String message, Object data) {
        return GlobalApiResponse.builder()
                .status(API_SUCCESS_STATUS)
                .message(message)
                .data(data)
                .build();
    }

    protected GlobalApiResponse successResponse(String message) {
        return GlobalApiResponse.builder()
                .status(API_SUCCESS_STATUS)
                .message(message)
                .data(null)
                .build();
    }

    protected GlobalApiResponse errorResponse(String message, Object errors) {
        return GlobalApiResponse.builder()
                .status(API_ERROR_STATUS)
                .message(message)
                .data(errors)
                .build();
    }


    public GlobalApiResponse globalApiResponse(String message, Object data) {
        return GlobalApiResponse
                .builder()
                .message(message)
                .data(data)
                .build();

    }







    /**
     * Function that sends error API Response
     *
     * @param message
     * @param errors
     * @return
     */
//    protected GlobalApiResponse errorResponse(String message, Object errors) {
//        globalApiResponse.setStatus(API_ERROR_STATUS);
//        globalApiResponse.setMessage(message);
//        globalApiResponse.setData(errors);
//        return globalApiResponse;
//    }
}
