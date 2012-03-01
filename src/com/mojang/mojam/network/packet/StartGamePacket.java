package com.mojang.mojam.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mojang.mojam.network.Packet;
import com.mojang.mojam.network.PacketListener;

public class StartGamePacket extends Packet {

	private long gameSeed;

	public StartGamePacket() {
	}

	public StartGamePacket(long gameSeed) {
		this.gameSeed = gameSeed;
	}

	@Override
	public void read(DataInputStream dis) throws IOException {
		gameSeed = dis.readLong();
	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		dos.writeLong(gameSeed);
	}

	public long getGameSeed() {
		return gameSeed;
	}
	
	@Override
	public void handle(PacketListener packetListener) {
		packetListener.handle(this);
		
	}
}
