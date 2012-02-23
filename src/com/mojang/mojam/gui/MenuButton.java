package com.mojang.mojam.gui;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class MenuButton extends Button {

	public final static int width = 128;
	public final static int heigth = 24;

	private int ix;
	private int iy;

	public MenuButton(int id, int buttonImageIndex, int x, int y) {
		super(id, x, y, width, heigth);
		this.ix = buttonImageIndex % 2;
		this.iy = buttonImageIndex / 2;
	}

	@Override
	public void render(Screen screen) {

		if (isPressed) {
			screen.blit(Art.buttons[ix][iy * 2 + 1], x, y);
		} else {
			screen.blit(Art.buttons[ix][iy * 2 + 0], x, y);
		}
	}
}
