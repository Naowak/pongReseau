package pong.src;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;



public class Ball extends PongObjectAbstract {

	public static final int COLLISION_GAUCHE = 1;
	public static final int COLLISION_DROITE = 2;
	public static final int COLLISION_HAUTE  = 3;
	public static final int COLLISION_BAS = 4;

	private static final int BALL_SIZE = 0;
	private static final Point BALL_SPEED = new Point(2, 2);
	private static final String NAME_SPRITE = "../../image/ball.png";

	public Ball(Point p){
		setPosition(p);
		setSize(BALL_SIZE);
		setSpeed((Point) BALL_SPEED.clone());
		setBelongsTo(0);
		setImage(Toolkit.getDefaultToolkit().createImage(
				              ClassLoader.getSystemResource("image/ball.png")));

		initHeigthWidth();

	}

	public Ball(int x, int y){
		this(new Point(x, y));
	}

	public boolean collision(Racket r){
		if(r.getBelongsTo() == GAUCHE){
			return getAbscisse() - (getWidth() / 2) <= r.getAbscisse() + (r.getWidth() / 2)
				&& getOrdonnee() >= r.getOrdonnee() 
				&& getOrdonnee() <= (r.getOrdonnee() + r.getSize());
		}
		else if(r.getBelongsTo() == DROITE){
			return getAbscisse() >= r.getAbscisse() 
					&& getOrdonnee() >= r.getOrdonnee() 
					&& getOrdonnee() <= (r.getOrdonnee() + r.getSize());
		}
		return false;
	}

	public void update(int type){
		switch(type){
			case 1:
			case 2:
				setSpeedAbscisse(-getSpeedAbscisse());
				break;
			case 3:
			case 4:
				setSpeedOrdonnee(-getSpeedOrdonnee());
				break;
		}
		move();
	}
}