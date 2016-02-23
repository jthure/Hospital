package model;

/**
 * Created by jonas on 2016-02-22.
 */
public abstract class Command {
	protected int LENGTH;
	protected String[] command;
	
	public Command(String[] command){
		this.command=command;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<LENGTH;i++){
			sb.append(command[i]);
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
