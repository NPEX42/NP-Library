import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import np.core.IO;
import np.core.async.ASIO;

public class Server {
	static Socket client;
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(5000);
		System.out.println("Server Started On Port 5000");
		client = server.accept();
		System.out.println("Connected To Client On Port "+client.getLocalPort());
		
		while(client.isConnected()) {
			String msg = IO.ReadStringFromSocket(client);
			if(msg.equalsIgnoreCase(".exit")) {
				client.close();
				break;
			}
			if(!IO.WriteStringToSocket(client, msg)) {
				client.close();
				break;
			}
		}
		
		System.out.println("Client Disconnected, Exiting...");
	} 
	
}
