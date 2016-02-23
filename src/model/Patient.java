package model;

/**
 * Created by jonas on 2016-02-22.
 */
public class Patient extends User {
	
	public Patient(String name){
		super(name,"");
	}
	public Patient(String name, String division){
		super(name,division);
	}

	@Override
	public Permission getPermission() {
		return Permission.PATIENT;
	}
}
