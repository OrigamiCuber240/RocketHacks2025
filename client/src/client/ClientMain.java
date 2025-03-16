package client;

import java.io.IOException;

public class ClientMain {

	public static void main(String[] args) {
		ClientSocket c = new ClientSocket();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					ClientSocket.out.writeUTF("over");
				}
				catch (IOException i) {
				}
			}
		});
	}
}
