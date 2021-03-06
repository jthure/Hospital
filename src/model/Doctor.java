package model;

import model.Command.Commands;

/**
 * Created by jonas on 2016-02-22.
 */
public class Doctor extends User {

	public Doctor(String name,String division) {
		super(name,division);
		permittedCommands.add(Commands.CREATE);
		permittedCommands.add(Commands.READ);
		permittedCommands.add(Commands.WRITE);
		
	}

	@Override
	public Permission getPermission() {
		
		return Permission.DOCTOR;
	}
	@Override
	public String getType() {
		
		return Doctor.class.getSimpleName();
	}


}
