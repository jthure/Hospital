package model;

import model.Command.Commands;

/**
 * Created by jonas on 2016-02-22.
 */
public class Nurse extends User {

	public Nurse(String name,String division) {
		super(name,division);
		permittedCommands.add(Commands.READ);
		permittedCommands.add(Commands.WRITE);
		
	}

	@Override
	public Permission getPermission() {
		return Permission.NURSE;
	}
	@Override
	public String getType() {
		
		return Nurse.class.getSimpleName();
	}


}
