package pong.src;

import java.awt.Point;

public abstract class PongObjectAbstract implements PongObjectInterface {

	protected static final int GAUCHE = 1;
	protected static final int DROITE = 2;

	public void setPosition(Point p){
		position = (Point) p.clone();
	}
	public void setPosition(int x, int y){
		position = new Point(x, y);
	}
	public void setSize(int s){
		size = s;
	}
	public void setSpeed(Point s){
		speed = (Point)s.clone();
	}
	public void setSpeedAbscisse(int sx){
		speed.setLocation(sx, (int)speed.getY());
	}
	public void setSpeedOrdonnee(int sy){
		speed.setLocation((int)speed.getX(), sy);
	}

	public void setBelongsTo(int joueur){
		belongsTo = joueur;
	}

	public Point getPosition(){
		return (Point) position.clone();
	}
	public int getAbscisse(){
		return (int) position.getX();
	}
	public int getOrdonnee(){
		return (int) position.getY();
	}
	public int getSize(){
		return size;
	}
	public Point getSpeed(){
		return (Point)speed.clone();
	}
	public int getSpeedAbscisse(){
		return (int)speed.getX();
	}
	public int getSpeedOrdonnee(){
		return (int)speed.getY();
	}

	public int getBelongsTo(){
		return belongsTo;
	}



	private Point position; //point le plus hauty d'un objet
	private int size;
	private Point speed;
	private int belongsTo;
}