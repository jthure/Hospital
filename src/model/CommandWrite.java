package model;

import java.io.IOException;
import java.util.Scanner;

import databas.DBHandler;
import server.Journal;

public class CommandWrite extends Command {

	
	public CommandWrite(String[] command) throws InvalidCommandException{
		super(command);
		LENGTH=4;
		if(command.length != LENGTH)
			super.command=setInfo();
		
		if(super.command.length!=LENGTH)
			invalidArguments("Write");
		
	}
	private String[] setInfo(){
		Scanner scan = new Scanner(System.in);
		String[] info = new String[LENGTH];
		info[0] = "write";
		System.out.print("Journal ID: ");
		info[1] = scan.nextLine();
		System.out.print("Data: ");
		info[2] = scan.nextLine();
		System.out.println("Append? (true/false): ");
		info[3] = scan.nextLine();
		return info;
	}


	
	@Override
	Commands getCommand() {
		return Commands.WRITE;
	}

	@Override
	public String execute(DBHandler dbh, User user)  throws IOException{
		boolean append = Boolean.parseBoolean(command[3]);
		boolean result = dbh.write(command[1], command[2], user, append);
		if(result)
			return "Journal successfully added";
		return "Journal was not added";
	}
	
}
