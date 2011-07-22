package br.com.detinho.jsr303swing.learning;

import java.util.Set;
import static org.junit.Assert.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import br.com.detinho.jsr303swing.Person;

public class LearnHibernateValidationBasics {

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();	
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void learnTheBasicsOfHibernateValidation() {
		
		Set<ConstraintViolation<Person>> constraintViolations = 
			validator.validate(new Person("Marcos", new Integer(24)));
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void shouldResultInAnError() {
		Person person = new Person("", new Integer(24));
		Set<ConstraintViolation<Person>> constraintViolation = 
			validator.validate(person);
		assertEquals(1, constraintViolation.size());
		assertEquals("may not be empty", constraintViolation.iterator().next().getMessage());
	}
	
	@Test
	public void learnValidationOfSingleValue() {
		String name = "Marcos";
		Set<ConstraintViolation<Person>> constraintViolation = 
			validator.validateValue(Person.class, "name", name);
		assertEquals(0, constraintViolation.size());
	}
	
	@Test
	public void shouldResultInAnErrorForASingleValue() {
		String name = "";
		Set<ConstraintViolation<Person>> constraintViolation = 
			validator.validateValue(Person.class, "name", name);
		assertEquals(1, constraintViolation.size());
		assertEquals("may not be empty", constraintViolation.iterator().next().getMessage());
	}
	
//	@Test
//	public void shouldThrowAnException() {
//		try {
//			validator.validateValue(Person.class, "age", "Test");
//		} catch (ValidationException ex) {
//			assertEquals(ex.getCause().getMessage(), "");
//		}
//	}
}
