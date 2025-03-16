package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmployeeSocket {
	
	private Socket s = null;
	private static DataInputStream in = null;
	private static DataOutputStream out = null;
	
	public EmployeeSocket() {
		
		try {
			s = new Socket("35.166.150.249", 5000);
			in = new DataInputStream(s.getInputStream());
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
		
		IdGUI idgui = new IdGUI();
		idgui.idGUI();
		idgui.enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idgui.frameID.setVisible(false);
				try {
					out.writeUTF("initialize|true|" + idgui.idBox.getText());
					out.flush();
				}
				catch(IOException i) {
					System.out.println(i.getMessage());
				}
		
		
				String description = "";
				
				try {
					description = in.readUTF();
				}
				catch(IOException i) {
					System.out.println(i.getMessage());
				}
				
				EmployeeGUI gui = new EmployeeGUI();
				gui.createEmplGUI(description);
				
				gui.accept.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gui.frame.setVisible(false);
						try {
							out.writeUTF("confirm|true");
							out.flush();
						}
						catch(IOException i) {
							System.out.println(i.getMessage());
						}
						
						try {
							in.close();
							out.close();
							s.close();
						}
						catch(IOException i) {
							System.out.println(i.getMessage());
						}
					}
				});
				
				gui.decline.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gui.frame.setVisible(false);
						try {
							out.writeUTF("confirm|false");
							out.flush();
						}
						catch(IOException i) {
							System.out.println(i.getMessage());
						}
						
						try {
							in.close();
							out.close();
							s.close();
						}
						catch(IOException i) {
							System.out.println(i.getMessage());
						}
					}
				});
			}
				
		});
		
	}
		
}
