package br.com.detinho.jsr303swing.inputverifier.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FloatMetatypeConverterTest {
	
	private MetaTypeConverter converter;

	@Before
	public void setUp() {
		converter = new FloatMetatypeConverter();
	}
	
	@Test
	public void shouldReturnAValidFloat() {
		String n = "10";
		Float f = new Float(n);
		assertEquals(f, converter.from(n));
	}
	
	@Test
	public void shouldThrowAnException() {
		String value = "Error";
		try {
			converter.from(value);
			fail("Should have thrown.");
		} catch(MetaTypeConversionException ex) {
			assertEquals(ex.getValue(), value);
		}
	}

}
