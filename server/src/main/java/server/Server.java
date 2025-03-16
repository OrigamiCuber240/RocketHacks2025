package server;

import java.util.HashMap;
import java.util.Set;
import java.net.*;
import java.sql.SQLException;
import java.io.*;

public class Server {
	//private Socket socket;
	private ServerSocket serverSocket;

	public HashMap<Integer, Integer> employeeHandlers = new HashMap<Integer, Integer>();
	public HashMap<Integer, Integer> patientHandlers = new HashMap<Integer, Integer>();
	public HashMap<Integer, ClientHandler> undefinedHandlers = new HashMap<Integer, ClientHandler>();

	public middleLayer mid;
	private int port;

	public Server(int port) {
		this.port = port;

		// initialize middle layer
		try {
			System.out.println("Initializing database connection...");
			mid = new middleLayer();
			System.out.println("Database connection initialized successfully");
		}
		catch (ClassNotFoundException e) {
			System.out.println("Error: JDBC Driver class not found");
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("Error: SQL Exception when connecting to database");
			System.out.println("Make sure MySQL is running and the database 'dyschu' exists");
			e.printStackTrace();
		}

		startServer();
	}
	
	private void startServer() {
		try {
			// Check if port is already in use
			try {
				serverSocket = new ServerSocket(port);
				serverSocket.setReuseAddress(true); //Allow multiple connections at same port
			} catch (BindException e) {
				System.out.println("Port " + port + " is already in use. Trying port " + (port + 1));
				port++;
				serverSocket = new ServerSocket(port);
				serverSocket.setReuseAddress(true);
			}
			
			System.out.println("Server started on port " + port);
			System.out.println("Awaiting client connections...");

			while (true) {
				System.out.println("awaiting client");

				Socket socket = serverSocket.accept();

				addHandler(socket);
			}
		}
		catch (IOException e) {
			System.out.println("Server error: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	int getLowestUnassignedIndex() {
		int i = 0;
		while (undefinedHandlers.containsKey(i)) {
			++i;
		}

		return i;
	}

	void printHash(HashMap<Integer, ClientHandler> hash) {
		Set<Integer> keyset = hash.keySet();
		Integer[] keys = keyset.toArray(new Integer[keyset.size()]);

		System.out.print("set: ");
		for (int i = 0; i < keys.length; ++i) {
			System.out.print(keys[i] + ":" + hash.get(keys[i]) + ", ");
		}

		System.out.println();
	}

	void addHandler(Socket socket) {
		System.out.println("New client connected" + socket.getInetAddress().getHostAddress());

		// creates a new thread for client
		int currentId = getLowestUnassignedIndex();
		ClientHandler clientSocket = new ClientHandler(this, socket, currentId);

		undefinedHandlers.put(currentId, clientSocket);

		new Thread(undefinedHandlers.get(currentId)).start();

		printHash(undefinedHandlers);
	}
		
}

