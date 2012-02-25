package com.mojang.mojam.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import com.mojang.mojam.network.PacketListener;

public abstract class Packet {


	public final int getId() {
		return PacketMap.get(getClass());
	}

	public abstract void read(DataInputStream dis) throws IOException;

	public abstract void write(DataOutputStream dos) throws IOException;

	public static void writePacket(Packet packet, DataOutputStream dos)
			throws IOException {
		dos.write(packet.getId());
		packet.write(dos);
	}

	public static Packet readPacket(DataInputStream inputStream)
			throws IOException {

		int id = 0;
		Packet packet = null;

		try {
			id = inputStream.read();
			if (id == -1)
				return null;

			packet = getPacket(id);
			if (packet == null)
				throw new IOException("Bad packet id " + id);

			packet.read(inputStream);

		} catch (EOFException e) {
			// reached end of stream
			System.out.println("Reached end of stream");
			return null;
		}

		return packet;
	}

	public static Packet getPacket(int id) {
		try {
			Class<? extends Packet> clazz = PacketMap.get(id);
			if (clazz == null){
				return null;
			}
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Skipping packet with id " + id);
			return null;
		}
	}

	public void handle(PacketListener packetListener) {
		packetListener.handle(this);
	}

}
