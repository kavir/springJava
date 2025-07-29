package com.authh.springJwt.Utils;



import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMessageSource {

    private final MessageSource messageSource;


    public String get(String code) {
        return messageSource.getMessage(code, null, LocalThreatStorage.getLocale());
    }

    public String get(String code, Object... objects) {
        return messageSource.getMessage(code, objects, LocalThreatStorage.getLocale());
    }
}


