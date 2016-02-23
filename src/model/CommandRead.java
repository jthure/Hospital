package model;

public class CommandRead extends Command {

	public CommandRead(String[] command) throws InvalidCommandException {
		super(command);
		LENGTH=3;
		if(command.length!=LENGTH)
			invalidArguments("Read");;
	}
	
}
