package com.mojang.mojam.gui.menu;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.mojang.mojam.MouseButtons;
import com.mojang.mojam.gui.Button;
import com.mojang.mojam.gui.ButtonListener;
import com.mojang.mojam.gui.GuiComponent;
import com.mojang.mojam.screen.Screen;

public abstract class GuiMenu extends GuiComponent implements KeyListener {

	public static final int START_GAME_ID = 1000;
	public static final int EXIT_GAME_ID = 1001;
	public static final int HOST_GAME_ID = 1002;
	public static final int JOIN_GAME_ID = 1003;
	
	public static final int START_LEVEL_ID = 1017;
	public static final int HOST_LEVEL_ID = 1018;

	public static final int CANCEL_JOIN_ID = 1004;
	public static final int PERFORM_JOIN_ID = 1005;
	public static final int RESTART_GAME_ID = 1006;

	public static final int BACK_ID = 1007;

	public static final int RETURN_ID = 1101;
	public static final int FPS_ID = 1102;
	public static final int END_GAME_ID = 1103;
	public static final int SEND_ID = 1104;

	public GuiMenu() {

	}

	protected List<Button> buttons = new ArrayList<Button>();

	protected Button addButton(Button button) {
		buttons.add(button);
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
}
