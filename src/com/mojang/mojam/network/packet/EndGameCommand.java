package com.mojang.mojam.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mojang.mojam.network.CommandListener;
import com.mojang.mojam.network.NetworkCommand;

public class EndGameCommand extends NetworkCommand {

	private int winner;

	public EndGameCommand() {
	}

	public EndGameCommand(int key) {
		this.winner = key;
	}

	@Override
	public void read(DataInputStream dis) throws IOException {
		winner = dis.readInt();
	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		dos.writeInt(winner);
	}

	public int getWinner() {
		return winner;
	}
	
	public void handle(int playerId, CommandListener listener){
		listener.handle(playerId, this);
	}

}
