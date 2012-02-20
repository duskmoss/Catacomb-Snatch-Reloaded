package com.mojang.mojam;

import java.applet.Applet;
import java.awt.BorderLayout;

public class MojamApplet extends Applet {
	private static final long serialVersionUID = 1L;

	private MojamComponent game;

	@Override
	public void init() {
		game = new MojamComponent();
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	}

	@Override
	public void start() {
		game.start();
	}

	@Override
	public void stop() {
		game.stop();
	}
}