package pong.src;
import java.awt.Point;


interface PongObjectInterface {
	


	void setPosition(Point p);
	void setPosition(int x, int y);
	void setSize(int s);
	void setSpeed(int s);

	Point getPosition(); //retourne la position de base de l'objet.
	int getAbscisse();
	int getOrdonnee();
	int getSize(); //retourne la hauteur de l'objet.
	int getSpeed(); //retourne la vitesse de l'objet.
	
	
}