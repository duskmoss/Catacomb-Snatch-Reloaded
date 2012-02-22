package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.screen.Screen;

public class InvalidHostMenu extends GuiMenu {
	
	

	private int textHeight;

	public InvalidHostMenu() {
		super(100);
		textHeight=getNextHeight();
		addButton(new Button(TitleMenu.BACK_ID, 4, 250, getNextHeight()));
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
