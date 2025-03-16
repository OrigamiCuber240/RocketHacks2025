package client;

import javax.swing.*;

public class EmployeeGUI {
	
	JFrame frame;
	JButton accept;
	JButton decline;
	
	public void createEmplGUI(String description) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,190);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextArea descriptionBox = new JTextArea(description);
		descriptionBox.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(descriptionBox);
		panel.add(scrollPane);
		scrollPane.setBounds(15, 20, 355, 70);
		
		accept = new JButton("Accept");
		panel.add(accept);
		accept.setBounds(15, 110, 100, 20);
		
		decline = new JButton("Decline");
		panel.add(decline);
		decline.setBounds(270, 110, 100, 20);
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
}
