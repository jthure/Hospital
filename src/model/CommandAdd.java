package model;

public class CommandAdd extends Command {

	public CommandAdd(String[] command) throws InvalidCommandException{
		super(command);
		LENGTH=5;
		if(command.length!=LENGTH)
			invalidArguments("Add");
	}

	@Override
	Commands getCommand() {
		return Commands.CREATE;
	}
	
}
