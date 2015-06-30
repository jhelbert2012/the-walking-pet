package com.zebra.constant.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zebra.constant.serializer.EnumSerializer;

@JsonSerialize(using = EnumSerializer.class)
public enum MaterialType {
	P,
	S,
	W,
	WR,
	R,
	WPVC,
	CPVC,
	RPVC,
	COMP,
	T;
}
