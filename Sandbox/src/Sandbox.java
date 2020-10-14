import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import np.core.IO;
import np.core.async.ASIO;

public class Sandbox {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
	}
	
	public static void CB(String source) {
		System.out.println("Hello From "+Thread.currentThread().getName());
		System.out.println(source);
	}

}
