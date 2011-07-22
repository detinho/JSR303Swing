package br.com.detinho.jsr303swing.inputverifier.converters;

public class FloatMetatypeConverter implements MetaTypeConverter {

	@Override
	public Object from(String value) {
		try {
			return new Float(value);
		} catch(NumberFormatException ex) {
			String msg = String.format("Error converting %s.", value);
			throw new MetaTypeConversionException(msg, ex, value);
		}
	}
}
