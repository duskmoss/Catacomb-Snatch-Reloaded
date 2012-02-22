package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class PauseMenu extends GuiMenu {
	
	private int selectedItem = 0;
	private final int gameWidth;
	private final Button returnButton;
	private int player;
	public final int buttonCenter;
	public int artHeight;
	public int textHeight;

	public PauseMenu(int gameWidth, int gameHeight, int player) {
		super(120);
		this.gameWidth = gameWidth;
		this.player = player;
		textHeight = getNextHeight();
		buttonCenter=((gameWidth-Button.width)/2);

		returnButton=addButton(new Button(TitleMenu.RETURN_ID, 5 , buttonCenter, getNextHeight()));
		addButton(new Button(TitleMenu.END_GAME_ID, 6 , buttonCenter, getNextHeight()));
		addButton(new CheckBox(TitleMenu.FPS_ID, buttonCenter, getNextHeight()));
		
		LAST_ITEM = 2;
		FIRST_ITEM = 0;
	}
	
	@Override
	public void render(Screen screen) {

		//screen.clear(0);
		// screen.blit(Art.titles[1], 0, 10);
		 //screen.blit(Art.pauseScreen, 0, 0);

		super.render(screen);
		String msg="";
		if (player == 0){
			msg = "by LORD LARD";
		}
		if (player == 1){
			msg = "by HERR VON SPECK";
		}
		int textWidth = msg.length()*8;
		int textCenter = ((gameWidth-textWidth)/2);
		
		Font.draw(screen, msg, textCenter, textHeight);
		screen.blit(Art.lordLard[0][6], buttonCenter - 40,
				150 + selectedItem * 40);
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
