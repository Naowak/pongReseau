package pong;

import pong.src.*;
import pong.gui.*;

/**
 * Starting point of the Pong application
 */
public class Main  {

	/**
	 * This function explain how to use the program.
	 */
	public static void usage() {
		System.out.println("This is like a classic pong, but with two rackets by player.");
		System.out.println("usage : java pong.Main [argument]");
		System.out.println("argument can be :");
		System.out.println("\t--help :            To display this message.");
		System.out.println("\t--commandes :       To display how to play.");
		System.out.println("\tan url or ip adress To start playing against a player on the");
		System.out.println("\t                    machine at specified adress.");
	}

	/**
	 * This function explain how to use the game.
	 */
	public static void commandes() {
		System.out.println("Like the so much famous old game \"Tennis for Two players\" this pong is like a");
		System.out.println("tennis simulation, playing againts a friend who's also owning the game on his");
		System.out.println("computer. Each player is holding two rackets. You can control the big one on the");
		System.out.println("left by using the keys 'z' and 's' (pr 'w' and 's' depending on if you are");
		System.out.println("azerty or qwerty. You can control the little one on the rigth by using arrow");
		System.out.println("keys. The fist player with 8 points will win, and the game will be over.");
		System.out.println("Tip for winning : Put some effects in your ball !");
	}

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Wrong arguments. Use pong.Main --help for more informations.");
            System.exit(1);
        }

        if(args[0].equals("--help")){
            usage();
            System.exit(1);
        }

        if(args[0].equals("--commandes")){
        	commandes();
        	System.exit(1);
        }

        Pong pong = new Pong(args[0]);
        Window window = new Window(pong);
        window.displayOnscreen();
    }
}
