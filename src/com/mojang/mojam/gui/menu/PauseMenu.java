package com.mojang.mojam.gui.menu;

import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.gui.CheckBox;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class PauseMenu extends ScrollingMenu implements Overlay {

	private final int gameWidth;
	private int player;

	public int textHeight;
	public final int buttonCenter;

	public PauseMenu(int gameWidth, int gameHeight, boolean checked, int player) {
		super(160, 2, 0);
		returnItem = 0;

		this.gameWidth = gameWidth;
		this.player = player;

		textHeight = 120;
		buttonCenter = ((gameWidth - MenuButton.width) / 2);

		addButton(new MenuButton(GuiMenu.RETURN_ID, 5, buttonCenter,
				getNextHeight()));
		addButton(new MenuButton(GuiMenu.END_GAME_ID, 6, buttonCenter,
				getNextHeight()));
		String text = "Show FPS Counter";
		addButton(new CheckBox(GuiMenu.FPS_ID, text, buttonCenter,
				getNextHeight(), checked));
	}

	@Override
	public void render(Screen screen) {

		String msg = "";
		int textCenter;

		super.render(screen);

		screen.blit(Art.pauseScreen, 0, 0);

		if (player == 0) {
			msg = "by LORD LARD";
		}
		if (player == 1) {
			msg = "by HERR VON SPECK";
		}
		textCenter = ((gameWidth - Font.getStringWidth(msg)) / 2);
		Font.draw(screen, msg, textCenter, textHeight);

		screen.blit(Art.lordLard[0][6], buttonCenter - 40, INITIAL - 10
				+ selectedItem * 40);
	}

}
