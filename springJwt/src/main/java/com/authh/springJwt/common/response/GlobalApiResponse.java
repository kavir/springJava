package com.authh.springJwt.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Generic API response wrapper for REST endpoints.
 * <p>
 * Fields:
 * <ul>
 *   <li>status - Indicates success or failure of the API call.</li>
 *   <li>message - Human-readable message about the response.</li>
 *   <li>data - Payload of the response, generic type.</li>
 *   <li>error - List of error messages, if any.</li>
 * </ul>
 * <p>
 * Null fields are excluded from JSON serialization.
 *
 * @param <T> Type of the response payload.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalApiResponse<T> implements Serializable {
    private Boolean status;
    private String message;
    private T data;
    private List<String> error;
}
