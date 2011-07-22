package br.com.detinho.jsr303swing.inputverifier;

import javax.swing.JComponent;

public interface ValidationFailedEvent {
	public void validationFailed(JComponent sender, String field, String message, Object invalidValue);
}
