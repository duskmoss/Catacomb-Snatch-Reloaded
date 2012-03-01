package com.mojang.mojam.gui.menu;

import java.awt.event.KeyEvent;

import com.mojang.mojam.gui.Button;
import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.screen.Screen;

public class JoinGameMenu extends GuiMenu {

	private Button joinButton, cancelButton;
	int textHeight;

	public JoinGameMenu() {
		super();
		textHeight = 100;
		joinButton = addButton(new MenuButton(GuiMenu.PERFORM_JOIN_ID, 3, 100,
				140));
		cancelButton = addButton(new MenuButton(GuiMenu.CANCEL_JOIN_ID, 4, 250, 140));
	}

	@Override
	public void render(Screen screen) {

		screen.clear(0);
		Font.draw(screen, "Enter IP of Host:", 100, textHeight);
		Font.draw(screen, TitleMenu.ip + "-", 100, textHeight + 20);

		super.render(screen);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && TitleMenu.ip.length() > 0) {
			joinButton.postClick();
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelButton.postClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE
				&& TitleMenu.ip.length() > 0) {
			TitleMenu.ip = TitleMenu.ip.substring(0, TitleMenu.ip.length() - 1);
		} else if (Font.letters.indexOf(Character.toUpperCase(e.getKeyCode())) >= 0) {
			TitleMenu.ip += e.getKeyChar();
		}
	}

}
