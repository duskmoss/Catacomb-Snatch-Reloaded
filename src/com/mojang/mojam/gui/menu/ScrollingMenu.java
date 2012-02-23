package com.mojang.mojam.gui.menu;

import java.awt.event.KeyEvent;

public class ScrollingMenu extends GuiMenu {

	protected int LAST_ITEM;
	protected int FIRST_ITEM;
	protected int INITIAL;

	protected int returnItem;

	private int HEIGHT = -1;

	protected int selectedItem = 0;

	public ScrollingMenu(int init, int last, int first) {
		super();
		LAST_ITEM = last;
		FIRST_ITEM = first;
		INITIAL = init;
	}

	public int getNextHeight() {
		if (HEIGHT == -1) {
			HEIGHT = INITIAL;
		} else {
			HEIGHT += 40;
		}
		return HEIGHT;
	}

	public int getSameHeight() {
		return HEIGHT;
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
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			e.consume();
			buttons.get(returnItem).postClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
