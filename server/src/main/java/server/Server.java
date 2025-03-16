package server;

import java.util.HashMap;
import java.util.Set;
import java.net.*;
import java.sql.SQLException;
import java.io.*;

public class Server {
	//private Socket socket;
	private ServerSocket serverSocket;
	private boolean running = true;

	public HashMap<Integer, Integer> employeeHandlers = new HashMap<Integer, Integer>();
	public HashMap<Integer, Integer> patientHandlers = new HashMap<Integer, Integer>();
	public HashMap<Integer, ClientHandler> undefinedHandlers = new HashMap<Integer, ClientHandler>();

	public middleLayer mid;
	private int port;
	private final int MAX_PORT_ATTEMPTS = 5;

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
			System.exit(1);
		}
		catch (SQLException e) {
			System.out.println("Error: SQL Exception when connecting to database");
			System.out.println("Make sure MySQL is running and the database 'dyschu' exists");
			e.printStackTrace();
			System.exit(1);
		}

		startServer();
	}
	
	private void startServer() {
		int attempts = 0;
		
		while (attempts < MAX_PORT_ATTEMPTS) {
			try {
				// Check if port is already in use
				try {
					serverSocket = new ServerSocket(port);
					serverSocket.setReuseAddress(true); //Allow multiple connections at same port
					break; // Successfully bound to port, exit the loop
				} catch (BindException e) {
					System.out.println("Port " + port + " is already in use. Trying port " + (port + 1));
					port++;
					attempts++;
					if (attempts >= MAX_PORT_ATTEMPTS) {
						throw new IOException("Failed to bind to a port after " + MAX_PORT_ATTEMPTS + " attempts");
					}
				}
			} catch (IOException e) {
				System.out.println("Server error: " + e.getMessage());
				e.printStackTrace();
				closeServer();
				return;
			}
		}
		
		System.out.println("Server started on port " + port);
		System.out.println("Awaiting client connections...");
		
		// Start a shutdown hook to properly close resources when the server is terminated
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Server shutting down...");
			closeServer();
		}));

		while (running) {
			try {
				System.out.println("Awaiting client connection...");
				Socket socket = serverSocket.accept();
				addHandler(socket);
			} catch (IOException e) {
				if (running) {
					System.out.println("Error accepting client connection: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	public void closeServer() {
		running = false;
		
		// Close all client handlers
		for (ClientHandler handler : undefinedHandlers.values()) {
			try {
				handler.stop();
			} catch (Exception e) {
				System.out.println("Error closing client handler: " + e.getMessage());
			}
		}
		
		// Close the server socket
		if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
				System.out.println("Server socket closed");
			} catch (IOException e) {
				System.out.println("Error closing server socket: " + e.getMessage());
				e.printStackTrace();
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

		System.out.print("Connected clients: ");
		for (int i = 0; i < keys.length; ++i) {
			System.out.print(keys[i] + ":" + hash.get(keys[i]) + ", ");
		}

		System.out.println();
	}

	void addHandler(Socket socket) {
		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());

		// creates a new thread for client
		int currentId = getLowestUnassignedIndex();
		ClientHandler clientSocket = new ClientHandler(this, socket, currentId);

		undefinedHandlers.put(currentId, clientSocket);

		new Thread(undefinedHandlers.get(currentId)).start();

		printHash(undefinedHandlers);
	}
}

