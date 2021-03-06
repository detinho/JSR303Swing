package br.com.detinho.jsr303swing.inputverifier;

import java.util.Collections;
import java.util.Set;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import br.com.detinho.jsr303swing.inputverifier.converters.MetaTypeConversionException;

public class JSR303InputVerifier extends InputVerifier {
	
	private Validator validator;
	private Class<?> theClass;
	private ValidatedField field;
	private JTextField textField;
	
	private ValidationPassedEvent passedEvent;
	private ValidationFailedEvent failedEvent;
	
	private Set<?> constraintViolations = Collections.emptySet();

	public JSR303InputVerifier(JTextField textField, ValidatedField field,
			Validator validator) {
		this.theClass = field.getDeclaringClass();
		this.field = field;
		this.textField = textField;
		
		this.validator = validator;
		textField.setInputVerifier(this);
	}

	@Override
	public boolean shouldYieldFocus(JComponent input) {
		verify(input);
		return true;
	}
	
	public boolean verify() {
		return verify(textField);
	}
	
	@Override
	public boolean verify(JComponent input) {
		Object toBeValidated = textField.getText();
		try {
			toBeValidated = convertThe(textField);
			return tryToValidateTheObject(toBeValidated);
		} catch (MetaTypeConversionException ex) {
			fireValidationEventBasedOnException(toBeValidated);
			return false;
		}
	}
	
	private boolean tryToValidateTheObject(Object toBeValidated) {
		constraintViolations = 
			validator.validateValue(theClass, field.getFieldName(), toBeValidated);
		boolean validationPassed = constraintViolations.size() == 0;
	
		if (validationPassed)
			fireValidationPassedEvents();
		else
			fireValidationFailedEvents();
		
		return validationPassed;
	}
	
	private void fireValidationEventBasedOnException(Object toBeValidated) {
		if (failedEvent != null) {
			String failedMessage = "invalid value for " + field.getFieldName();
			fireValidationFailedEvent(textField, field.getFieldName(), failedMessage, 
					toBeValidated);
		}
	}

	private Object convertThe(JTextField textFieldObject) {
		return field.getConvertedValue(textFieldObject.getText());
	}

	private void fireValidationPassedEvents() {
		if (passedEvent != null)
			passedEvent.validationPassed();
	}
	
	private void fireValidationFailedEvents() {
		ConstraintViolation<?> violation = 
			(ConstraintViolation<?>)constraintViolations.iterator().next();
		
		fireValidationFailedEvent(textField, field.getFieldName(), violation.getMessage(), 
				violation.getInvalidValue());
	}
	
	private void fireValidationFailedEvent(JComponent sender, String field, String message,
			Object invalidValue) {
		if (failedEvent != null)
			failedEvent.validationFailed(textField, field, message, invalidValue);		
	}

	public void setValidationPassedEvent(ValidationPassedEvent passedEvent) {
		this.passedEvent = passedEvent;
	}

	public void setValidationFailedEvent(ValidationFailedEvent failedEvent) {
		this.failedEvent = failedEvent;
	}

	public Validator getValidator() {
		return validator;
	}
}