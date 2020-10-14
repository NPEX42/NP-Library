package np.core;

import java.io.*;
import java.net.Socket;

public class IO {
	public static BufferedReader OpenReader(String file) throws IOException {
		return new BufferedReader(new FileReader(file));
	}
	
	public static BufferedWriter OpenWriter(String file) throws IOException {
		return new BufferedWriter(new FileWriter(file));
	}
	
	public static boolean FileExists(String path) {
		return new File(path).exists();
	}
	
	public static boolean CreateNewFile(String path) {
		try {
			new File(path).createNewFile();
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	
	public static boolean CreateNewDir(String path) {
		try {
			File file = new File(path);
			file.mkdir();
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	
	public static String ReadString(String path) {
		try (BufferedReader reader = OpenReader(path)) {
			String line;
			StringBuffer buffer = new StringBuffer();
			while((line = reader.readLine()) != null) {
				buffer.append(line).append('\n');
			}
			return buffer.toString();
		} catch (IOException e) {
			return "";
		} 
	}
	
	public static String[] ReadLines(String path) {
		return ReadString(path).split("\n");
	}
	
	public static boolean SaveStrings(String path, String... lines) {
		try (BufferedWriter writer = OpenWriter(path)) {
			for(String line : lines) {
				writer.write(line);
				writer.newLine();
			}
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		} 
	}
	
	public static String ReadStringFromSocket(Socket sock) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			return reader.readLine();
		} catch (IOException e) {
			return "";
		}
	}

	public static boolean WriteStringToSocket(Socket sock, String msg) {
		try {
			PrintWriter printer = new PrintWriter(sock.getOutputStream());
			printer.print(msg);
			printer.flush();
			return true;
		} catch (IOException e) {
			System.err.println("======"+Thread.currentThread().getName()+"======");
			e.printStackTrace();
			return false;
		}
	}
}
