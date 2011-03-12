package br.com.detinho.jsr303swing.test.inputverifier;

import javax.swing.JTextField;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import br.com.detinho.jsr303swing.main.inputverifier.JSR303InputVerifier;
import br.com.detinho.jsr303swing.main.inputverifier.ValidatedField;
import br.com.detinho.jsr303swing.main.inputverifier.ValidationFailedEvent;
import br.com.detinho.jsr303swing.main.inputverifier.ValidationPassedEvent;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConversionException;
import br.com.detinho.jsr303swing.test.Person;

public class JSR303InputVerifierTest {

	private JTextField nameTextField;
	private JSR303InputVerifier nameVerifier;
	private final String nameField = "name";
	
	private JTextField ageTextField;
	private JSR303InputVerifier ageVerifier;
	private final String ageField = "age";
	
	private final String nameToTest = "Marcos";
	private final String ageStringToTest = "25";
	private final String invalidAgeStringToTest = "200";
	private final String invalidFormatAgeString = "Should be and Integer";
	
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private ValidatedField stringField = mock(ValidatedField.class);
	private ValidatedField integerField = mock(ValidatedField.class);
	
	@Before
	public void setUp() throws Exception {
		prepareMocks();
		
		nameTextField = new JTextField();
		nameVerifier = new JSR303InputVerifier(nameTextField, stringField, validator);
		
		ageTextField = new JTextField();
		ageVerifier = new JSR303InputVerifier(ageTextField, integerField, validator);
	}
	
	private void prepareMocks() {
		when(stringField.getConvertedValue(nameToTest)).thenReturn(nameToTest);
		when(stringField.getConvertedValue("")).thenReturn("");
		
		when(integerField.getConvertedValue(ageStringToTest)).thenReturn(new Integer(ageStringToTest));
		when(integerField.getConvertedValue(invalidAgeStringToTest)).thenReturn(new Integer(invalidAgeStringToTest));
		when(integerField.getConvertedValue(invalidFormatAgeString)).thenThrow(new MetaTypeConversionException("", null));
		
		doReturn(Person.class).when(stringField).getDeclaringClass();
		doReturn(Person.class).when(integerField).getDeclaringClass();
		
		when(stringField.getFieldName()).thenReturn(nameField);
		when(integerField.getFieldName()).thenReturn(ageField);
	}
	
	@Test
	public void creation() {
		assertSame(nameVerifier, nameTextField.getInputVerifier());
		assertSame(validator, nameVerifier.getValidator());
	}
	
	
	@Test
	public void shouldPassTheVerificationTest() {
		nameTextField.setText(nameToTest);
		assertTrue(nameVerifier.verify(nameTextField));
	}
	
	@Test
	public void shouldNotPassTheVerificationTest() {
		nameTextField.setText("");
		assertFalse(nameVerifier.verify(nameTextField));
	}
	
	@Test
	public void shouldNotifyValidationPassed() {
		ValidationPassedEvent passedEvent = mock(ValidationPassedEvent.class);
		nameTextField.setText(nameToTest);
		nameVerifier.setValidationPassedEvent(passedEvent);
		
		nameVerifier.verify(nameTextField);
		
		verify(passedEvent).validationPassed();
	}
	
	@Test
	public void shouldNotifyValidationFailed() {
		ValidationFailedEvent failedEvent = mock(ValidationFailedEvent.class);
		nameTextField.setText("");
		nameVerifier.setValidationFailedEvent(failedEvent);
		
		nameVerifier.verify(nameTextField);
		
		verify(failedEvent).validationFailed(nameTextField, nameField, "may not be empty", nameTextField.getText());
	}
	
	@Test
	public void shouldPassOnTheIntegerVerificationTest() {
		ageTextField.setText(ageStringToTest);
		assertTrue(ageVerifier.verify(ageTextField));
	}
	
	@Test
	public void shouldFaildOnTheIntegerVerificationTest() {
		ageTextField.setText(invalidAgeStringToTest);
		assertFalse(ageVerifier.verify(ageTextField));
	}

	@Test
	public void cannotConvertTheToAValidValue() {
		ValidationFailedEvent failedEvent = mock(ValidationFailedEvent.class);
		ageTextField.setText(invalidFormatAgeString);
		ageVerifier.setValidationFailedEvent(failedEvent);
		
		assertFalse(ageVerifier.verify(ageTextField));
		
		verify(failedEvent).validationFailed(ageTextField, ageField, "", ageTextField.getText());
	}
}