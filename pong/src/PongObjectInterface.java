package pong.src;
import java.awt.Point;


interface PongObjectInterface {
	/*
	boolean collision(PongObjectInterface po);
	debattre de ca.
	*/

	void setPosition(Point p);
	void setPosition(int x, int y);
	void setSize(int s);
	void setSpeed(Point s);
	void setSpeedAbscisse(int sx);
	void setSpeedOrdonnee(int sy);
	void setBelongsTo(int joueur); // 0:personne, 1:joueur1, 2:joueur2 .....

	Point getPosition(); //retourne la position de base de l'objet.
	int getAbscisse();
	int getOrdonnee();
	int getSize(); //retourne la hauteur de l'objet.
	Point getSpeed(); //retourne la vitesse de l'objet.
	int getSpeedAbscisse();
	int getSpeedOrdonnee();
	int getBelongsTo(); //A quel joueur apparient l'objet

	//void update(); //Met l'objet a jour.
	
}