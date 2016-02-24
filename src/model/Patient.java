package model;

import model.Command.Commands;

/**
 * Created by jonas on 2016-02-22.
 */
public class Patient extends User {
	
	public Patient(String name){
		this(name,"N/A");
	}
	public Patient(String name, String division){
		super(name,division);
		permittedCommands.add(Commands.READ);
	}

	@Override
	public Permission getPermission() {
		return Permission.PATIENT;
	}
	@Override
	public String getType() {
		
		return Patient.class.getSimpleName();
	}
}
