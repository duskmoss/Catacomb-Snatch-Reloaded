package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;


import com.mojang.mojam.gui.menu.LevelSelectMenu;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class ArrowButton extends Button {
	
	private LevelSelectMenu menu;
	
	public ArrowButton(int id, int x, int y, LevelSelectMenu menu) {
		super(id, x, y, 50, 50);
		this.menu = menu;
	}
	
	@Override
	public void render(Screen screen) {
		if (id==KeyEvent.VK_LEFT) {
			screen.blit(Art.arrowButtons[0][0], x, y);
		} else if (id==KeyEvent.VK_RIGHT){
			screen.blit(Art.arrowButtons[1][0], x, y);
		}
	}
	@Override
	public void postClick() {
		menu.changeSelection(id);
	}

}
