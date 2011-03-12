package br.com.detinho.jsr303swing.test.inputverifier.converters;


import org.junit.Before;
import org.junit.Test;

import br.com.detinho.jsr303swing.main.inputverifier.converters.IntegerMetaTypeConverter;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConversionException;

import static org.junit.Assert.*;

public class IntegerMetatypeConverterTest {

	private IntegerMetaTypeConverter converter;
	private final String testNumber = "20";
	
	@Before
	public void setUp() throws Exception {
		converter = new IntegerMetaTypeConverter();
	}
	
	@Test
	public void shouldReturnAValidInteger() {
		assertEquals(new Integer(testNumber), converter.from(testNumber));
	}
	
	@Test(expected=MetaTypeConversionException.class)
	public void shouldThrowAnException() {
		converter.from("Error!");
	}

}
