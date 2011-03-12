package br.com.detinho.jsr303swing.test.inputverifier.converters;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import br.com.detinho.jsr303swing.main.inputverifier.converters.IntegerMetaTypeConverter;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverter;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverterFinder;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverterNotFoundException;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeNotAConverterException;

public class MetaTypeConverterFinderTest {

	private MetaTypeConverterFinder finder;
	
	@Before
	public void setUp() throws Exception {
		finder = new MetaTypeConverterFinder();
	}
	
	@Test
	public void shouldReturnAMetatypeConverter() {
		MetaTypeConverter converter = finder.find(Integer.class);
		assertFalse(converter == null);
	}
	
	@Test
	public void shouldReturnAnIntegerMetaTypeConverter() {
		MetaTypeConverter converter = finder.find(Integer.class);
		assertTrue(converter instanceof IntegerMetaTypeConverter);
	}
	
	@Test(expected=MetaTypeConverterNotFoundException.class)
	public void shouldThrowConverterNotFoundException() throws Exception {
		finder.find(MetaTypeConverter.class);
	}
	
	@Test(expected=MetaTypeNotAConverterException.class)
	public void shouldThrowNotAConverterException() {
		finder.find(Fake.class);
	}

}
