package pong.src;

import java.awt.Point;

public class Ball extends PongObjectAbstract {

	private static final int BALL_SIZE = 0;

	public Ball(Point p, int speed){
		setPosition(p);
		setSize(BALL_SIZE);
		setSpeed(speed);
		setBelongsTo(0);
	}

	public Ball(int x, int y, int speed){
		this(new Point(x, y), speed);
	}

	public Ball(Point p){
		this(p, 0);
	}

	public Ball(int x, int y){
		this(new Point(x, y));
	}

	public boolean collision(Racket r){
		if(r.getBelongsTo() == 1){
			return getAbscisse() <= r.getAbscisse() 
					&& getOrdonnee() >= r.getOrdonnee() 
					&& getOrdonnee() <= (r.getOrdonnee() + r.getSize());
		}
		else if(r.getBelongsTo() == 2){
			return getAbscisse() >= r.getAbscisse() 
					&& getOrdonnee() >= r.getOrdonnee() 
					&& getOrdonnee() <= (r.getOrdonnee() + r.getSize());
		}
	}
}