package br.com.detinho.jsr303swing.inputverifier.converters;

public class StringMetaTypeConverter implements MetaTypeConverter {

	
	@Override
	public Object from(String value) {
		return value;
	}
	
}
