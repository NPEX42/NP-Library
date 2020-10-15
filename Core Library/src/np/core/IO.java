package np.core;

import java.io.*;
import java.net.Socket;

import np.core.async.ASIO.FileSavedCallbackData;

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

	public static byte[] LoadBytes(String path) {
		File file = new File(path);
		int length = (int) file.length();
		byte[] data = new byte[length];
		try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
			for(int i = 0; i < length; i++) {
				data[i] = stream.readByte();
			}
			return data;
		} catch(IOException ioex) {
			return new byte[0];
		}
	}
	
	public static boolean SaveBytes(String path, byte... data) {
		try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(path))) {
			for(byte b : data) {
				stream.writeByte(b);
			}
			return true;
		} catch (IOException ioex) {
			return false;
		} 
	}
	
	public static boolean SaveObject(String path, Serializable obj) {
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path))) {
			stream.writeObject(obj);
			return true;
		} catch (IOException ioex) {
			return false;
		}
	}

	public static Object LoadObjectOrNull(String path) {
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path))) {
			return stream.readObject();
		} catch (ObjectStreamException obsex) {
			return null;
		} catch (IOException ioex) {
			return null;
		} catch (ClassNotFoundException cnfex) {
			return null;
		}
	}
}
