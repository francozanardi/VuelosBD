package gui.elements;

import javax.swing.text.JTextComponent;

abstract class TextComponent extends JTextComponent {
	public abstract boolean isEmpty();
	public abstract void focusOn();
	public abstract void focusOff();
}
