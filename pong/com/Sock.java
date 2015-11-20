package pong.com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Sock{

	public Sock(String ipv4){
		try{
			socket = new Socket(ipv4, PORT);
			System.out.println("Connection to host successfull.");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Creation d'un ServerSocket");
			try{
				servSocket = new ServerSocket(PORT);
				socket = servSocket.accept();
				System.out.println("Client accepte, debut de connection.");
				System.out.println(socket.toString());
			} catch (IOException e2) {
				System.out.println(e2);
			}

		}
		inStream  = socket.getInputStream();
		outStream = socket.getOutputStream();
	}

	public int communicate(int envoyer){
		outStream.write(envoyer);
		return inStream.read();
	}

	public void close(){
		try{
			if(socket != null)
				socket.close();
			if(servSocket != null)
				servSocket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String argv[]){
		Sock my_Sock = new Sock(argv[0]);

		System.out.println(communicate(1));
		System.out.println(communicate(2));
		System.out.println(communicate(3));
		System.out.println(communicate(4));

		my_Sock.close();
	}


	private final static int PORT = 10654;
	private static Socket socket;
	private static ServerSocket servSocket;

	private InputStream inStream;
	private OutputStream outStream;
}

// 10.3.7.126