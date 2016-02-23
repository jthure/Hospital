package model;

/**
 * Created by jonas on 2016-02-22.
 */
public class Doctor extends User {

	public Doctor(String name,String division) {
		super(name,division);
	}

	@Override
	public Permission getPermission() {
		
		return Permission.DOCTOR;
	}
}
