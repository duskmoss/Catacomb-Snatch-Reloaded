package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class TitleMenu extends GuiMenu {
	
	

	public static final int START_GAME_ID = 1000;
	public static final int HOST_GAME_ID = 1002;
	public static final int JOIN_GAME_ID = 1003;
	public static final int EXIT_GAME_ID = 1001;

	public static final int CANCEL_JOIN_ID = 1004;
	public static final int PERFORM_JOIN_ID = 1005;
	public static final int RESTART_GAME_ID = 1006;
	
	public static final int BACK_ID = 1007;
	
	public static final int RETURN_ID = 1101;
	public static final int FPS_ID = 1102;
	public static final int END_GAME_ID = 1103;
	
	// public static lol... ;)
	public static String ip = "";

	private int selectedItem = 0;
	public final int buttonCenter;

	public TitleMenu(int gameWidth, int gameHeight) {
		super(200);
		buttonCenter=((gameWidth-Button.width)/2);

		addButton(new Button(START_GAME_ID, 0, buttonCenter, getNextHeight()));
		addButton(new Button(HOST_GAME_ID, 2, buttonCenter, getNextHeight()));
		addButton(new Button(JOIN_GAME_ID, 3, buttonCenter, getNextHeight()));
		addButton(new Button(EXIT_GAME_ID, 1, buttonCenter, getNextHeight()));
		LAST_ITEM=3;
		FIRST_ITEM=0;
	}

	@Override
	public void render(Screen screen) {

		screen.clear(0);
		// screen.blit(Art.titles[1], 0, 10);
		screen.blit(Art.titleScreen, 0, 0);

		super.render(screen);

		screen.blit(Art.lordLard[0][6], buttonCenter - 40,
				190 + selectedItem * 40);
	}

	@Override
	public void buttonPressed(Button button) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			selectedItem--;
			if (selectedItem < FIRST_ITEM) {
				selectedItem = LAST_ITEM;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			selectedItem++;
			if (selectedItem > LAST_ITEM) {
				selectedItem = FIRST_ITEM;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
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
