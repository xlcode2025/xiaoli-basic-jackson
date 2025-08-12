package com.xiaoli.basic.jackson.ser;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * LocalDateTime通过Long反序列化<br/>
 * 
 * @see {@link com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer}
 * 
 *
 */
public class LongLocalDateTimeDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LocalDateTime> {
	public static final LongLocalDateTimeDeserializer INSTANCE = new LongLocalDateTimeDeserializer();

	private final ZoneId zoneId;

	public LongLocalDateTimeDeserializer() {
		this.zoneId = ZoneOffset.systemDefault();
	}

	public LongLocalDateTimeDeserializer(ZoneId zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getLongValue()), zoneId);
	}

}
