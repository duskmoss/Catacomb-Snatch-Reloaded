package com.mojang.mojam.gui.menu;

import java.awt.event.KeyEvent;

import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.screen.Screen;

public class HostingWaitMenu extends GuiMenu {
	
	
	int textHeight;

	public HostingWaitMenu() {
		super();
		textHeight=100;
		addButton(new MenuButton(TitleMenu.CANCEL_JOIN_ID, 4, 250, 180));
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
