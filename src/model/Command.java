package model;

import java.io.IOException;
import java.util.List;

import databas.DBHandler;
import server.Journal;

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
	protected String listOfJournalsToString(List<Journal> journals){
		StringBuilder sb = new StringBuilder();
		for (Journal j : journals){
			sb.append(j.toString()).append("\n");
		}
		if(sb.length()==0){
			return "No records found. Either they don't exist or you don't have the permission to read them";
		}
		return sb.toString();
	}

	public abstract String execute(DBHandler dbh,User user)  throws IOException;

	
}
