package np.core.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import np.core.IO;

public class ASIO {
	public static Thread LoadString(String path, Callback<String> callback) {
		Thread t = new Thread( () -> {
			LoadStringThread(path, callback);
		});
		t.setName("ASIO-Worker #"+(int)(Math.random() * 1000));
		t.start();
		return t;
	}
	
	private static void LoadStringThread(String path, Callback<String> callback) {
		callback.Invoke(IO.ReadString(path));
	}
	
	private static void SendStringOverSocketThread(Socket sock, String msg) {
		try {
			PrintWriter printer = new PrintWriter(sock.getOutputStream());
			printer.print(msg);
		} catch (IOException e) {
			System.err.println("======"+Thread.currentThread().getName()+"======");
			e.printStackTrace();
		}
	}
	
	private static void ReadStringFromSocketThread(Socket sock, Callback<String> callback) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			callback.Invoke(reader.readLine());
		} catch (IOException e) {
			callback.Invoke("");
		}
	}
	
	public static Thread SendStringOverSocket(Socket sock, String msg) {
		Thread t = CreateThread(()->{
			SendStringOverSocketThread(sock, msg);
		});
		t.start();
		return t;
	}
	
	
	private static Thread CreateThread(Runnable runnable) {
		Thread t = new Thread(runnable);
		t.setName("ASIO-Worker #"+(int)(Math.random() * 1000));
		return t;
	}

	public static Thread ReadStringFromSocket(Socket socket, Callback<String> cb) {
		Thread t = CreateThread(()->{
			ReadStringFromSocketThread(socket, cb);
		});
		t.start();
		return t;
	}

}
