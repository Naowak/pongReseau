package pong.src;
import java.awt.Point;
import java.awt.Image;


interface PongObjectInterface {

	void setPosition(Point p);
	void setPosition(int x, int y);
	void setSpeed(Point s);
	void setSpeedAbscisse(int sx);
	void setSpeedOrdonnee(int sy);
	void setImage(Image img);
	void setImageHeigth(int size);
	void setImageWidth(int size);
	void setBelongsTo(int joueur); // 0:personne, 1:joueur1, 2:joueur2 .....

	Point getPosition(); //retourne la position de base de l'objet.
	int getAbscisse();
	int getOrdonnee();
	Point getSpeed(); //retourne la vitesse de l'objet.
	int getSpeedAbscisse();
	int getSpeedOrdonnee();
	Image getImage();
	int getImageHeigth();
	int getImageWidth();
	int getBelongsTo(); //A quel joueur apparient l'objet

	void update(int type); //Met l'objet a jour.	
}