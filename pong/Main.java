package pong;

import pong.src.*;
import pong.gui.*;


/**
 * Starting point of the Pong application
 */
public class Main  {
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("usage: java Main [ip adress]");
			System.exit(0);
		}
		Pong pong = new Pong(args[0]);
		Window window = new Window(pong);
		window.displayOnscreen();
	}
}
