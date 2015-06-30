package com.zebra.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be used to auto generate hashcode, equals and toString methods
 * in DomainEntities.
 * 
 * @author jgallardo
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface BusinessKey {
    BusinessKeyMethod[] include() default BusinessKeyMethod.ALL;

    BusinessKeyMethod[] exclude() default BusinessKeyMethod.NONE;
}
