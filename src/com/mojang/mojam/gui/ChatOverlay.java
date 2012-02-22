package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.gui.menu.GuiMenu;
import com.mojang.mojam.gui.menu.TitleMenu;

public class ChatOverlay extends GuiMenu{
	
	private String message;
	private String header;
	private Button sendButton;
	
	public ChatOverlay(int player){
		if(player==0){
			header="Lord Lard:";
		}else if(player==1){
			header="Herr Von Speck:";
		}
		sendButton=new Button(GuiMenu.SEND_ID);
	}
	
	public String getMessage(){
		return header+message;		
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER && message.length() > 0) {
			sendButton.postClick();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE
				&& message.length() > 0) {
			message = message.substring(0, message.length() - 1);
		} else if (Font.letters.indexOf(Character.toUpperCase(e.getKeyChar())) >= 0) {
			message += e.getKeyChar();
		}

	}

}
