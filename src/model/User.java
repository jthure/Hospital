package model;

/**
 * Created by jonas on 2016-02-22.
 */
public abstract class User {
	private String name;
	private String division;
	
	public User(String name,String divison){
		this.name=name;
		this.division=divison;
	}
	public abstract Permission getPermission();
	
	public enum Permission{
		DOCTOR,NURSE,PATIENT,AUTHORITY
	}
	public String getName(){
		return name;
	}
	public String getDivision(){
		return name;
	}
}
