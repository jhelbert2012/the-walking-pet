package com.zebra.das.model.api;

import com.zebra.model.annotation.BusinessKey;
import com.zebra.model.annotation.BusinessKeyMethod;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class DomainEntity extends AbstractDomainEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false)
    @BusinessKey(include = BusinessKeyMethod.ALL)
    protected Long id;

    @Version
    @Column(name = "VERSION")
    @BusinessKey(include = BusinessKeyMethod.TO_STRING)
    protected Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
