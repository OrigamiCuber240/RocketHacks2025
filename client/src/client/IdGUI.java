package client;

import javax.swing.*;

public class IdGUI {
	
	JFrame frameID;
	JTextField idBox;
	JButton enter;

	public void idGUI(){
		frameID = new JFrame();
		frameID.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameID.setSize(300,150);
		frameID.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextField idPrompt = new JTextField("Enter ID number:");
		panel.add(idPrompt);
		idPrompt.setEditable(false);
		idPrompt.setBounds(15, 30, 90, 15);
		idPrompt.setBackground(UIManager.getColor("panel.background"));
		idPrompt.setBorder(BorderFactory.createEmptyBorder());
		
		idBox = new JTextField();
		panel.add(idBox);
		idBox.setBounds(110, 30, 100, 20);
		
		enter = new JButton("Enter");
		panel.add(enter);
		enter.setBounds(15, 55, 80, 20);
		
		frameID.add(panel);
		frameID.setVisible(true);
	}
}
