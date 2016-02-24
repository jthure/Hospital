package server;

import java.util.List;

public class JournalResponse extends Response {
	private List<Journal> journals;

	public JournalResponse(List<Journal> journals) {
		this.journals = journals;
	}

	@Override
	protected String response() {
		StringBuilder sb = new StringBuilder();
		sb.append("Har kommer ett resultat med journals som inte visas än");
		return sb.toString();
	}

}
