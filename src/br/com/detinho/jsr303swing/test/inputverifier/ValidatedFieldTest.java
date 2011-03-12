package br.com.detinho.jsr303swing.test.inputverifier;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import br.com.detinho.jsr303swing.main.inputverifier.NoSuchFieldRuntimeException;
import br.com.detinho.jsr303swing.main.inputverifier.ValidatedField;
import br.com.detinho.jsr303swing.main.inputverifier.converters.IntegerMetaTypeConverter;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverterFinder;
import br.com.detinho.jsr303swing.main.inputverifier.converters.StringMetaTypeConverter;
import br.com.detinho.jsr303swing.test.Person;

public class ValidatedFieldTest {

	ValidatedField stringField;
	ValidatedField integerField;
	
	MetaTypeConverterFinder finder = mock(MetaTypeConverterFinder.class);
	
	@Before
	public void setUp() throws Exception {
		prepareFinderMocks();
		
		stringField = new ValidatedField(Person.class, "name", finder);
		integerField = new ValidatedField(Person.class, "age", finder);
	}
	
	private void prepareFinderMocks() {
		when(finder.find(String.class)).thenReturn(new StringMetaTypeConverter());
		when(finder.find(Integer.class)).thenReturn(new IntegerMetaTypeConverter());
	}
	
	@Test
	public void testTheDeclaringClass() {
		assertEquals(Person.class, stringField.getDeclaringClass());
	}
	
	@Test(expected=NoSuchFieldRuntimeException.class)
	public void shouldThrowNoSuchFieldRuntimeException() {
		new ValidatedField(Person.class, "unknowField", finder);
	}
	
	@Test
	public void shouldReturnConvertedObjectAsString() {
		String textFieldValue = "Marcos";
		Object returnedObject = stringField.getConvertedValue(textFieldValue);
		assertEquals(textFieldValue, returnedObject);
	}
	
	@Test
	public void shouldReturnConvertedObjectAsInteger() {
		String textFieldValue = "25";
		Integer integerValue = new Integer(textFieldValue);
		Object returnedObject = integerField.getConvertedValue(textFieldValue);
		assertEquals(integerValue, returnedObject);
	}

}
