package com.zebra.constant.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zebra.constant.serializer.EnumSerializer;

@JsonSerialize(using = EnumSerializer.class)
public enum Region {
	APAC,
	EMEA,
	LATAM,
	NA;
}
