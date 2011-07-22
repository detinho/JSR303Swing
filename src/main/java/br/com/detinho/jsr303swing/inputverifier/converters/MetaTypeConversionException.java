package br.com.detinho.jsr303swing.inputverifier.converters;

public class MetaTypeConversionException extends RuntimeException {
	
	private Object value;
	
	public MetaTypeConversionException(String message, Throwable ex, Object value) {
		super(message, ex);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
	
}
