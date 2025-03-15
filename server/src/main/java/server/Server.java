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
				System.out.println("awaiting client");

				Socket socket = serverSocket.accept();

				System.out.println("New client connected" + socket.getInetAddress().getHostAddress());

				// creates a new thread for client
				ClientHandler clientSocket = new ClientHandler(socket);

				new Thread(clientSocket).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		finally {
		}
	}
}

