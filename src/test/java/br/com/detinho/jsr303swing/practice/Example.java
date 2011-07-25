package br.com.detinho.jsr303swing.practice;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.detinho.jsr303swing.Person;
import br.com.detinho.jsr303swing.context.VerifierContext;
import br.com.detinho.jsr303swing.inputverifier.JSR303InputVerifier;
import br.com.detinho.jsr303swing.inputverifier.ValidationFailedPopUp;

public class Example extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textName = new JTextField("Type the Name here");
	private JTextField textAge = new JTextField("Type the Age here");
	
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private VerifierContext context = new VerifierContext(validator);
	
	public Example() throws Exception {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		context.add(textName, Person.class, "name", new ValidationFailedPopUp(this));
		context.add(textAge, Person.class, "age", new ValidationFailedPopUp(this));
		
		JButton validateAll = new JButton("Validate All");
		validateAll.addMouseListener(new MouseAdapter() {
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
		Example t = new Example();
		t.setVisible(true);
	}
}
