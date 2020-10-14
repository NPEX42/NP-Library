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
	
	public static Thread SaveStrings(String path, String... data) {
		Thread t = CreateThread(() -> { SaveStringsThread(path, data); });
		t.start();
		return t;
	} 
	
	public static Thread LoadBytes(String path, Callback<Byte[]> cb) {
		Thread t = CreateThread(() -> { LoadBytesThread(path, cb); });
		t.start();
		return t;
	}
	
	private static void LoadBytesThread(String path, Callback<Byte[]> cb) {
		long tp1, tp2;
		System.out.println(Thread.currentThread().getName()+" Started Work...");
		tp1 = System.currentTimeMillis();
		Byte[] data = IO.LoadBytes(path);
		tp2 = System.currentTimeMillis();
		
		System.out.println(Thread.currentThread().getName()+" Loaded File '"+path+"' in "+(tp2 - tp1)+"ms, Read "+data.length+" Bytes...");
		
		if(cb != null)
			cb.Invoke(data);
	}

	private static void SaveStringsThread(String path, String... data) {
		IO.SaveStrings(path, data);
	}
	
	private static void SendStringOverSocketThread(Socket sock, String msg) {
		IO.WriteStringToSocket(sock, msg);
	}
	
	private static void ReadStringFromSocketThread(Socket sock, Callback<String> callback) {
		callback.Invoke(IO.ReadStringFromSocket(sock));
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
