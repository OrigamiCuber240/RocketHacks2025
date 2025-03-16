package server;

import java.util.Set;
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
	private final Socket socket;

	private DataInputStream in;
	private DataOutputStream out;

	public Boolean isEmployee;
	public int id;
	public Server parent;

	public ClientHandler(Server parent, Socket socket, int id) {
		this.socket = socket; //set socket
		this.id = id;
		this.parent = parent;
	}

	public void run() {
		try {
			// Takes input from the client socket
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			// Outputs to client socket
			out = new DataOutputStream( new BufferedOutputStream(socket.getOutputStream()));

			String message = "";

			// Reads message from client until "Over" is sent
			while (!message.equals("over")) {
				try {
					message = in.readUTF();
					System.out.println(message);

					parseInput(message);
				}
				catch(IOException i) {
					// print diagnostic message
					System.out.println("Error: Closing connection");
					System.out.println(i);

					closeConnection();

					return;
				}
			}
			System.out.println("Closing connection");

			closeConnection();

			return;
		}
		catch (IOException i) {
			System.out.println(i);

			return;
		}
	}

	void initialize(String first, String sir, int role) {
		Set<Integer> keyset = parent.employeeHandlers.keySet();
		Integer[] keys = keyset.toArray(new Integer[keyset.size()]);
		for (int i = 0; i < keys.length; ++i) {
		}
	}

	void writeOut(String output) {
		try {
			out.writeUTF(output);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	Boolean send_request(String first, String sir) {
		writeOut("request|" + first + "|" + sir);

		Boolean accepted;

		try {
			accepted = in.readBoolean();
		}
		catch (IOException e) {
			e.printStackTrace();
			accepted = false;
		}

		return accepted;
	}

	void startLogin(int id) {
		
	}

	void parseInput(String message) {
		String[] args = message.split("[|]");
		System.out.println("Input: " + message);

		for (int i = 0; i < args.length; ++i) {
			System.out.println(i + ": " + args[i]);
		}

		switch (args[0]) {
			//case "accept":
			//	accept(Boolean.parseBoolean(args[1]));
			//	break;
			case "login":
				startLogin(Integer.parseInt(args[1]));
				break;
			case "initialize":
				initialize(args[1], args[2], Integer.parseInt(args[3]));
				break;
			default:
				System.out.println("command not found" + args[0]);
				break;
		}
	}

	void closeConnection() {
		try {
			socket.close();
			in.close();
		}
		catch (IOException i) {
			System.out.println(i);
			return;
		}
	}
}
