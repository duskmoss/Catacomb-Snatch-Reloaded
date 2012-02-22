package com.mojang.mojam.gui;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.mojang.mojam.MouseButtons;
import com.mojang.mojam.screen.Screen;

public abstract class GuiMenu extends GuiComponent implements ButtonListener,
		KeyListener {

	
	protected int LAST_ITEM;
	protected int FIRST_ITEM;
	private int HEIGHT=-1;
	private int initial;
	
	public int getNextHeight() {
		if(HEIGHT==-1){
			HEIGHT=initial;
		}else{
			HEIGHT += 40;
		}
		return HEIGHT;
	}
	public int getSameHeight(){
		return HEIGHT;
	}

	public GuiMenu(int init){
		LAST_ITEM = 0 ;
		FIRST_ITEM = 0;
		initial = init;
		
	}
	
	protected List<Button> buttons = new ArrayList<Button>();

	
	
	protected Button addButton(Button button) {
		buttons.add(button);
		button.addListener(this);
		return button;
	}

	@Override
	public void render(Screen screen) {
		super.render(screen);

		for (Button button : buttons) {
			button.render(screen);
		}
	}

	@Override
	public void tick(MouseButtons mouseButtons) {
		super.tick(mouseButtons);

		for (Button button : buttons) {
			button.tick(mouseButtons);
		}
	}

	public void addButtonListener(ButtonListener listener) {
		for (Button button : buttons) {
			button.addListener(listener);
		}
	}

	@Override
	public void buttonPressed(Button button) {
	}

}
