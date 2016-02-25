package model;

import java.io.IOException;

import databas.DBHandler;
import server.Journal;

public class CommandRead extends Command {

	public CommandRead(String[] command) throws InvalidCommandException {
		super(command);
		LENGTH=3;
		if(command.length!=LENGTH)
			invalidArguments("Read");
	}

	@Override
	Commands getCommand() {
		return Commands.READ;
	}
	
	//ToDo
	public String execute(DBHandler dbh)  throws IOException{
		boolean result = dbh.add(new Journal(command[1], command[2], command[3], command[4], command[5], command[6]));
		if(result)
			return "Journal successfully added";
		return "Journal was not added";
	}
}
