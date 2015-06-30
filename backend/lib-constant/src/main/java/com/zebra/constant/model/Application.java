package com.zebra.constant.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zebra.constant.serializer.EnumSerializer;

@JsonSerialize(using = EnumSerializer.class)
public enum Application {
	AM,
	CA,
	DM,
	DSD,
	EC,
	FS,
	HS,
	K,
	L,
	MPOS,
	MPRINT,
	MWF,
	PM,
	M,
	POS,
	RFID,
	SC,
	T,
	WM,
	LC,
	SII;
}
