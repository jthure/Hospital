package model;

import java.io.IOException;
import java.util.Scanner;

import databas.DBHandler;
import server.Journal;

public class CommandDelete extends Command {

	public CommandDelete(String[] command) throws InvalidCommandException {
		super(command);
		LENGTH=2;
		if(command.length!=LENGTH)
			super.command = setInfo();
		
		
		
		if(super.command.length!=LENGTH)
			invalidArguments("Delete");
	}

	private String[] setInfo(){
		Scanner scan = new Scanner(System.in);
		String[] info = new String[LENGTH];
		info[0] = "delete";
		System.out.print("Journal id: ");
		info[1] = scan.nextLine();
		return info;
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
