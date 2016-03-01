package model;

import java.io.IOException;
import java.util.Scanner;

import databas.DBHandler;
import server.Journal;

public class CommandAdd extends Command {

	
	public CommandAdd(String[] command) throws InvalidCommandException{
		super(command);
		LENGTH=5;
		if(command.length!=LENGTH)
			super.command = setInfo();
		
		
		
		if(super.command.length!=LENGTH)
			invalidArguments("Add");
		
	}

	private String[] setInfo(){
		Scanner scan = new Scanner(System.in);
		String[] info = new String[LENGTH];
		info[0] = "add";
		System.out.print("Pnr of patient: ");
		info[1] = scan.nextLine();
		System.out.print("Name of patient: ");
		info[2] = scan.nextLine();
		System.out.print("Nurse: ");
		info[3] = scan.nextLine();
		System.out.print("Data: ");
		info[4] = scan.nextLine();
		return info;
	}
	
	public String getInfo(){
		return null;
	}
	
	@Override
	public Commands getCommand() {
		return Commands.CREATE;
	}

	@Override
	public String execute(DBHandler dbh,User user)  throws IOException{
		boolean result = dbh.add(new Journal(command[1], command[2], user.getDivision(), user.getName(), command[3], command[4]));
		if(result)
			return "Journal successfully added";
		return "Journal was not added";
	}
	
}
