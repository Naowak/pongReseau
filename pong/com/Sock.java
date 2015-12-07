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
			System.out.println("Being host, waiting for stranger's connection.");
			try{
				servSocket = new ServerSocket(PORT);
				socket = servSocket.accept();
				System.out.println("Client accepted, communication starts.");
			} catch (IOException e2) {
				System.out.println("Connection failed, don't know why :/");
				System.exit(1);
			}
		}
		try{
			inStream  = socket.getInputStream();
			outStream = socket.getOutputStream();
		} catch (IOException e) {
			System.out.println("Error: Sock.java: unable to open streams");
			System.exit(1);
		}
		try{
			socket.setTcpNoDelay(true);
		} catch (SocketException e) {
			System.out.println("Warning: Sock.java: unable to disable Nagle's algorithme");
		}
		try{
			oos = new ObjectOutputStream(outStream); 
			ois = new ObjectInputStream(inStream);
		} catch (IOException e) {
			System.out.println("Error: Sock.java: unable to open Object streams");
			System.exit(1);
		}
	}

	public Object communicate(Object toSend){
		try{
			oos.writeObject(toSend);
			Object reception = new Object();
			reception = (Object)ois.readObject();
			return reception;
		} catch (SocketException e){
			System.out.println("Your partner just leaved the game.");
			System.exit(1);
			return null; //Never read this line, but never compile if not present ...
		} catch (IOException e) {
			System.out.println("Error: Sock.communicate: cannot communicate.");
			System.out.println(e);
			System.exit(1);
			return null; //Never read this line, but never compile if not present ...
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Sock.communicate: cannot communicate.");
			System.out.println(e);
			System.exit(1);
			return null; //Never read this line, but never compile if not present ...
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
			System.out.println("Unable to close streams.");
			System.out.println(e);
		}
	}


	private final static int PORT = 10654;
	private static Socket socket;
	private static ServerSocket servSocket;

	private InputStream inStream;
	private OutputStream outStream;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

}

