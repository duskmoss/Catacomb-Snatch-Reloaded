package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class PauseMenu extends GuiMenu {
	
	private int selectedItem = 0;
	private final int gameWidth;
	private final Button returnButton;
	
	
	
	public static final int RETURN_ID = 1101;
	public static final int FPS_ID = 1102;
	public static final int END_GAME_ID = 1103;

	public PauseMenu(int gameWidth, int gameHeight) {
		super();
		this.gameWidth = gameWidth;

		returnButton=addButton(new Button(RETURN_ID, 5 , (gameWidth - 128) / 2, 200));
		addButton(new Button(END_GAME_ID, 6 , (gameWidth - 128) / 2, 240));
		addButton(new CheckBox(END_GAME_ID, (gameWidth - 128) / 2, 280));
		
		LAST_ITEM = 2;
		FIRST_ITEM = 0;
	}
	
	@Override
	public void render(Screen screen) {

		//screen.clear(0);
		// screen.blit(Art.titles[1], 0, 10);
		 //screen.blit(Art.pauseScreen, 0, 0);

		super.render(screen);

		screen.blit(Art.lordLard[0][6], (gameWidth - 128) / 2 - 40,
				190 + selectedItem * 40);
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
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			e.consume();
			returnButton.postClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
