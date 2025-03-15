package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI {
	
	String worker;
	String description;
	String location;

	public void createGUI() {
		// GUI window
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,280);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		// Worker dropdown menu
		String workers[] = {"Select a worker...", "Doctor", "Nurse", "Janitor"};
		JComboBox workerBox = new JComboBox(workers);
		panel.add(workerBox);
		workerBox.setBounds(15, 10, 150, 25);
		
		// Description textbox
		JTextField descriptionPrompt = new JTextField("Please enter reason for request");
		panel.add(descriptionPrompt);
		descriptionPrompt.setEditable(false);
		descriptionPrompt.setBackground(UIManager.getColor("panel.background"));
		descriptionPrompt.setBorder(BorderFactory.createEmptyBorder());
		descriptionPrompt.setBounds(15, 50, 300, 15);
		
		JTextArea descriptionBox = new JTextArea();
		descriptionBox.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(descriptionBox);
		panel.add(scrollPane);
		scrollPane.setBounds(15, 70, 300, 70);
		
		// Location textbox
		JTextField locationPrompt = new JTextField("Please enter location");
		panel.add(locationPrompt);
		locationPrompt.setEditable(false);
		locationPrompt.setBackground(UIManager.getColor("panel.background"));
		locationPrompt.setBorder(BorderFactory.createEmptyBorder());
		locationPrompt.setBounds(15, 150, 300, 15);
		
		JTextField locationBox = new JTextField();
		panel.add(locationBox);
		locationBox.setBounds(15, 170, 300, 20);
		
		// Submit button
		JButton submit = new JButton("Submit");
		panel.add(submit);
		submit.setBounds(15, 200, 100, 20);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker = (String) workerBox.getSelectedItem();
				description = descriptionBox.getText();
				location = locationBox.getText();
				frame.setVisible(false);
			}
		});
		
		frame.add(panel);
		frame.setVisible(true);
		
		
	}
}
