package pong.src;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class SmallRacket extends Racket {

    public SmallRacket(Point p, int joueur){
        super(p, joueur);

        ImageIcon icon = new ImageIcon(getImage());
        setImageHeigth(icon.getIconHeight() / 2);
        setImageWidth(icon.getIconWidth() / 2);
    }

    public SmallRacket(int x, int y, int joueur){
        this(new Point(x, y), joueur);
    }

}