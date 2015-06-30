package com.zebra.model.api;

import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zebra.constant.web.WebElementType;
import com.zebra.model.annotation.BusinessKey;

public class Principal extends HateoasSingleResource {

    private static final long serialVersionUID = 1L;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 50)
    private String name;

    @BusinessKey
    private Set<WebElementType> rights;

    public Principal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WebElementType> getRights() {
        return rights;
    }

    public void setRights(Set<WebElementType> rights) {
        this.rights = rights;
    }

}
