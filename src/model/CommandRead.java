package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import databas.DBHandler;
import server.Journal;

public class CommandRead extends Command {

	public CommandRead(String[] command) throws InvalidCommandException {
		super(command);
		
		LENGTH=2;
		
		if(command.length!=LENGTH)
			super.command = setInfo();
		
		if(super.command.length!=LENGTH)
			invalidArguments("Read");
	}

	public String[] setInfo(){
		Scanner scan = new Scanner(System.in);
		String[] info = new String[LENGTH];
		info[0] = "read";
		System.out.print("Name or pnr of patient: ");
		info[1] = scan.nextLine();
		scan.close();
		return info;
		
	}
	
	@Override
	Commands getCommand() {
		return Commands.READ;
	}

	public String execute(DBHandler dbh, User user)  throws IOException{
		return listOfJournalsToString(dbh.read(command[1], user));
	
	}
}
