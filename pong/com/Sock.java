package pong.com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.SocketException;

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
		try{
			inStream  = socket.getInputStream();
			outStream = socket.getOutputStream();
		} catch (IOException e) {
			System.out.println(e);
		}
		try{
			socket.setTcpNoDelay(true);
		} catch (SocketException e) {
			System.out.println(e);
		}
		try{
			oos = new ObjectOutputStream(outStream); 
			System.out.println(oos);
			ois = new ObjectInputStream(inStream);
			System.out.println(ois);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public Object communicate(Object toSend){
		try{
			oos.writeObject(toSend);
			Object reception = new Object();
			reception = (Object)ois.readObject();
			return reception;
		} catch (IOException e) {
			System.out.println(e);
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean isHost(){
		return servSocket != null;
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

	/*public static void main(String argv[]){
		Sock my_Sock = new Sock(argv[0]);

		System.out.println(my_Sock.communicate(1));
		System.out.println(my_Sock.communicate(2));
		System.out.println(my_Sock.communicate(3));
		System.out.println(my_Sock.communicate(4));

		my_Sock.close();
	}*/


	private final static int PORT = 10654;
	private static Socket socket;
	private static ServerSocket servSocket;

	private InputStream inStream;
	private OutputStream outStream;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

}

