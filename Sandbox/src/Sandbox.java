import java.io.IOException;
import java.net.UnknownHostException;

import np.core.IO;
import np.core.async.ASIO;

public class Sandbox {

	public static void main(String[] args) throws UnknownHostException, IOException {
		byte[] data = IO.LoadBytes("assets/Test.txt");
		ASIO.SaveBytes("assets/engine2.so", data, Sandbox::SBCB);
		
	
	}
	
	public static void CB(String source) {
		System.out.println("Hello From "+Thread.currentThread().getName());
		System.out.println(source);
	}
	
	public static void SBCB(ASIO.FileSavedCallbackData data) {
		System.out.println(data.path);
		System.out.println(data.bytesWritten);
		
		IO.SaveObject("assets/cbdata.bin", data);
		data.path = "hello";
		
		data = (ASIO.FileSavedCallbackData) IO.LoadObjectOrNull("assets/cbdata.bin");
		System.out.println(data.path);
		System.out.println(data.bytesWritten);
	}

}
