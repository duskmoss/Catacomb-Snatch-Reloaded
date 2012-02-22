package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class WinMenu extends GuiMenu {
	private int selectedItem = 0;
	private final int gameWidth;
	private int winningPlayer;
	private int textHeight;
	public final int buttonCenter;

	public WinMenu(int gameWidth, int gameHeight, int winningPlayer) {
		super(160);
		buttonCenter=((gameWidth-Button.width)/2);
		this.winningPlayer = winningPlayer;
		this.gameWidth = gameWidth;
		textHeight = getNextHeight();

		addButton(new Button(TitleMenu.RESTART_GAME_ID, 6,
				buttonCenter, getNextHeight()));
	}

	@Override
	public void render(Screen screen) {
		screen.clear(0);
		screen.blit(Art.gameOverScreen, 0, 0);

		String msg = "";
		if (winningPlayer == 0){
			msg = "LORD LARD WINS WOOHOO";
		}
		if (winningPlayer == 1){
			msg = "HERR VON SPECK WINS YAY";
		}
		int textWidth = msg.length()*8;
		int textCenter = ((gameWidth-textWidth)/2);
		Font.draw(screen, msg, textCenter, textHeight);
		

		super.render(screen);

		if (winningPlayer == 0)
			screen.blit(Art.lordLard[0][6], buttonCenter - 40,
					190 + selectedItem * 40);
		if (winningPlayer == 1)
			screen.blit(Art.herrSpeck[0][6], buttonCenter - 40,
					190 + selectedItem * 40);
	}

	@Override
	public void buttonPressed(Button button) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			buttons.get(selectedItem).postClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
