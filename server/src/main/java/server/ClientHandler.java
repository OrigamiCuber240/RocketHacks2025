package server;

import java.util.Set;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

enum Jobs {
	DOCTOR,
	NURSE,
	JANITOR,
	FOORMANEGER,
	EMERGENCY
}

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

				System.out.println(message);
				parseInput(message);
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

	Boolean isPasswordCorrect(int id, String passwd) {
		String pass = parent.mid.readPassword(String.format("%d", id));
		return (passwd == pass);
	}

	Boolean isEmployeeInDatabase(int id) {
		String raw = parent.mid.read(String.format("%d", id), "employee");
		String[] arr = raw.split("[|]");

		if (arr[3].length() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	int destringTitle(String str) {
		int out;

		switch (str) {
			case "Doctor":
			out = 0;
			break;
			case "Nurse":
			out = 1;
			break;
			case "Janitor":
			out = 2;
			break;
			case "Floor Manager":
			out = 3;
			break;
			case "Emergency Services":
			out = 4;
			break;
			default:
			out = 5;
			break;
		}

		return out;
	}

	void initialize(String first, String sir, int role) {
		Set<Integer> keyset = parent.employeeHandlers.keySet();
		Integer[] keys = keyset.toArray(new Integer[keyset.size()]);
		for (int i = 0; i < keys.length; ++i) {
			var raw = parent.mid.read(String.format("%d", keys[i]), "employee");
			String[] params = raw.split("|");
			if (destringTitle(params[3]) == role) {
				parent.undefinedHandlers.get(parent.employeeHandlers.get(keys[i])).sendRequest(first, sir);
			}

		}
	}

	String readString() {
		String message = "";
		try {
			message = in.readUTF();
			System.out.println(message);
			return message;
		}
		catch(IOException i) {
			// print diagnostic message
			System.out.println("Error: Closing connection");
			System.out.println(i);

			closeConnection();

			return null;
		}
	}

	void writeOut(String output) {
		try {
			out.writeUTF(output);
		}
		catch (IOException e) {
			e.printStackTrace();
			closeConnection();
		}
	}

	void writeBooleanOut(Boolean output) {
		try {
			out.writeBoolean(output);
		}
		catch (IOException e) {
			e.printStackTrace();
			closeConnection();
		}
	}

	Boolean sendRequest(String first, String sir) {
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
		writeBooleanOut(isEmployeeInDatabase(id));
		String passwd = readString();
		isEmployee = isPasswordCorrect(id, passwd);
		writeBooleanOut(isEmployee);
		if (isEmployee) {
			parent.employeeHandlers.put(id, this.id);
			this.id = id;
		}
	}

	void signup(String first, String sir, int job, String password) {
		String[] args = {first, sir, String.format("%d", job), password};
		try {
			parent.mid.write(args, "employee");
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
			case "signup":
			signup(args[1], args[2], Integer.parseInt(args[3]), args[4]);
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
		if (isEmployee == null) {
			parent.undefinedHandlers.remove(id);
		} 
		else if (isEmployee == true) {
			parent.undefinedHandlers.remove(parent.employeeHandlers.get(id));
			parent.employeeHandlers.remove(id);
		}
		else {
			parent.undefinedHandlers.remove(parent.patientHandlers.get(id));
			parent.patientHandlers.remove(id);
		}

		try {
			socket.close();
			in.close();
			out.close();
		}
		catch (IOException i) {
			System.out.println(i);
			return;
		}
	}
}
