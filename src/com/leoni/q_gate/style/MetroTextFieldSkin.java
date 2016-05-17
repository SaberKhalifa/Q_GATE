package com.leoni.q_gate.style;

import javafx.scene.control.TextField;

/**
 *
 * @author Saber Ben Khalifa
 */
public class MetroTextFieldSkin extends TextFieldWithButtonSkin {
	public MetroTextFieldSkin(TextField textField) {
		super(textField);
	}

	@Override
	protected void rightButtonPressed() {
		getSkinnable().setText("");
	}

}