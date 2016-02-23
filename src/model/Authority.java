package model;

/**
 * Created by jonas on 2016-02-22.
 */
public class Authority extends User {

	public Authority(String name) {
		super(name,"");
	}
	public Authority(String name, String division){
		super(name,division);
	}

	@Override
	public Permission getPermission() {
		return Permission.AUTHORITY;
	}
}
