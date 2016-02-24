package server;

public abstract class Response {
	@Override
	public String toString(){
		return response();
	}
	protected abstract String response();
}
