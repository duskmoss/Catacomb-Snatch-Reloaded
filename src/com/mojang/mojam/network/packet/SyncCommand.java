package com.mojang.mojam.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mojang.mojam.network.CommandListener;

public class SyncCommand extends NetworkCommand {

	public double posx, posy, xAim, yAim;
	public int facing2;
	public boolean firing;

	public SyncCommand() {
		
	}
	
	public SyncCommand(double x, double y, double xa, double ya, boolean fire){
		posx=x;
		posy=y;
		xAim=xa;
		yAim=ya;
		firing=fire;
				
	}

	@Override
	public void read(DataInputStream dis) throws IOException {
		posx=dis.readDouble();
		posy=dis.readDouble();
		xAim=dis.readDouble();
		yAim=dis.readDouble();
		firing=dis.readBoolean();

	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		dos.writeDouble(posx);
		dos.writeDouble(posy);
		dos.writeDouble(xAim);
		dos.writeDouble(yAim);
		dos.writeBoolean(firing);

	}
	
	@Override
	public void handle(int i, CommandListener commandListener) {
		commandListener.handle(i, this);
		
	}

}
