package com.zebra.constant.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zebra.constant.serializer.EnumSerializer;

@JsonSerialize(using = EnumSerializer.class)
public enum RibbonColor {
        LM,
	FC,
	MCBLK,
	MCW,
	MCR,
	MCBLU,
	MCMG,
	MCMS,
	MCG,
	MCS,
	MCSO,
        CL,
        HL
	
}
