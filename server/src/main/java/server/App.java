package server;

public class App {
	private static Server server;

	public static void main(String[] args) {
		server = new Server(5000);
	}

	static Server getServer() {
		return server;
	}
}
