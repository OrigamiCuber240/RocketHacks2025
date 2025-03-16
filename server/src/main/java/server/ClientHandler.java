package server;

import java.util.Set;
import java.io.*;
import java.net.*;
import java.sql.SQLException;


public class ClientHandler implements Runnable {
	static enum Jobs {
		DOCTOR,
		NURSE,
		JANITOR,
		FLOOR_MANAGER,
		EMERGENCY
	}
	private final Socket socket;

	private DataInputStream in;
	private DataOutputStream out;
	private boolean running = true;

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
			out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

			String message = "";

			// Reads message from client until "Over" is sent
			while (running && !message.equals("over")) {
				try {
					message = in.readUTF();
					System.out.println("Received message from client " + id + ": " + message);
					parseInput(message);
				} catch (EOFException e) {
					System.out.println("Client " + id + " disconnected");
					break;
				} catch (SocketException e) {
					System.out.println("Connection with client " + id + " lost: " + e.getMessage());
					break;
				} catch (IOException e) {
					System.out.println("Error reading from client " + id + ": " + e.getMessage());
					break;
				}
			}
			System.out.println("Closing connection with client " + id);
		} catch (IOException e) {
			System.out.println("Error setting up streams for client " + id + ": " + e.getMessage());
		} finally {
			closeConnection();
		}
	}
	
	public void stop() {
		running = false;
		closeConnection();
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
		try {
			if (isEmployee == null) {
				if (parent.undefinedHandlers.containsKey(id)) {
					parent.undefinedHandlers.remove(id);
					System.out.println("Removed undefined client " + id + " from handlers");
				}
			} 
			else if (isEmployee == true) {
				if (parent.employeeHandlers.containsKey(id)) {
					int handlerId = parent.employeeHandlers.get(id);
					if (parent.undefinedHandlers.containsKey(handlerId)) {
						parent.undefinedHandlers.remove(handlerId);
					}
					parent.employeeHandlers.remove(id);
					System.out.println("Removed employee client " + id + " from handlers");
				}
			}
			else {
				if (parent.patientHandlers.containsKey(id)) {
					int handlerId = parent.patientHandlers.get(id);
					if (parent.undefinedHandlers.containsKey(handlerId)) {
						parent.undefinedHandlers.remove(handlerId);
					}
					parent.patientHandlers.remove(id);
					System.out.println("Removed patient client " + id + " from handlers");
				}
			}
			
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					System.out.println("Error closing input stream: " + e.getMessage());
				}
			}
			
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					System.out.println("Error closing output stream: " + e.getMessage());
				}
			}
			
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
					System.out.println("Socket for client " + id + " closed");
				} catch (IOException e) {
					System.out.println("Error closing socket: " + e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("Error during connection cleanup: " + e.getMessage());
		}
	}
}
