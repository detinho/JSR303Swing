package br.com.detinho.jsr303swing.inputverifier.converters;


import org.junit.Before;
import org.junit.Test;

import br.com.detinho.jsr303swing.inputverifier.converters.IntegerMetaTypeConverter;
import br.com.detinho.jsr303swing.inputverifier.converters.MetaTypeConversionException;

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
	
	@Test
	public void shouldThrowAnException() {
		String value = "Error";
		try {
			converter.from(value);
			fail("Should have thrown.");
		} catch (MetaTypeConversionException ex) {
			assertEquals(ex.getValue(), value);
		}
	}

}
