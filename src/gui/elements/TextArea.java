package gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextArea extends JTextArea implements FocuseableElement {

	public TextArea(String text, Font font) {
		super();
		StyleTextComponent.addStyle(this, text, font);
	}

	@Override
	public boolean isEmpty() {
		return getText().isEmpty();
	}

}
