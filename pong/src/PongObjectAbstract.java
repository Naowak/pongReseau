package pong.src;

import java.awt.Point;

public abstract class PongObjectAbstract implements PongObjectInterface {

	public void setPosition(Point p){
		position = (Point) p.clone();
	}
	public void setPosition(int x, int y){
		position = new Point(x, y);
	}
	public void setSize(int s){
		size = s;
	}
	public void setSpeed(int s){
		speed = s;
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
	public int getSpeed(){
		return speed;
	}



	private Point position;
	private int size;
	private int speed;
}