package gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;


class StyleTextComponent<E extends JTextComponent & FocuseableElement> {
	
	private StyleTextComponent() {
		
	}

	
	public static void addStyle(FocuseableElement fe, String text, Font font) {	
		focusOff(fe, text, font);
		
		fe.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				focusOn(fe, font);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(fe.isEmpty()) {
					focusOff(fe, text, font);
				}
			}
		});
	}
	
	private static void focusOff(FocuseableElement tc, String text, Font font) {
		tc.focusOff();
		tc.setForeground(Color.LIGHT_GRAY);
		tc.setFont(font.deriveFont(Font.ITALIC));
		tc.setText(text);
	}
	
	private static void focusOn(FocuseableElement tc, Font font) {
		tc.focusOn();
		tc.setForeground(Color.BLACK);
		tc.setFont(font);
		tc.setText("");
	}
	
	
}