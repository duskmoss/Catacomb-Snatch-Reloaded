package com.mojang.mojam.gui.menu;

import java.awt.event.KeyEvent;

import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.screen.Screen;

public class InvalidHostMenu extends GuiMenu {

	private int textHeight;

	public InvalidHostMenu() {
		super();
		textHeight = 100;
		addButton(new MenuButton(GuiMenu.BACK_ID, 4, 250, 140));
	}

	@Override
	public void render(Screen screen) {

		screen.clear(0);
		Font.draw(screen, "Not a valid host", 100, textHeight);

		super.render(screen);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
