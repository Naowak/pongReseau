package pong.src;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class Racket extends PongObjectAbstract {

	protected static final Point RACKET_SPEED = new Point(0, 4);

	public static final int DO_NOT_MOVE = 1;
	public static final int MOVE_UP     = 2;
	public static final int MOVE_DOWN   = 3;

	public Racket(Point p, int joueur){
		setPosition(p);
		setSpeed(RACKET_SPEED);
		setBelongsTo(joueur);
		if(joueur == 1)
			setImage(Toolkit.getDefaultToolkit().createImage(
				           ClassLoader.getSystemResource("image/racket1.png")));
		if(joueur == 2)
			setImage(Toolkit.getDefaultToolkit().createImage(
				           ClassLoader.getSystemResource("image/racket2.png")));

		initHeigthWidth();
	}

	public Racket(int x, int y, int joueur){
		this(new Point(x, y), joueur);
	}
	
	public void update(int type){
		switch (type) {
			case DO_NOT_MOVE:
				setSpeed(new Point(0, 0));
				break;
			case MOVE_UP:
				setSpeed(new Point((int)RACKET_SPEED.getX(), (int)-RACKET_SPEED.getY()));
				break;
			case MOVE_DOWN:
				setSpeed(new Point((int)RACKET_SPEED.getX(), (int)RACKET_SPEED.getY()));
				break;
		}
		move();
	}

}