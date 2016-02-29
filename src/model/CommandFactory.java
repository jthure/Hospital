package model;

public class CommandFactory {

	public static Command createCommand(String command) throws InvalidCommandException {
		String[] commandComps = command.split("\\$");
		System.out.println(commandComps[0]);
		switch (commandComps[0]) {
		case ("add"):
			return new CommandAdd(commandComps);
		case ("delete"):
			return new CommandDelete(commandComps);
		case ("read"):
			return new CommandRead(commandComps);

		default:
			throw new InvalidCommandException("Invalid command entered");
		}
	}
}
