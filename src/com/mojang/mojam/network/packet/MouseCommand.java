package com.mojang.mojam.network.packet;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mojang.mojam.network.CommandListener;
import com.mojang.mojam.network.NetworkCommand;

public class MouseCommand extends NetworkCommand {
	boolean down;
	int x,y;

	public MouseCommand() {
	}
	
	public MouseCommand(boolean down, int x, int y){
		this.down=down;
		this.x=x;
		this.y=y;
	}


	@Override
	public void read(DataInputStream dis) throws IOException {
		down=dis.readBoolean();
		x=dis.readInt();
		y=dis.readInt();

	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		dos.writeBoolean(down);
		dos.writeInt(x);
		dos.writeInt(y);

	}
	public boolean isDown(){
		return down;
	}
	public Point getLocation() {
		return new Point(x,y);
	}



	@Override
	public void handle(int playerId, CommandListener commandListener) {
		commandListener.handle(playerId, this);

	}

	
}
