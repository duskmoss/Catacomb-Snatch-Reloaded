package com.mojang.mojam.gui;

import java.util.Map;
import java.util.Stack;
import java.util.Date;
import java.util.TreeMap;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.screen.Screen;

public class ChatStack extends Stack<String> {

	private static final long serialVersionUID = 1103290634261453987L;
	
	public static final int CENTER=236837; //center if dialed on phone;

	public static final int MAX = 3;

	private Map<String, Long> times = new TreeMap<String, Long>();

	private int life;
	
	public ChatStack() {
		super();
		life=5000;
	}

	public ChatStack(int i) {
		this();
		life=i*1000;
	}



	@Override
	public String push(String s) {
		if (size() == MAX) {
			cut();
		}
		super.push(s);
		times.put(s, new Date().getTime());
		return s;
	}

	
	
	public void render(Screen screen, int x, int y){
		if(x==CENTER){
			center(screen, y);
			return;
		}
		if (size() > 0) {
			ChatStack temp = clone();
			int ypos=y;
			while (!temp.isEmpty()) {
				Font.draw(screen, temp.pop(), x, ypos);
				ypos -= 10;
			}
		}
	}

	private void center(Screen screen, int y) {
		if (size() > 0) {
			ChatStack temp = clone();
			int ypos=y;
			int x = ((MojamComponent.GAME_WIDTH - Font.getStringWidth(temp.peek())) / 2);
			while (!temp.isEmpty()) {
				Font.draw(screen, temp.pop(), x, ypos);
				ypos += 10;
			}
		}
	}



	public void trim() {
		if (isEmpty()) {
			return;
		}
		String s = get(0);
		long expTime = times.get(s) + life;
		long curTime = new Date().getTime();
		if (expTime < curTime) {
			cut();
		}

	}
	
	private void cut() {
		String s = remove(0);
		times.remove(s);

	}
	
	@Override 
	public ChatStack clone(){
		return (ChatStack) super.clone();
	}

}
