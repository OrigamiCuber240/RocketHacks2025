package server;

import java.net.*;
import java.io.*;

public class Server {
	//private Socket socket;
	private ServerSocket serverSocket;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port); //Connect at port
			serverSocket.setReuseAddress(true); //Allow multiple connections at same port

			while (true) {
				Socket socket = serverSocket.accept();

				System.out.println("New client connected" + socket.getInetAddress().getHostAddress());

				// creates a new thread for client
				ClientHandler clientSocket = new ClientHandler(socket);

				new Thread(clientSocket).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		}
	}
}

class ClientHandler implements Runnable {
	private final Socket socket;

	private DataInputStream in;
	private DataOutputStream out;

	public ClientHandler(Socket socket) {
		this.socket = socket; //set socket
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
					System.out.println(i);
				}
			}
			System.out.println("Closing connection");

			// Close connection
			socket.close();
			in.close();
		}
		catch (IOException i) {
			System.out.println(i);
		}
	}

	void sendEmployee(String workerType, int roomNum) {
		System.out.println("sending " + workerType + "to room #" + roomNum);

		// Send a message to employee client to show popup
		// log that for use on confirmation
	}

	void addEvent(String name) {
		System.out.println("event " + name + "added");
	}

	void parseInput(String message) {
		String[] args = message.split(" ");
		switch (args[0]) {
			case "send-employee":
				sendEmployee(args[0], Integer.parseInt(args[1]));
				break;
			case "add-event":
				addEvent(args[0]);
				break;
			default:
				System.out.println("command not found" + args[0]);
				break;
		}
	}
}
