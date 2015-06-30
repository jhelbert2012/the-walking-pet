/**
 * 
 */
package com.zebra.constant.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zebra.constant.serializer.EnumSerializer;

/**
 * @author ignaciogonzalez
 *
 */
@JsonSerialize(using = EnumSerializer.class)
public enum PrinterType {
	
	Empty(""),
	Tabletop("Tabletop"),
	Desktop("Desktop");
	
	private final String code;
	
	private PrinterType(String code){
		this.code = code;
	}

}
