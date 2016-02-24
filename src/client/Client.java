package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.X509Certificate;

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

		try { /* set up a key manager for client authentication */
			SSLSocketFactory factory = null;
			try {
				char[] password = "password".toCharArray();
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
					System.out.print("sending '" + cmd.toString() + "' to server...");
					out.println(cmd.toString());
					out.flush();
					System.out.println("done");

					System.out.println("received '" + in.readLine() + "' from server\n");
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
