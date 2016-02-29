package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import databas.DBHandler;
import server.Journal;

public class CommandRead extends Command {

	public CommandRead(String[] command) throws InvalidCommandException {
		super(command);
		LENGTH=2;
		if(command.length!=LENGTH)
			invalidArguments("Read");
	}

	@Override
	Commands getCommand() {
		return Commands.READ;
	}

	public String execute(DBHandler dbh, User user)  throws IOException{
		if(command[1].length()==10)
			return listOfJournalsToString(dbh.readByPnr(command[1]));
		else
			return listOfJournalsToString(dbh.readById(command[1]));
	
	}
}
