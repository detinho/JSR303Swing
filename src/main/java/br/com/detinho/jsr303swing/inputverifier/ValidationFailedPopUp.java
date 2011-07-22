package br.com.detinho.jsr303swing.inputverifier;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * <p>Based on code from Michal Urban</p>
 * <a href="http://www.javalobby.org/java/forums/t20551.html">
 * 		http://www.javalobby.org/java/forums/t20551.html
 * </a>
 */
public class ValidationFailedPopUp implements ValidationFailedEvent, KeyListener {

	private JDialog popup;
	private JComponent sender;
	
	public ValidationFailedPopUp(JDialog parent) {
		popup = new JDialog(parent);
		initComponents();
	}
	
	public ValidationFailedPopUp(JFrame parent) {
		popup = new JDialog(parent);
		initComponents();
	}
	
	private void initComponents() {
		popup.setUndecorated(true);
		popup.getContentPane().setLayout(new FlowLayout());
		popup.setFocusableWindowState(false);
	}
	
	@Override
	public void validationFailed(JComponent sender, String field, String message, Object invalidValue) {
		String newMessage = field + ": " + message + "(" + invalidValue + ")";
		
		sender.addKeyListener(this);
		
		this.sender = sender;
		
		displayMessage(newMessage);
	}
	
	private void displayMessage(String newMessage) {
		popup.getContentPane().removeAll();
		popup.getContentPane().add(new JLabel(newMessage));
		
		popup.setSize(0, 0);
		popup.setLocationRelativeTo(sender);
		
		Point point = popup.getLocation();
		Dimension cDim = sender.getSize();
		
        popup.setLocation(point.x-(int)cDim.getWidth()/2,
            point.y+(int)cDim.getHeight()/2);
        
		popup.pack();
		popup.setVisible(true);
	}
	
	public void keyPressed(KeyEvent event) {
		popup.setVisible(false);
	}
	
	public void keyTyped(KeyEvent event) {}
	
	public void keyReleased(KeyEvent event) {
		this.sender.removeKeyListener(this);
	}
}
