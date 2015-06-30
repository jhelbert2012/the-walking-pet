package com.zebra.das.model.api;

import com.zebra.model.annotation.BusinessKey;
import com.zebra.model.annotation.BusinessKeyMethod;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@MappedSuperclass
public abstract class AuditableDomainEntity extends DomainEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CREATED_BY", updatable = false, nullable = false)
    @BusinessKey(include = BusinessKeyMethod.TO_STRING)
    @NotEmpty
    protected String createdBy;

    @Column(name = "CREATION_DATETIME", updatable = false, nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @BusinessKey(include = BusinessKeyMethod.TO_STRING)
    @NotNull
    protected DateTime creationDateTime;

    @Column(name = "MODIFIED_BY")
    @BusinessKey(include = BusinessKeyMethod.TO_STRING)
    protected String modifiedBy;

    @Column(name = "MODIFICATION_DATETIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @BusinessKey(include = BusinessKeyMethod.TO_STRING)
    protected DateTime modificationDateTime;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(DateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public DateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(DateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }
}
