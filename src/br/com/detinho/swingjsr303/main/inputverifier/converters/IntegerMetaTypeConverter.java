package br.com.detinho.swingjsr303.main.inputverifier.converters;

public class IntegerMetaTypeConverter implements MetaTypeConverter {

	public Object from(String value) {
		try {
			return new Integer(value);
		} catch (NumberFormatException ex) {
			String message = "Error converting " + value + ".";
			throw new MetaTypeConversionException(message, ex);
		}
	}
	
}
