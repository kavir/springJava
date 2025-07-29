package com.authh.springJwt.Utils;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
@Scope("prototype")
public class GlobalApiResponse implements Serializable {
    private Boolean status;
    private String message;
    private Object data;
    private List<String> error;
}
