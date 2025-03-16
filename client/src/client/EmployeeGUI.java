package client;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class EmployeeGUI {
	
	JFrame frame;
	JTextField idBox;
	JTextField pwBox;
	JTextField firstNameBox;
	JTextField lastNameBox;
	JComboBox<String> jobsBox;
	JButton logIn;
	JButton createAccount;
	JButton enterID;
	JButton enterPW;
	JButton enterAcc;
	JButton accept;
	JButton decline;
	
	public void initGUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,190);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		logIn = new JButton("Log In");
		panel.add(logIn);
		logIn.setBounds(25, 60, 150, 25);
		
		createAccount = new JButton("Create an Account");
		panel.add(createAccount);
		createAccount.setBounds(210, 60, 150, 25);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public void idGUI(String str) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,190);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextField idPrompt = new JTextField("Enter ID:");
		panel.add(idPrompt);
		idPrompt.setEditable(false);
		idPrompt.setBounds(20, 60, 50, 15);
		idPrompt.setBackground(UIManager.getColor("panel.background"));
		idPrompt.setBorder(BorderFactory.createEmptyBorder());
		
		idBox = new JTextField();
		panel.add(idBox);
		idBox.setBounds(80, 60, 100, 20);
		
		JTextField displayError = new JTextField(str);
		panel.add(displayError);
		displayError.setEditable(false);
		displayError.setBounds(20, 85, 300, 20);
		displayError.setBackground(UIManager.getColor("panel.background"));
		displayError.setBorder(BorderFactory.createEmptyBorder());
		
		enterID = new JButton("Enter");
		panel.add(enterID);
		enterID.setBounds(260, 110, 100, 20);
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public void pwGUI(String str) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,190);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextField pwPrompt = new JTextField("Enter Password:");
		panel.add(pwPrompt);
		pwPrompt.setEditable(false);
		pwPrompt.setBounds(20, 60, 90, 15);
		pwPrompt.setBackground(UIManager.getColor("panel.background"));
		pwPrompt.setBorder(BorderFactory.createEmptyBorder());
		
		JTextField displayError = new JTextField(str);
		panel.add(displayError);
		displayError.setEditable(false);
		displayError.setBounds(20, 85, 300, 20);
		displayError.setBackground(UIManager.getColor("panel.background"));
		displayError.setBorder(BorderFactory.createEmptyBorder());
		
		pwBox = new JTextField();
		panel.add(pwBox);
		pwBox.setBounds(115, 60, 100, 20);
		
		enterPW = new JButton("Enter");
		panel.add(enterPW);
		enterPW.setBounds(260, 110, 100, 20);
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public void createAccGUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,190);
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
		
		JTextField pwPrompt = new JTextField("Enter Password:");
		panel.add(pwPrompt);
		pwPrompt.setEditable(false);
		pwPrompt.setBounds(15, 65, 90, 15);
		pwPrompt.setBackground(UIManager.getColor("panel.background"));
		pwPrompt.setBorder(BorderFactory.createEmptyBorder());
		
		pwBox = new JTextField();
		panel.add(pwBox);
		pwBox.setBounds(110, 65, 120, 20);
		
		// Worker dropdown menu
		String jobs[] = {"Select job title...", "Doctor", "Nurse", "Janitor", "Floormanager", "Emergency Services"};
		jobsBox = new JComboBox<>(jobs);
		panel.add(jobsBox);
		jobsBox.setBounds(15, 93, 150, 25);
		
		enterAcc = new JButton("Enter");
		panel.add(enterAcc);
		enterAcc.setBounds(260, 110, 100, 20);
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public void createEmplGUI(String description) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,190);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextField descriptionBox = new JTextField(description);
		panel.add(descriptionBox);
		descriptionBox.setEditable(false);
		descriptionBox.setBackground(UIManager.getColor("panel.background"));
		descriptionBox.setBorder(BorderFactory.createEmptyBorder());
		descriptionBox.setBounds(25, 43, 355, 20);
		
		accept = new JButton("Accept");
		panel.add(accept);
		accept.setBounds(25, 110, 100, 20);
		
		decline = new JButton("Decline");
		panel.add(decline);
		decline.setBounds(260, 110, 100, 20);
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	
}
