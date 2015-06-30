package com.zebra.constant.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zebra.constant.serializer.EnumSerializer;

@JsonSerialize(using = EnumSerializer.class)
public enum Dmar {
	CDW,
	PCC,
        MID,
        AB_RFID,
        AH
}
