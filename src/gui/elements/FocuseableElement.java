package gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusListener;

interface FocuseableElement {
	boolean isEmpty();
	void setText(String s);
	void setFont(Font f);
	void setForeground(Color c);
	void addFocusListener(FocusListener fl);

	
	default void focusOn() {
		
	}
	default void focusOff() {
		
	}
}
