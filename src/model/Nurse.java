package model;

/**
 * Created by jonas on 2016-02-22.
 */
public class Nurse extends User {

	public Nurse(String name,String division) {
		super(name,division);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Permission getPermission() {
		return Permission.NURSE;
	}
}
