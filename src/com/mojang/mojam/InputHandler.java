package com.mojang.mojam;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import com.mojang.mojam.Keys.Key;

public class InputHandler implements KeyListener {
	private Map<Integer, Key> mappings = new HashMap<Integer, Key>();

	public InputHandler(Keys keys) {
		mappings.put(KeyEvent.VK_UP, keys.fireup);
		mappings.put(KeyEvent.VK_DOWN, keys.firedown);
		mappings.put(KeyEvent.VK_LEFT, keys.fireleft);
		mappings.put(KeyEvent.VK_RIGHT, keys.fireright);

		mappings.put(KeyEvent.VK_W, keys.up);
		mappings.put(KeyEvent.VK_S, keys.down);
		mappings.put(KeyEvent.VK_A, keys.left);
		mappings.put(KeyEvent.VK_D, keys.right);

		mappings.put(KeyEvent.VK_X, keys.build);
		mappings.put(KeyEvent.VK_Z, keys.use);
		mappings.put(KeyEvent.VK_R, keys.build);
		mappings.put(KeyEvent.VK_E, keys.use);
	}

	public void keyPressed(KeyEvent ke) {
		toggle(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		toggle(ke, false);
	}

	public void keyTyped(KeyEvent ke) {
	}

	private void toggle(KeyEvent ke, boolean state) {
		Key key = mappings.get(ke.getKeyCode());
		if (key != null) {
			key.nextState = state;
		}
	}
}
