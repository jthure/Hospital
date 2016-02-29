package model;

import java.util.LinkedList;
import java.util.List;

import model.Command.Commands;

/**
 * Created by jonas on 2016-02-22.
 */
public abstract class User {
	private String name;
	private String division;
	protected List<Commands> permittedCommands;

	public User(String name, String divison) {
		permittedCommands = new LinkedList<>();
		this.name = name;
		this.division = divison;
	}

	public abstract Permission getPermission();

	public abstract String getType();

	public enum Permission {
		DOCTOR, NURSE, PATIENT, AUTHORITY
	}

	public String getName() {
		return name;
	}

	public String getDivision() {
		return division;
	}
	
	public String info() {
		StringBuilder sb = new StringBuilder();
		return sb.append("Name: ").append(name).append(", ").
		append("Position: ").append(getType()).append(", ").
		append("Division: ").append(division).toString();
	}

	public boolean checkCommandPermission(Command cmd){
		return permittedCommands.contains(cmd.getCommand());
	}
}
