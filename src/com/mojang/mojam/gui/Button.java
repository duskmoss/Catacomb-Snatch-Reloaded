package com.mojang.mojam.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.mojam.MouseButtons;

public class Button extends GuiComponent {
	
	protected List<ButtonListener> listeners;
	protected boolean isPressed;
	
	public int x,y,w,h;
	
	protected final int id;
	protected boolean performClick=false;
	private final boolean clickable;
	
	public Button(int id){
		this.id = id;
		clickable = false;
	}
	
	
	public Button(int id, int x, int y, int width, int heigth) {
		this.id = id;
		this.x=x;
		this.y=y;
		this.w=width;
		this.h=heigth;
		clickable=true;
	}


	public void addListener(ButtonListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<ButtonListener>();
		}
		listeners.add(listener);
	}
	
	@Override
	public void tick(MouseButtons mouseButtons) {
		super.tick(mouseButtons);
		if(clickable){
			checkClick(mouseButtons);
		}
    }
	
	
	private void checkClick(MouseButtons mouseButtons){
		int mx = mouseButtons.getX() / 2;
		int my = mouseButtons.getY() / 2;
		isPressed = false;
		if (mx >= x && my >= y && mx < (x + w) && my < (y + h)) {
			if (mouseButtons.isRelased(1)) {
				postClick();
			} else if (mouseButtons.isDown(1)) {
				isPressed = true;
			}
		}
	}
    public void postClick() {
    	if (listeners != null) {
			for (ButtonListener listener : listeners) {
				listener.buttonPressed(this);
			}
		}
    }
	public boolean isPressed() {
		return isPressed;
	}
	public int getId() {
		return id;
	}

}
