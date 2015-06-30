package com.zebra.constant.app;

import java.util.Locale;

public enum SupportedLocale {
    EN(new Locale("en")),
    ES(new Locale("es"));

    private final Locale locale;

    SupportedLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
