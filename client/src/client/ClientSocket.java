package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ClientSocket {

	private Socket s = null;
	private static DataOutputStream out = null;
	
	public ClientSocket() {
		
		try {
			s = new Socket("35.166.150.249", 5000);
			out = new DataOutputStream(s.getOutputStream());
		}
		catch (UnknownHostException u) {
			System.out.println(u.getMessage());
			return;
		}
		catch (IOException i) {
			System.out.println(i.getMessage());
			System.out.println(i.getCause());
			return;
		}
		
		ClientGUI client = new ClientGUI();
		client.createGUI();
		
		client.submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.worker = (String) client.workerBox.getSelectedItem();
				client.description = client.descriptionBox.getText();
				client.location = client.locationBox.getText();
				client.frame.setVisible(false);
		
				try {
					out.writeUTF(client.worker);
					out.writeUTF(client.description);
					out.writeUTF(client.location);
					out.flush();
				}
				catch (IOException i) {
					System.out.println(i.getMessage());
				}
				
				try {
					out.close();
					s.close();
				}
				catch (IOException i) {
					System.out.println(i.getMessage());
				}
			}
		});
		
		
	}
	
	public static void main(String[] args) {
		ClientSocket c = new ClientSocket();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					out.writeUTF("over");
				}
				catch (IOException i) {
				}
			}
		});
	}
}
