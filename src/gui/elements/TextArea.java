package gui.elements;

import java.awt.Font;
import javax.swing.JTextArea;

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
