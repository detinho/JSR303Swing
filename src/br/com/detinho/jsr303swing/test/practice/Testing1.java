package br.com.detinho.jsr303swing.test.practice;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.detinho.jsr303swing.main.inputverifier.JSR303InputVerifier;
import br.com.detinho.jsr303swing.main.inputverifier.ValidatedField;
import br.com.detinho.jsr303swing.main.inputverifier.ValidationFailedPopUp;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverterFinder;
import br.com.detinho.jsr303swing.test.Person;

public class Testing1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textName = new JTextField("Type the Name here");
	private JTextField textAge = new JTextField("Type the Age here");
	
	public Testing1() throws Exception {
		getContentPane().setLayout(new FlowLayout());
		
		MetaTypeConverterFinder finder = new MetaTypeConverterFinder();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		ValidatedField nameField = new ValidatedField(Person.class, "name", finder);
		ValidatedField ageField = new ValidatedField(Person.class, "age", finder);
		
		registerFailedEvent(new JSR303InputVerifier(textName, nameField, validator));
		registerFailedEvent(new JSR303InputVerifier(textAge, ageField, validator));
		
		add(new JLabel("Name:"));
		add(textName);
		add(new JLabel("Age:"));
		add(textAge);
		add(new JButton("Do Nothing!"));
		pack();
	}
	
	public void registerFailedEvent(JSR303InputVerifier verifier) {
		verifier.setValidationFailedEvent(new ValidationFailedPopUp(this));
	}
	
	public static void main(String... args) throws Exception {
		new Testing1().setVisible(true);
	}
}
