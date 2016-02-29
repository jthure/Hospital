package databas;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	private static final String loggFileUrl = "logg.txt";
	private static FileWriter writer;

	public static void init() {
		try {
			Files.createFile(Paths.get(loggFileUrl));

		} catch (IOException e) {
			System.out.println("Logfile exists");
		}
		try {
			writer = new FileWriter(loggFileUrl, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String msg) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		try {

			writer.write(timeStamp + "\t" + msg + "\n");
			writer.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loginEvent(String user, String type, String division) {

		log("LoginEvent:\t" + "Username: " + user + " Type: " + type + " Division: " + division);
	}
	public static void userDisconnected(String user, String type, String division) {

		log("UserDisconnected:\t" + "Username: " + user + " Type: " + type + " Division: " + division);
	}
	public static void commandEntered(String user, String command) {

		log("Command:\t" + "Username: " + user + " Command: " + command);
	}
	public static void invalidCommandEntered(String user, String command) {

		log("InvalidCommand:\t" + "Username: " + user + " Command: " + command);
	}
}
