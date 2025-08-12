package com.xiaoli.basic.jackson;

/**
 * JSON异常
 */
public class JsonException extends RuntimeException {
	private static final long serialVersionUID = -8817297415234620513L;

	public JsonException() {
		super();
	}

	public JsonException(String message) {
		super(message);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonException(Throwable cause) {
		super(cause);
	}

}
