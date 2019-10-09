package gui.elements;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class TextField extends JTextField implements FocuseableElement {
	
	public TextField(String text, Font font) {
		super();
		StyleTextComponent.addStyle(this, text, font);
		
	}

	@Override
	public boolean isEmpty() {
		return getText().isEmpty();
	}
	
}