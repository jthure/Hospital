package model;

public class CommandAdd extends Command {

	public CommandAdd(String[] command) throws InvalidCommandException {
		super(command);
		LENGTH=3;
		if(command.length!=LENGTH)
			throw new InvalidCommandException("Add command should be of length "+LENGTH);
	}
	
}
