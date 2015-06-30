package com.zebra.das.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.type.Type;
import org.joda.time.DateTime;
import com.zebra.context.Context;
import com.zebra.context.ContextHolder;
import com.zebra.das.model.api.AuditableDomainEntity;

public class EntityInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;

	private static final String USER_UNKNOWN = "unknown";

	@Override
	public Object instantiate(String entityName, EntityMode entityMode,
			Serializable id) {
		return super.instantiate(entityName, entityMode, id);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		if (entity instanceof AuditableDomainEntity) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("creationDateTime".equals(propertyNames[i])) {
					state[i] = new DateTime();
				}
				if ("createdBy".equals(propertyNames[i])) {
					state[i] = getUserIdFromSession();
				}
				if ("modificationDateTime".equals(propertyNames[i])) {
					state[i] = new DateTime();
				}
				if ("modifiedBy".equals(propertyNames[i])) {
					state[i] = getUserIdFromSession();
				}
			}

		}
		return false;
	}

	/**
	 * Gets the user id from session.
	 * 
	 * @return the user id from session
	 */
	private String getUserIdFromSession() {

		Context context = ContextHolder.getContext();

		if (context != null && context.getUserId() != null) {
			return context.getUserId();
		} else {
			return USER_UNKNOWN;
		}
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {

		if (entity instanceof AuditableDomainEntity) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("modificationDateTime".equals(propertyNames[i])) {
					currentState[i] = new DateTime();
				}
				if ("modifiedBy".equals(propertyNames[i])) {
					currentState[i] = getUserIdFromSession();
				}
			}
		}
		return false;
	}

}
