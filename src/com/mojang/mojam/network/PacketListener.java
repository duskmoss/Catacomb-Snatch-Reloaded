package com.mojang.mojam.network;

import com.mojang.mojam.network.packet.LevelPacket;
import com.mojang.mojam.network.packet.StartGamePacket;
import com.mojang.mojam.network.packet.TurnPacket;
import com.mojang.mojam.network.packet.VerifyLevelPacket;

public interface PacketListener {

	public void handle(StartGamePacket packet);
	public void handle(TurnPacket packet);
	public void handle(VerifyLevelPacket packet);
	public void handle(LevelPacket packet);


}
