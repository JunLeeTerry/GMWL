package com.gmsz.domain;

public class ResetPackage {
	private byte[][] command;
	public ResetPackage(byte[][] commands){
		this.command = commands;
	}
	
	public void setCommand(byte[][] command){
		this.command = command;
	}
	
	public byte[][] getCommand(){
		return this.command;
	}
}
