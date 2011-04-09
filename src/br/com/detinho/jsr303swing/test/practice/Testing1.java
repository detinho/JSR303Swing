package br.com.detinho.jsr303swing.test.practice;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.engine.ValidationContext;

import br.com.detinho.jsr303swing.main.context.VerifierContext;
import br.com.detinho.jsr303swing.main.inputverifier.JSR303InputVerifier;
import br.com.detinho.jsr303swing.main.inputverifier.ValidatedField;
import br.com.detinho.jsr303swing.main.inputverifier.ValidationFailedPopUp;
import br.com.detinho.jsr303swing.main.inputverifier.converters.MetaTypeConverterFinder;
import br.com.detinho.jsr303swing.test.Person;

public class Testing1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textName = new JTextField("Type the Name here");
	private JTextField textAge = new JTextField("Type the Age here");
	
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private VerifierContext context = new VerifierContext(validator);
	
	public Testing1() throws Exception {
		getContentPane().setLayout(new FlowLayout());
		
		/*MetaTypeConverterFinder finder = new MetaTypeConverterFinder();
		
		ValidatedField nameField = new ValidatedField(Person.class, "name", finder);
		ValidatedField ageField = new ValidatedField(Person.class, "age", finder);
		
		registerFailedEvent(new JSR303InputVerifier(textName, nameField, validator));
		registerFailedEvent(new JSR303InputVerifier(textAge, ageField, validator));*/
		
		context.add(textName, Person.class, "name", new ValidationFailedPopUp(this));
		context.add(textAge, Person.class, "age", new ValidationFailedPopUp(this));
		
		JButton validateAll = new JButton("Validate All");
		validateAll.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					verifyAll();
				}
			}
		});
		
		add(new JLabel("Name:"));
		add(textName);
		add(new JLabel("Age:"));
		add(textAge);
		add(validateAll);
		pack();
		

	}
	
	private void verifyAll() {
		context.verifyAll();
	}
	
	public void registerFailedEvent(JSR303InputVerifier verifier) {
		verifier.setValidationFailedEvent(new ValidationFailedPopUp(this));
	}
	
	public static void main(String... args) throws Exception {
		Testing1 t = new Testing1();
		t.setVisible(true);
		t.textName.setText("");
		t.textAge.setText("");
		t.verifyAll();
	}
}
