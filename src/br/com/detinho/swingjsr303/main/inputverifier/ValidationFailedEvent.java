package br.com.detinho.swingjsr303.main.inputverifier;

import javax.swing.JComponent;

public interface ValidationFailedEvent {
	public void validationFailed(JComponent sender, String field, String message, Object invalidValue);
}
