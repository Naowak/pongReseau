package pong.src;

import java.awt.Point;

public class Racket extends PongObjectAbstract {

	private static final int RACKET_SIZE = 50;
	private static final Point RACKET_SPEED = new Point(0, 4);

	public Racket(Point p, int joueur){
		setPosition(p);
		setSize(RACKET_SIZE);
		setSpeed(RACKET_SPEED);
		setBelongsTo(joueur);
	}

	public Racket(int x, int y, int joueur){
		this(new Point(x, y), joueur);
	}
	
}