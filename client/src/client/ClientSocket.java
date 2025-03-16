package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ClientSocket {

	private Socket s = null;
	public static DataOutputStream out = null;
	private final String SERVER_IP = "35.166.150.249";
	private final int SERVER_PORT = 5000;
	private final int MAX_RETRIES = 3;
	
	public ClientSocket() {
		int retries = 0;
		boolean connected = false;
		
		while (!connected && retries < MAX_RETRIES) {
			try {
				System.out.println("Attempting to connect to server at " + SERVER_IP + ":" + SERVER_PORT + " (Attempt " + (retries + 1) + ")");
				s = new Socket(SERVER_IP, SERVER_PORT);
				out = new DataOutputStream(s.getOutputStream());
				connected = true;
				System.out.println("Connected to server successfully");
			}
			catch (UnknownHostException u) {
				System.out.println("Error: Unknown host - " + u.getMessage());
				retries++;
				if (retries < MAX_RETRIES) {
					System.out.println("Retrying in 2 seconds...");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				}
			}
			catch (IOException i) {
				System.out.println("Error: Connection failed - " + i.getMessage());
				System.out.println("Cause: " + i.getCause());
				retries++;
				if (retries < MAX_RETRIES) {
					System.out.println("Retrying in 2 seconds...");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
		
		if (!connected) {
			System.out.println("Failed to connect to server after " + MAX_RETRIES + " attempts");
			return;
		}
		
		ClientGUI client = new ClientGUI();
		client.createGUI();
		
		client.submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = client.firstNameBox.getText();
				String lastName = client.lastNameBox.getText();
				int worker = client.workerBox.getSelectedIndex() - 1;
				String description = client.descriptionBox.getText();
				client.frame.setVisible(false);
		
				try {
					out.writeUTF("initialize|" + firstName + "|" + lastName + "|" + worker + "|" + description);
					// out.writeUTF(client.location);
					out.flush();
					System.out.println("Data sent to server successfully");
				}
				catch (IOException i) {
					System.out.println("Error sending data to server: " + i.getMessage());
				}
				
				try {
					out.close();
					s.close();
					System.out.println("Connection closed");
				}
				catch (IOException i) {
					System.out.println("Error closing connection: " + i.getMessage());
				}
			}
		});
	}
}
