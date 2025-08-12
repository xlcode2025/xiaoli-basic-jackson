package com.xiaoli.basic.jackson.ser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * LocalDateTime序列化为Long<br/>
 * 
 * @see {@link com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer}

 *
 */
public class LongLocalDateTimeSerializer extends com.fasterxml.jackson.databind.JsonSerializer<LocalDateTime> {

	public static final LongLocalDateTimeSerializer INSTANCE = new LongLocalDateTimeSerializer();

	private final ZoneId zoneId;
	
	public LongLocalDateTimeSerializer() {
		this.zoneId = ZoneOffset.systemDefault();
	}

	public LongLocalDateTimeSerializer(ZoneId zoneId) {
		this.zoneId  = zoneId;
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeNumber(value.atZone(zoneId).toInstant().toEpochMilli());
	}

}
