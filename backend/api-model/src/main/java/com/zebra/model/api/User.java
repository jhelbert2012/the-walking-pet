package com.zebra.model.api;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zebra.constant.app.SupportedLocale;
import com.zebra.model.annotation.BusinessKey;
import com.zebra.model.annotation.BusinessKeyMethod;

public class User extends HateoasSingleResource {

    private static final long serialVersionUID = 1L;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 50)
    private String username;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 50)
    private String password;

    @BusinessKey
    @NotNull
    private Principal principal;

    @NotNull
    @BusinessKey
    private SupportedLocale preferredLanguage = SupportedLocale.EN;

    @BusinessKey(include = BusinessKeyMethod.TO_STRING)
    private String sessionUUID;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public SupportedLocale getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(SupportedLocale preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getSessionUUID() {
        return sessionUUID;
    }

    public void setSessionUUID(String sessionUUID) {
        this.sessionUUID = sessionUUID;
    }

}
