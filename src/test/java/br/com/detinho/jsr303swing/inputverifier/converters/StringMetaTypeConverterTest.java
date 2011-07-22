package br.com.detinho.jsr303swing.inputverifier.converters;

import org.junit.Test;
import static org.junit.Assert.*;

import br.com.detinho.jsr303swing.inputverifier.converters.StringMetaTypeConverter;

public class StringMetaTypeConverterTest {

	@Test
	public void shouldReturnAString() {
		String testString = "Ok!";
		StringMetaTypeConverter converter = new StringMetaTypeConverter();
		assertEquals(testString, converter.from(testString));
		assertEquals(testString.getClass(), converter.from(testString).getClass());
	}

}
