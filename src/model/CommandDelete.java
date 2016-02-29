package model;

import java.io.IOException;

import databas.DBHandler;
import server.Journal;

public class CommandDelete extends Command {

	public CommandDelete(String[] command) throws InvalidCommandException {
		super(command);
		LENGTH=2;
		if(command.length!=LENGTH)
			invalidArguments("Delete");
	}

	@Override
	Commands getCommand() {
		return Commands.DELETE;
	}
	
	//ToDo
	public String execute(DBHandler dbh, User user)  throws IOException{
		boolean result = dbh.delete(command[1]);
		if(result)
			return "Journal with id: "+command[1]+" was deleted";
		return "Journal with id: "+command[1]+" could NOT be deleted";
	}
}
