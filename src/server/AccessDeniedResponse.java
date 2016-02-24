package server;

public class AccessDeniedResponse extends Response {
	private String message;
	public  AccessDeniedResponse() {
		this("Access denied");
	}
	public AccessDeniedResponse(String msg){
		message = msg;
	}

	@Override
	protected String response() {
		return message;
	}

}
