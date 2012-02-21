package com.mojang.mojam.gui;

import com.mojang.mojam.MouseButtons;

public class CheckBox extends Button {
	
	private boolean isChecked;

	public CheckBox(int id, int x, int y) {
		super(id, x, y);
		width = 24;
		heigth = 24;
		isChecked=false;
	}
	
	public CheckBox(int id, int x, int y, boolean initial){
		super(id, x, y);
		width = 24;
		heigth = 24;
		isChecked=initial;
	}
	
	@Override
	public void tick(MouseButtons mouseButtons) {
		super.tick(mouseButtons);

		int mx = mouseButtons.getX() / 2;
		int my = mouseButtons.getY() / 2;
		if (mx >= x && my >= y && mx < (x + width) && my < (y + heigth)) {
			if (mouseButtons.isRelased(1)) {
				postClick();
			} 
		}
		
		if (performClick) {
            if (listeners != null) {
                for (ButtonListener listener : listeners) {
                    listener.buttonPressed(this);
                }
            }
            performClick = false;
        }
    }

    public void postClick() {
    	isChecked=!isChecked;
        performClick = true;
    }
	
	public boolean isChecked(){
		return isChecked;
	}

}
