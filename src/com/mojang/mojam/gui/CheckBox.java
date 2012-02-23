package com.mojang.mojam.gui;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class CheckBox extends Button {

	private boolean isChecked;
	public final static int width = 40;
	public final static int heigth = 40;
	public final String text;

	public CheckBox(int id, String text, int x, int y) {
		this(id, text, x, y, false);
	}

	public CheckBox(int id, String text, int x, int y, boolean initial) {
		super(id, x - 8, y - 10, width, heigth);
		isChecked = initial;
		this.text = text;
	}

	@Override
	public void render(Screen screen) {

		if (isChecked) {
			screen.blit(Art.checked, x, y);
		} else {
			screen.blit(Art.unchecked, x, y);
		}
		Font.draw(screen, text, x + width, y + 15);
	}

	@Override
	public void postClick() {
		isChecked = !isChecked;
		super.postClick();
	}

	public boolean isChecked() {
		return isChecked;
	}

}
