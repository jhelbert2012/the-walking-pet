package com.zebra.constant.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zebra.constant.serializer.EnumSerializer;

@JsonSerialize(using = EnumSerializer.class)
public enum Color {
	B,
	C,
	FLO,
	G,
	LG,
	LPI,
	M,
	O,
	PI,
	PU,
	R,
	W,
	Y,
	GM,
	SM
	
}
