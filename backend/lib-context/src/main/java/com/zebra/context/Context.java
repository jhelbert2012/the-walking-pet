package com.zebra.context;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public final class Context implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String sessionId;

    private final String userId;

    /**
     * Create a context containing a sessionId and UserId
     * 
     * @param sessionId
     *            Session id
     * @param userId
     *            User id
     */
    public Context(final String sessionId, final String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    /**
     * Create a context containing a sessionId only
     * 
     * @param sessionId
     *            Session id
     */
    public Context(final String sessionId) {
        this(sessionId, null);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Context rhs = (Context) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(sessionId, rhs.sessionId)
                .append(userId, rhs.userId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(sessionId).append(userId).toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return builder.toString();
    }

}