package br.com.detinho.jsr303swing.context;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.validation.Validator;

import br.com.detinho.jsr303swing.inputverifier.JSR303InputVerifier;
import br.com.detinho.jsr303swing.inputverifier.ValidatedField;
import br.com.detinho.jsr303swing.inputverifier.ValidationFailedEvent;
import br.com.detinho.jsr303swing.inputverifier.converters.MetaTypeConverterFinder;

public class VerifierContext {

	private List<JSR303InputVerifier> verifiers;
	private Validator validator;
	
	public VerifierContext(Validator validator) {
		this.validator = validator;
		verifiers = new ArrayList<JSR303InputVerifier>();
	}
	
	public void add(JTextField textField, Class<?> theClass,
			String fieldName, ValidationFailedEvent event) {
		MetaTypeConverterFinder finder = new MetaTypeConverterFinder();
		ValidatedField field = new ValidatedField(theClass, fieldName, finder);
		JSR303InputVerifier verifier = new JSR303InputVerifier(textField, field, validator);
		
		if (event != null)
			verifier.setValidationFailedEvent(event);
		
		verifiers.add(verifier);
	}	
	
	public void add(JTextField textField, Class<?> theClass,
			String fieldName) {
		add(textField, theClass, fieldName, null);
	}

	public boolean verifyAll() {
		boolean verificationSucceed = true;
		for (JSR303InputVerifier verifier : verifiers)
			if (verificationFails(verifier))
				verificationSucceed = false;
		
		return verificationSucceed;
	}

	private boolean verificationFails(JSR303InputVerifier verifier) {
		return !verifier.verify();
	}

}
