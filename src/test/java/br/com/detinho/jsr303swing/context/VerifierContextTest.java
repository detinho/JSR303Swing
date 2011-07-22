package br.com.detinho.jsr303swing.context;


import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import br.com.detinho.jsr303swing.context.VerifierContext;
import br.com.detinho.jsr303swing.inputverifier.ValidationFailedEvent;
import br.com.detinho.jsr303swing.Person;

public class VerifierContextTest {

	private JTextField nameTextField;
	private JTextField ageTextField; 
	private Validator validator = null;

	@Before
	public void setUp() throws Exception {
		nameTextField = new JTextField();
		ageTextField = new JTextField();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	public void makeANewVerifier() {
		new VerifierContext(validator).add(nameTextField, Person.class, "name");
	}
	
	@Test
	public void verifyAll() {
		VerifierContext context = new VerifierContext(validator);
		nameTextField.setText("Marcos");
		ageTextField.setText("25");
		
		context.add(nameTextField, Person.class, "name");
		context.add(ageTextField, Person.class, "age");
		
		assertTrue(context.verifyAll());
	}
	
	@Test
	public void shouldNotValidateAll() {
		VerifierContext context = new VerifierContext(validator);
		nameTextField.setText("");
		ageTextField.setText("200");
		
		context.add(nameTextField, Person.class, "name");
		context.add(ageTextField, Person.class, "age");
		
		assertFalse(context.verifyAll());		
	}

	@Test
	public void shouldCallTheValidationFailedEvent() {
		VerifierContext context = new VerifierContext(validator);
		ValidationFailedEvent nameEvent = mock(ValidationFailedEvent.class);
		ValidationFailedEvent ageEvent = mock(ValidationFailedEvent.class);
		
		nameTextField.setText("");
		
		context.add(nameTextField, Person.class, "name", nameEvent);
		context.add(ageTextField, Person.class, "age", ageEvent);
		context.verifyAll();
		
		verify(nameEvent).validationFailed((JComponent)any(), anyString(), anyString(), any());
		verify(ageEvent).validationFailed((JComponent)any(), anyString(), anyString(), any());
	}
}
