package model;

public class CommandDelete extends Command {

	public CommandDelete(String[] command) throws InvalidCommandException {
		super(command);
		LENGTH=3;
		if(command.length!=LENGTH)
			invalidArguments("Delete");
	}
	
}
