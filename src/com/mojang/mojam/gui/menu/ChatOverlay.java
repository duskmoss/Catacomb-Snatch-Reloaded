package com.mojang.mojam.gui.menu;

import java.awt.event.KeyEvent;

import com.mojang.mojam.entity.mob.Team;
import com.mojang.mojam.gui.Button;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.screen.Screen;

public class ChatOverlay extends GuiMenu implements Overlay {

	private String message = "";
	private String header = "";
	private Button sendButton;

	public ChatOverlay(int player) {
		if (player == Team.Team1) {
			header = "Lord Lard:";
		} else if (player == Team.Team2) {
			header = "Herr Von Speck:";
		}
		sendButton = addButton(new Button(GuiMenu.SEND_ID));
	}

	public String getMessage() {
		if (message.length() > 0) {
			return header + message;
		}
		return null;

	}

	@Override
	public void render(Screen screen) {

		Font.draw(screen, header + message, 0, 320);

		super.render(screen);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			sendButton.postClick();
		}
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			message = "";
			sendButton.postClick();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && message.length() > 0) {
			message = message.substring(0, message.length() - 1);
		} else if (Font.letters.indexOf(Character.toUpperCase(e.getKeyChar())) >= 0) {
			message += e.getKeyChar();
		}

	}

}
