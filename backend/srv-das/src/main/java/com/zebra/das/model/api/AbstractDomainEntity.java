package com.zebra.das.model.api;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.zebra.model.util.DomainEntityUtils;

@MappedSuperclass
public abstract class AbstractDomainEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object obj) {
        return DomainEntityUtils.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return DomainEntityUtils.hashCode(this);
    }

    @Override
    public String toString() {
        return DomainEntityUtils.toString(this);
    }

}
