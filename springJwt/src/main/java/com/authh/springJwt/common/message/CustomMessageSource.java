package com.authh.springJwt.common.message;

import com.authh.springJwt.common.locale.LocaleThreadStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMessageSource {

    private final MessageSource messageSource;


    public String get(String code) {
        return messageSource.getMessage(code, null, LocaleThreadStorage.getLocale());
    }

    public String get(String code, Object... objects) {
        return messageSource.getMessage(code, objects, LocaleThreadStorage.getLocale());
    }


}