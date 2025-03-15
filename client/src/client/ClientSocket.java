package client;

import java.io.*;
import java.net.*;

public class ClientSocket {

	private Socket s = null;
	private DataOutputStream out = null;
	
	public ClientSocket() {
		System.out.println("start");
		try {
			s = new Socket("35.166.150.249", 5000);
			System.out.println("after socket");
			out = new DataOutputStream(s.getOutputStream());
			System.out.println("after dout");
		}
		catch (UnknownHostException u) {
			System.out.println("Unknown Host Exception");
			return;
		}
		catch (IOException i) {
			System.out.println(i.getMessage());
			System.out.println(i.getCause());
			return;
		}
		System.out.println("After");
		ClientGUI client = new ClientGUI();
		client.createGUI();
		
		try {
			out.writeUTF(client.worker);
			out.writeUTF(client.description);
			out.writeUTF(client.location);
		}
		catch (IOException i) {
		}
		
		try {
			out.close();
			s.close();
		}
		catch (IOException i) {
		}
		
	}
	
	public static void main(String[] args) {
		ClientSocket c = new ClientSocket();
	}
}
