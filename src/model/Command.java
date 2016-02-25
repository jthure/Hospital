package model;

import java.io.IOException;

import databas.DBHandler;

/**
 * Created by jonas on 2016-02-22.
 */
public abstract class Command {
	protected int LENGTH;
	protected String[] command;
	
	public Command(String[] command){
		this.command=command;
	}
	
	public void invalidArguments(String commandName) throws InvalidCommandException{
		throw new InvalidCommandException("Error: "+commandName+"should be of length "+LENGTH);
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<LENGTH;i++){
			sb.append(command[i]);
			sb.append("$");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	abstract Commands getCommand();
	
	enum Commands{
		READ,WRITE,DELETE,CREATE
	}

	public abstract String execute(DBHandler dbh)  throws IOException;

	
}
