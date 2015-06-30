package com.zebra.constant.serializer;

import java.io.IOException;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zebra.constant.util.ConstantUtil;

public class EnumSerializer extends JsonSerializer<Enum<?>> {

	@Override
	public void serialize(Enum<?> value, JsonGenerator generator, SerializerProvider provider) throws IOException,
		JsonProcessingException {
		generator.writeStartObject();
	    generator.writeFieldName("id");
	    generator.writeString(value.name());
	    generator.writeFieldName("label");
	    generator.writeString(ConstantUtil.value(value, Locale.ENGLISH));
	    generator.writeEndObject();
	}

}
