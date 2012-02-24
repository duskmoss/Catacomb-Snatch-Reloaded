package com.mojang.mojam.gui.menu;

import com.mojang.mojam.entity.mob.Team;
import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class WinMenu extends ScrollingMenu {

	private int winningPlayer;
	private final int gameWidth;

	private int textHeight;
	public final int buttonCenter;

	public WinMenu(int gameWidth, int gameHeight, int winningPlayer) {
		super(160, 0, 0);
		returnItem = 0;

		this.winningPlayer = winningPlayer;
		this.gameWidth = gameWidth;

		textHeight = getNextHeight();
		buttonCenter = ((gameWidth - MenuButton.width) / 2);

		addButton(new MenuButton(GuiMenu.RESTART_GAME_ID, 6, buttonCenter,
				getNextHeight()));
	}

	@Override
	public void render(Screen screen) {
		screen.clear(0);
		screen.blit(Art.gameOverScreen, 0, 0);

		String msg = "";
		if (winningPlayer == Team.Team1) {
			msg = "LORD LARD WINS WOOHOO";
		}
		if (winningPlayer == Team.Team2) {
			msg = "HERR VON SPECK WINS YAY";
		}
		int textCenter = ((gameWidth - Font.getStringWidth(msg)) / 2);
		Font.draw(screen, msg, textCenter, textHeight);

		super.render(screen);

		if (winningPlayer == 0)
			screen.blit(Art.lordLard[0][6], buttonCenter - 40,
					190 + selectedItem * 40);
		if (winningPlayer == 1)
			screen.blit(Art.herrSpeck[0][6], buttonCenter - 40,
					190 + selectedItem * 40);
	}
}
