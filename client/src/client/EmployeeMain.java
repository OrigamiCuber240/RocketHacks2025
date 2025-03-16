package client;

import java.io.IOException;

public class EmployeeMain {

	public static void main(String[] args) {
		EmployeeSocket e = new EmployeeSocket();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					EmployeeSocket.out.writeUTF("over");
				}
				catch (IOException i) {
				}
			}
		});

	}

}
