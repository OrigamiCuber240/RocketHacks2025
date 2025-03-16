package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmployeeSocket {
	
	private Socket s = null;
	private static DataInputStream in = null;
	public static DataOutputStream out = null;
	
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
		
		EmployeeGUI gui = new EmployeeGUI();
	// Log in or create account buttons
		gui.initGUI();
		gui.logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.frame.setVisible(false);
				/*try {
					out.writeUTF("login|" + gui.idBox.getText());
					out.flush();
				}
				catch(IOException i) {
					System.out.println(i.getMessage());
				}*/
		
	// ID
				gui.idGUI("");
				gui.enterID.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gui.frame.setVisible(false);
						try {
							out.writeUTF("login|" + gui.idBox.getText());
							out.flush();
						}
						catch(IOException i) {
							System.out.println(i.getMessage());
						}
						
						try {
							boolean valid = in.readBoolean();
							while(valid == false) {
								gui.idGUI("Wrong username");
								gui.enterID.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										gui.frame.setVisible(false);
										try {
											out.writeUTF("login|" + gui.idBox.getText());
											out.flush();
										}
										catch(IOException i) {
											System.out.println(i.getMessage());
										}
										
										try {
											boolean valid = in.readBoolean();
										}
										catch(IOException i) {
											System.out.println(i.getMessage());
										}
									}
								});
							}
						}
						catch(IOException i) {
							System.out.println(i.getMessage());
						}
						
				
						
		// Password
						gui.pwGUI("");
						gui.enterPW.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								gui.frame.setVisible(false);
								try {
									out.writeUTF("login|" + gui.pwBox.getText());
									out.flush();
								}
								catch(IOException i) {
									System.out.println(i.getMessage());
								}
								

								try {
									boolean valid = in.readBoolean();
									while(valid == false) {
										gui.idGUI("Wrong password");
										gui.enterID.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												gui.frame.setVisible(false);
												try {
													out.writeUTF("login|" + gui.idBox.getText());
													out.flush();
												}
												catch(IOException i) {
													System.out.println(i.getMessage());
												}
												
												try {
													boolean valid = in.readBoolean();
												}
												catch(IOException i) {
													System.out.println(i.getMessage());
												}
											}
										});
									}
								}
							
								catch(IOException i) {
									System.out.println(i.getMessage());
								}
									
								
								try {
									while(in.readUTF() == null) {	
									}
								}
								catch (IOException i) {
									System.out.println(i.getMessage());
								}
		// Accept request	
								String str = "";
								try {
									String input = in.readUTF();
									String[] args = input.split("[|]");
									str = args[0] + " " + args[1] + " " + args[2];
								}
								catch(IOException i) {
									System.out.println(i.getMessage());
								}
								
			
								gui.createEmplGUI(str);
								gui.accept.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										gui.frame.setVisible(false);
										try {
											out.writeBoolean(true);
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
											out.writeBoolean(false);
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
				});
			}
		});
		
		// Log in or create account buttons
				gui.createAccount.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gui.frame.setVisible(false);
						/*try {
							out.writeUTF("login|" + gui.idBox.getText());
							out.flush();
						}
						catch(IOException i) {
							System.out.println(i.getMessage());
						}*/
				
		// Create account
						gui.createAccGUI();
						gui.enterAcc.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								gui.frame.setVisible(false);
								try {
									String firstName = gui.firstNameBox.getText();
									String lastName = gui.lastNameBox.getText();
									String password = gui.pwBox.getText();
									int job = gui.jobsBox.getSelectedIndex() - 1;
									out.writeUTF("signup|" + firstName + "|" + lastName + "|" + job + "|" + password);
									out.flush();
								}
								catch(IOException i) {
									System.out.println(i.getMessage());
								}

								String str = "";
								
								try {
									String input = in.readUTF();
									String[] args = input.split("[|]");
									str = args[0] + " " + args[1] + " " + args[2];
								}
								catch(IOException i) {
									System.out.println(i.getMessage());
								}
								
								try {
									while(in.readUTF() == null) {	
									}
								}
								catch (IOException i) {
									System.out.println(i.getMessage());
								}
								
		// Accept request		
								gui.createEmplGUI(str);
								gui.accept.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										gui.frame.setVisible(false);
										try {
											out.writeBoolean(true);
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
											out.writeBoolean(false);
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
				});
	}
		
}
