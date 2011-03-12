package br.com.detinho.swingjsr303.test.inputverifier.converters;

import org.junit.Test;
import static org.junit.Assert.*;

import br.com.detinho.swingjsr303.main.inputverifier.converters.StringMetaTypeConverter;

public class StringMetaTypeConverterTest {

	@Test
	public void shouldReturnAString() {
		String testString = "Ok!";
		StringMetaTypeConverter converter = new StringMetaTypeConverter();
		assertEquals(testString, converter.from(testString));
		assertEquals(testString.getClass(), converter.from(testString).getClass());
	}

}
