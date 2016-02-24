package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.X509Certificate;

import model.Authority;
import model.Command;
import model.CommandFactory;
import model.Doctor;
import model.InvalidCommandException;
import model.Nurse;
import model.Patient;
import model.User;
import databas.Logger;

public class Server implements Runnable {
	private ServerSocket serverSocket = null;
	private static int numConnectedClients = 0;
	private static String certPath = "certificates/Server/";

	public Server(ServerSocket ss) throws IOException {
		serverSocket = ss;
		newListener();
	}

	public void run() {
		try {
			SSLSocket socket = (SSLSocket) serverSocket.accept();
			newListener();
			SSLSession session = socket.getSession();
			X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
			
			numConnectedClients++;
			String subject = cert.getSubjectDN().getName();
			
			String[] info = new String[] { subject.split("CN=")[1].split(",")[0], subject.split("O=")[1].split(",")[0],
					subject.split("OU=")[1].split(",")[0] };
			Logger.loginEvent(info[0], info[1], info[2]);
			
			User user = null;
			switch (info[1]) {
			case ("Doctor"):
				user = new Doctor(info[0], info[2]);
				break;
			case ("Nurse"):
				user = new Nurse(info[0], info[2]);
				break;
			case ("Patient"):
				user = new Patient(info[0]);
				break;
			case ("Authority"):
				user = new Authority(info[0]);
				break;
			default:
				throw new InvalidUserException("Invalid user type in certificate");
				// Log

			}

			System.out.println("client connected");
			System.out.println("User info > " + user.info());
			System.out.println(numConnectedClients + " concurrent connection(s)\n");

			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String clientMsg = null;
			while ((clientMsg = in.readLine()) != null) {
				System.out.println("received '" + clientMsg + "' from client");
				Command cmd = null;
				try {
					cmd = CommandFactory.createCommand(clientMsg);
				} catch (InvalidCommandException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (cmd != null) {
					Response response = getData(user, cmd);
					out.println("Recieved commando: " + clientMsg);
					out.flush();
					System.out.println("done\n");
				} else {

				}
			}
			in.close();
			out.close();
			socket.close();
			numConnectedClients--;
			System.out.println("client disconnected");
			System.out.println(numConnectedClients + " concurrent connection(s)\n");
		} catch (SSLPeerUnverifiedException e) {

		} catch (IOException e) {
			System.out.println("Client died: " + e.getMessage());
			e.printStackTrace();
			return;
		} catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Response getData(User user, Command cmd) {
		boolean allowed = user.checkCommandPermission(cmd);
		if (!allowed) {
			return new AccessDeniedResponse();
		}
		return new JournalResponse(null);
	}

	private void newListener() {
		(new Thread(this)).start();
	} // calls run()

	public static void main(String args[]) {
		System.out.println("\nServer Started\n");
		Logger.init();
		Logger.log("Server started");
		int port = -1;
		if (args.length >= 1) {
			port = Integer.parseInt(args[0]);
		}
		String type = "TLS";
		try {
			ServerSocketFactory ssf = getServerSocketFactory(type);
			ServerSocket ss = ssf.createServerSocket(port);
			((SSLServerSocket) ss).setNeedClientAuth(true); // enables client
															// authentication
			new Server(ss);
		} catch (IOException e) {
			System.out.println("Unable to start Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static ServerSocketFactory getServerSocketFactory(String type) {
		if (type.equals("TLS")) {
			SSLServerSocketFactory ssf = null;
			try { // set up key manager to perform server authentication
				SSLContext ctx = SSLContext.getInstance("TLS");
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
				KeyStore ks = KeyStore.getInstance("JKS");
				KeyStore ts = KeyStore.getInstance("JKS");
				char[] password = "password".toCharArray();

				ks.load(new FileInputStream(certPath + "server_keystore"), password); // keystore
																						// password
																						// (storepass)
				ts.load(new FileInputStream(certPath + "server_truststore"), password); // truststore
																						// password
																						// (storepass)
				kmf.init(ks, password); // certificate password (keypass)
				tmf.init(ts); // possible to use keystore as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				ssf = ctx.getServerSocketFactory();
				return ssf;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return ServerSocketFactory.getDefault();
		}
		return null;
	}
}
