package pong.src;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;



public class Ball extends PongObjectAbstract {

	public static final int COLLISION_GAUCHE        = 11;
	public static final int COLLISION_GAUCHE_STABLE = 11;
	public static final int COLLISION_GAUCHE_MONTE  = 12;
	public static final int COLLISION_GAUCHE_DECEND = 13;
	public static final int COLLISION_DROITE        = 21;
	public static final int COLLISION_DROITE_STABLE = 21;
	public static final int COLLISION_DROITE_MONTE  = 22;
	public static final int COLLISION_DROITE_DECEND = 23;
	public static final int COLLISION_HAUTE         = 31;
	public static final int COLLISION_BAS           = 41;
	public static final int NO_COLLISION            = 51;

	private static final int BALL_SIZE = 0;
	private static final Point BALL_SPEED = new Point(5, 3);
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

	public boolean collision(Racket r) {
		if(r.getBelongsTo() == GAUCHE) {
			return getAbscisse() - (getImageWidth() / 2) <= r.getAbscisse() + (r.getImageWidth() / 2)
				&& getAbscisse() - (getImageWidth() / 2) >= r.getAbscisse() - (r.getImageWidth() / 2)
				&& getOrdonnee() >= r.getOrdonnee() 
				&& getOrdonnee() <= (r.getOrdonnee() + r.getSize());
		}
		else {
			return getAbscisse() + (getImageWidth() / 2) >= r.getAbscisse() - (r.getImageWidth() / 2)
				&& getAbscisse() + (getImageWidth() / 2) <= r.getAbscisse() + (r.getImageWidth() / 2)
				&& getOrdonnee() >= r.getOrdonnee() 
				&& getOrdonnee() <= (r.getOrdonnee() + r.getSize());
		}
	}

	public void update(int type){
		switch(type){
			case COLLISION_GAUCHE:
			case COLLISION_GAUCHE_STABLE:
				if(getSpeedAbscisse() < 0)
					setSpeedAbscisse(-getSpeedAbscisse());
				break;
			case COLLISION_GAUCHE_MONTE:
				if(getSpeedAbscisse() < 0){
					setSpeedAbscisse(-getSpeedAbscisse());
					setSpeedOrdonnee(getSpeedOrdonnee() + 1);
				}
				break;
			case COLLISION_GAUCHE_DECEND:
				if(getSpeedAbscisse() < 0){
					setSpeedAbscisse(-getSpeedAbscisse());
					setSpeedOrdonnee(getSpeedOrdonnee() - 1);
				}
				break;
			case COLLISION_DROITE:
			case COLLISION_DROITE_STABLE:
				if(getSpeedAbscisse() > 0)
					setSpeedAbscisse(-getSpeedAbscisse());
				break;
			case COLLISION_DROITE_MONTE:
				if(getSpeedAbscisse() > 0){
					setSpeedAbscisse(-getSpeedAbscisse());
					setSpeedOrdonnee(getSpeedOrdonnee() + 1);
				}
				break;
			case COLLISION_DROITE_DECEND:
				if(getSpeedAbscisse() > 0){
					setSpeedAbscisse(-getSpeedAbscisse());
					setSpeedOrdonnee(getSpeedOrdonnee() - 1);
				}
				break;
			case COLLISION_HAUTE:
				if(getSpeedOrdonnee() < 0)
					setSpeedOrdonnee(-getSpeedOrdonnee());
				break;
			case COLLISION_BAS:
				if(getSpeedOrdonnee() > 0)
					setSpeedOrdonnee(-getSpeedOrdonnee());
				break;
			default:
				break;
		}
		move();
		//System.out.println(getPosition());
	}
}

