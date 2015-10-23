package pong.src;

import java.awt.Point;

public class Racket extends PongObjectAbstract {

	private static final int RACKET_SIZE = 50;
	private static final int RACKET_SPEED = 4;

	public Racket(Point p){
		setPosition(p);
		setSize(RACKET_SIZE);
		setSpeed(RACKET_SPEED);
	}

	public Racket(int x, int y){
		this(new Point(x, y));
	}
	
}