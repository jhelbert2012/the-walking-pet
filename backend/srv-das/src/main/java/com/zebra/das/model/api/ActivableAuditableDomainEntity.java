package com.zebra.das.model.api;

import com.zebra.model.annotation.BusinessKey;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class ActivableAuditableDomainEntity extends AuditableDomainEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = "ACTIVE", nullable = false, columnDefinition = "tinyint")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @NotNull
    @BusinessKey
    protected Boolean active;

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
