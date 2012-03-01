package com.mojang.mojam.gui.menu;

import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class TitleMenu extends ScrollingMenu {

	// public static lol... ;)
	public static String ip = "";

	public final int buttonCenter;

	public TitleMenu(int gameWidth, int gameHeight) {
		super(200, 3, 0);
		returnItem = 3;

		buttonCenter = ((gameWidth - MenuButton.width) / 2);

		addButton(new MenuButton(GuiMenu.START_LEVEL_ID, 0, buttonCenter,
				getNextHeight()));
		addButton(new MenuButton(GuiMenu.HOST_LEVEL_ID, 2, buttonCenter,
				getNextHeight()));
		addButton(new MenuButton(JOIN_GAME_ID, 3, buttonCenter, getNextHeight()));
		addButton(new MenuButton(EXIT_GAME_ID, 1, buttonCenter, getNextHeight()));
	}

	@Override
	public void render(Screen screen) {

		screen.clear(0);
		// screen.blit(Art.titles[1], 0, 10);
		screen.blit(Art.titleScreen, 0, 0);

		super.render(screen);

		screen.blit(Art.lordLard[0][6], buttonCenter - 40, INITIAL - 10
				+ selectedItem * 40);
	}

}
