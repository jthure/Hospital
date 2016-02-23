package model;

public class CommandFactory {
	
	public Command createCommand(String command) throws InvalidCommandException{
		String[] commandComps = command.split(" ");
		switch(commandComps[0]){
			case("add"):
					return new CommandAdd(commandComps);
			case("del"):
				
				break;
			
			default:
				throw new InvalidCommandException("Invalid command entered");
		}
		
		return null;
	}
}
