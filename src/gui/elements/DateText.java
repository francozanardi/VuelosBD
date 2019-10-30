package gui.elements;

import java.awt.Font;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class DateText extends JFormattedTextField implements FocuseableElement {
	
	public DateText(String text, Font font) {
		super();
		StyleTextComponent.addStyle(this, text, font);
	}

	@Override
	public void focusOn() {
		try {
			super.setFormatter(new MaskFormatter("##'/##'/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void focusOff() {
		super.setFormatter(null);
	}

	@Override
	public boolean isEmpty() {
		return getText().equals("  /  /    ") || getText().isEmpty();
	}

	@Override
	protected void setFormatter(AbstractFormatter format) {
		if(format != null){
			super.setFormatter(format);
		}
	}
}
