package client;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.X509Certificate;

import databas.DBHandler;
import dnl.utils.text.table.TextTable;
import model.Command;
import model.CommandFactory;
import model.InvalidCommandException;

/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */
public class Client {
	private static String certPath = "certificates/User/";
	private static String user = "no_user";

	public static void main(String[] args) throws Exception {
		String host = null;
		int port = -1;
		for (int i = 0; i < args.length; i++) {
			System.out.println("args[" + i + "] = " + args[i]);
		}
		if (args.length < 2) {
			System.out.println("USAGE: java client host port username");
			System.exit(-1);
		}
		try { /* get input parameters */
			host = args[0];
			port = Integer.parseInt(args[1]);
		} catch (IllegalArgumentException e) {
			System.out.println("USAGE: java client host port");
			System.exit(-1);
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter username.");
		user = scanner.nextLine();
		System.out.println("Please enter password.");
		Console console = System.console();
		String secretpassword = "password";
		if(console!=null)
			secretpassword = new String(System.console().readPassword());
		else{
			secretpassword= scanner.nextLine();
		}

		try { /* set up a key manager for client authentication */
			SSLSocketFactory factory = null;
			try {
				char[] password = secretpassword.toCharArray();
				KeyStore ks = KeyStore.getInstance("JKS");
				KeyStore ts = KeyStore.getInstance("JKS");
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
				SSLContext ctx = SSLContext.getInstance("TLS");
				ks.load(new FileInputStream(certPath + user + "_keystore"), password); // keystore
																						// password
																						// (storepass)
				ts.load(new FileInputStream(certPath + user + "_truststore"), password); // truststore
																							// password
																							// (storepass);
				kmf.init(ks, password); // user password (keypass)
				tmf.init(ts); // keystore can be used as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				factory = ctx.getSocketFactory();
			} catch (Exception e) {
				scanner.close();
				throw new IOException(e.getMessage());
			}
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
			System.out.println("\nsocket before handshake:\n" + socket + "\n");

			/*
			 * send http request
			 *
			 * See SSLSocketClient.java for more information about why there is
			 * a forced handshake here when using PrintWriters.
			 */
			socket.startHandshake();

			SSLSession session = socket.getSession();
			X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
			String subject = cert.getSubjectDN().getName();
			System.out.println(
					"certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
			System.out.println("socket after handshake:\n" + socket + "\n");
			System.out.println("secure connection established\n\n");

			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String msg;
			for (;;) {
				System.out.print(">");
				msg = read.readLine();
				if (msg.equalsIgnoreCase("quit")) {
					break;
				}
				Command cmd = null;
				try {
					cmd = CommandFactory.createCommand(msg);
				} catch (InvalidCommandException e) {
					System.out.println(e.getMessage());
				}
				if (cmd != null) {
					//System.out.print("sending '" + cmd.toString() + "' to server...");
					out.println(cmd.toString());
					out.flush();
					System.out.println("done");
					System.out.println("Server response:");
					if(cmd.getCommand().equals(Command.Commands.READ)) {
						String[] colNames = DBHandler.TABLE_HEADER.split(";");
						List<String[]> data = new ArrayList<>();
						do {
							data.add(in.readLine().split(";"));
						} while (in.ready());
						String[][] dataTable = new String[colNames.length][data.size()];
						for (int i = 0; i < data.size(); i++) {
							dataTable[i] = data.get(i);
						}
						TextTable tt = new TextTable(colNames, dataTable);
						tt.printTable();
					}else{
						do {
							System.out.println(in.readLine());
						} while (in.ready());
					}

				}
				
			}
			in.close();
			out.close();
			read.close();
			socket.close();
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
