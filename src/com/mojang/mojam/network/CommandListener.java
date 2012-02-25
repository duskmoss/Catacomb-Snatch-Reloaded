package com.mojang.mojam.network;

import com.mojang.mojam.network.packet.*;

public interface CommandListener {

	
	public abstract void handle(int i, ChatCommand command);
	
	public abstract void handle(int i, EndGameCommand command);
	
	public abstract void handle(int i, PauseCommand command);
	
	public abstract void handle(int i, PickupCommand command);
	
	public abstract void handle(int i, SyncCommand command);

}
