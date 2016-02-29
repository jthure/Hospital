package server;

import java.io.IOException;
import java.util.List;

import databas.DBHandler;
import model.Command;

public class JournalResponse extends Response {
	private String msg;
	
	
	public JournalResponse(Command cmd, DBHandler dbh) {
		try {
			msg=cmd.execute(dbh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected String response() {
		StringBuilder sb = new StringBuilder();
		sb.append("Har kommer ett resultat med journals som inte visas än");
		return sb.toString();
	}

}
