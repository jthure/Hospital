package model;

import model.Command.Commands;

/**
 * Created by jonas on 2016-02-22.
 */
public class Authority extends User {

	public Authority(String name) {
		this(name,"N/A");
	}
	public Authority(String name, String division){
		super(name,division);
		permittedCommands.add(Commands.READ);
		permittedCommands.add(Commands.DELETE);
	}

	@Override
	public Permission getPermission() {
		return Permission.AUTHORITY;
	}
	@Override
	public String getType() {
		
		return Authority.class.getSimpleName();
	}

}
