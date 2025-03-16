package server;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
	private final Socket socket;

	private DataInputStream in;
	private DataOutputStream out;

	public Boolean isEmployee;
	public int id;

	public ClientHandler(Socket socket, int id) {
		this.socket = socket; //set socket
		this.id = id;
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

	void initialize(Boolean isEmployee, int id) {
		this.isEmployee = isEmployee;

		if (isEmployee == true) { 
			App.server.employeeHandlers.put(id, this.id);
		}
		else {
			App.server.patientHandlers.put(id, this.id);
		}

		this.id = id;
	}

	void sendEmployee(String workerType, int roomNum) {
		System.out.println("sending " + workerType + "to room #" + roomNum);

		// Send a message to employee client to show popup
		// log that for use on confirmation
		//App.server

	}

	void confirm(Boolean b) {
		System.out.println("Confirms " + b);
	}

	void addEvent(String name) {
		System.out.println("event " + name + "added");
	}

	void parseInput(String message) {
		String[] args = message.split("|");
		System.out.println("Input: " + message);

		for (int i = 0; i < args.length; ++i) {
			System.out.println(i + ": " + args[i]);
		}

		switch (args[0]) {
			case "send-employee":
				sendEmployee(args[1], Integer.parseInt(args[2]));
				break;
			case "confirm":
				sendEmployee(args[1], Integer.parseInt(args[2]));
				break;
			case "add-event":
				addEvent(args[1]);
				break;
			case "initialize":
				initialize(Boolean.parseBoolean(args[1]), Integer.parseInt(args[2]));
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
