package server;

import java.util.HashMap;
import java.util.Set;
import java.net.*;
import java.io.*;

public class Server {
	//private Socket socket;
	private ServerSocket serverSocket;

	public HashMap<Integer, Integer> employeeHandlers = new HashMap<Integer, Integer>();
	public HashMap<Integer, Integer> patientHandlers = new HashMap<Integer, Integer>();
	public HashMap<Integer, ClientHandler> undefinedHandlers = new HashMap<Integer, ClientHandler>(); // when it isn't yet known whether they are employee or patient

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port); //Connect at port
			serverSocket.setReuseAddress(true); //Allow multiple connections at same port

			while (true) {
				System.out.println("awaiting client");

				Socket socket = serverSocket.accept();

				addHandler(socket);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		finally {
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

