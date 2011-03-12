package br.com.detinho.jsr303swing.main.inputverifier;

import java.lang.reflect.Field;

import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverter;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverterFinder;

public class ValidatedField {

	private Class<?> declaringClass;
	private Field field;
	private MetaTypeConverter converter;
	
	public <T> ValidatedField(Class<T> declaringClass, String fieldName, MetaTypeConverterFinder finder) {
		this.declaringClass = declaringClass;
		tryToFindTheField(fieldName);
		tryToFindTheConverter(finder);
	}
	
	private void tryToFindTheField(String fieldName) {
		try {
			field = declaringClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException ex) {
			throw new NoSuchFieldRuntimeException(ex.getLocalizedMessage(), ex);
		}
	}
	
	private void tryToFindTheConverter(MetaTypeConverterFinder finder) {
		converter = finder.find(getClassType());
	}
	
	public Class<?> getDeclaringClass() {
		return declaringClass;
	}

	private Class<?> getClassType() {
		return field.getType();
	}

	public Object getConvertedValue(String textFieldValue) {
		return converter.from(textFieldValue);
	}

	public String getFieldName() {
		return field.getName();
	}
	
}
