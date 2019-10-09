package gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusListener;

interface FocuseableElement {
	public boolean isEmpty();
	public void setText(String s);
	public void setFont(Font f);
	public void setForeground(Color c);
	public void addFocusListener(FocusListener fl);
	
	public default void focusOn() {
		
	}
	public default void focusOff() {
		
	}
}
