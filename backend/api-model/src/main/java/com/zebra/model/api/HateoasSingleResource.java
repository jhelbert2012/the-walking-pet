package com.zebra.model.api;

import java.io.Serializable;

import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

import com.zebra.model.annotation.BusinessKey;
import com.zebra.model.validation.ExternalValidationGroup;
import com.zebra.model.validation.InternalValidationGroup;

public abstract class HateoasSingleResource extends HateoasResource implements
        Serializable {

    private static final long serialVersionUID = 1L;

    @BusinessKey
    @Null(groups = ExternalValidationGroup.class)
    @NotEmpty(groups = InternalValidationGroup.class)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
