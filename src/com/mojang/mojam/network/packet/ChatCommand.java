package com.mojang.mojam.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mojang.mojam.network.CommandListener;
import com.mojang.mojam.network.NetworkCommand;

public class ChatCommand extends NetworkCommand {
	
	public static final int CHAT_TYPE=127;
	public static final int NOTE_TYPE=129;

	byte[] message;
	int message_type;

	public ChatCommand() {
		message = "".getBytes();
	}

	public ChatCommand(String msg, int type) {
		message = msg.getBytes();
		message_type = type;

	}

	@Override
	public void read(DataInputStream dis) throws IOException {
		message = new byte[dis.readInt()];
		dis.readFully(message);
		message_type = dis.readInt();

	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		dos.writeInt(message.length);
		dos.write(message);
		dos.writeInt(message_type);

	}

	public String getMessage() {
		return new String(message);
	}

	public int getType() {
		return message_type;
	}
	
	public void handle(int playerId, CommandListener listener){
		listener.handle(playerId, this);
	}

}
