package com.zebra.model.annotation;

/**
 * Possible options for the annotation to auto generate methods for
 * DomainEntities
 * 
 * @author jgallardo
 * 
 */
public enum BusinessKeyMethod {
    ALL, NONE, EQUALS, HASH_CODE, TO_STRING
}
