package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI {
	
	JFrame frame;
	JTextField firstNameBox;
	JTextField lastNameBox;
	JComboBox workerBox;
	JTextField descriptionBox;
	JButton submit;
	
	

	public void createGUI() {
		// GUI window
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextField firstNamePrompt = new JTextField("First name:");
		panel.add(firstNamePrompt);
		firstNamePrompt.setEditable(false);
		firstNamePrompt.setBounds(15, 13, 65, 20);
		firstNamePrompt.setBackground(UIManager.getColor("panel.background"));
		firstNamePrompt.setBorder(BorderFactory.createEmptyBorder());
		
		firstNameBox = new JTextField();
		panel.add(firstNameBox);
		firstNameBox.setBounds(80, 15, 150, 20);
		
		JTextField lastNamePrompt = new JTextField("Last name:");
		panel.add(lastNamePrompt);
		lastNamePrompt.setEditable(false);
		lastNamePrompt.setBounds(15, 38, 65, 20);
		lastNamePrompt.setBackground(UIManager.getColor("panel.background"));
		lastNamePrompt.setBorder(BorderFactory.createEmptyBorder());
		
		lastNameBox = new JTextField();
		panel.add(lastNameBox);
		lastNameBox.setBounds(80, 40, 150, 20);
		
		// Worker dropdown menu
		String workers[] = {"Select a worker...", "Doctor", "Nurse", "Janitor", "Floormanager", "Emergency Services"};
		workerBox = new JComboBox(workers);
		panel.add(workerBox);
		workerBox.setBounds(15, 70, 150, 25);
		
		// Description textbox
		JTextField descriptionPrompt = new JTextField("Please enter reason for request");
		panel.add(descriptionPrompt);
		descriptionPrompt.setEditable(false);
		descriptionPrompt.setBackground(UIManager.getColor("panel.background"));
		descriptionPrompt.setBorder(BorderFactory.createEmptyBorder());
		descriptionPrompt.setBounds(15, 105, 300, 15);
		
		JTextArea descriptionBox = new JTextArea();
		descriptionBox.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(descriptionBox);
		panel.add(scrollPane);
		scrollPane.setBounds(15, 125, 300, 70);
		
		// Submit button
		submit = new JButton("Submit");
		panel.add(submit);
		submit.setBounds(15, 200, 100, 20);
				
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
}
