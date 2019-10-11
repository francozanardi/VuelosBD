package gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;

public class PasswordField extends JPasswordField implements FocuseableElement {
	
	private char passwordChar;
	
	public PasswordField(String text, Font font) {
		super();
		this.passwordChar = getEchoChar();
		StyleTextComponent.addStyle(this, text, font);
		
	}
	
	public void focusOff() {
		setEchoChar ((char) 0);
	}
	
	public void focusOn() {
		setEchoChar(passwordChar);
	}

	@Override
	public boolean isEmpty() {
		return getPassword().length == 0;
	}
}
