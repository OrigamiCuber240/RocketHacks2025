package server;

public class App {
	public static Server server;

	public static void main(String[] args) {
		server = new Server(5000);
	}
}
