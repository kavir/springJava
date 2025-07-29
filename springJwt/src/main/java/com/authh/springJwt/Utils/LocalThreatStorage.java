package com.authh.springJwt.Utils;

import java.util.Locale;

public class LocalThreatStorage {

    private static final ThreadLocal<Locale> locale = new ThreadLocal<>();

    public static void setLocale(Locale locale) {
        LocalThreatStorage.locale.set(locale);
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static void clear() {
        locale.remove();
    }

}