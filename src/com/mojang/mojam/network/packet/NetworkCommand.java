package com.mojang.mojam.network.packet;

import com.mojang.mojam.network.CommandListener;

public abstract class NetworkCommand extends Packet {

	public abstract void handle(int i, CommandListener commandListener);
	

}
