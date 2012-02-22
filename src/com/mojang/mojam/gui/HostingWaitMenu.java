package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.screen.Screen;

public class HostingWaitMenu extends GuiMenu {
	
	
	int textHeight;

	public HostingWaitMenu() {
		super(100);
		textHeight=getNextHeight();
		getNextHeight();
		addButton(new Button(TitleMenu.CANCEL_JOIN_ID, 4, 250, getNextHeight()));
	}

	@Override
	public void render(Screen screen) {

		screen.clear(0);
		Font.draw(screen, "Waiting for client to join...", 100, textHeight);

		super.render(screen);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
