package pong.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import javax.swing.JPanel;

import pong.com.Sock;

/**
 * An Pong is a Java graphical container that extends the JPanel class in
 * order to display graphical elements.
 */
public class Pong extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;

    /**
     * Constant (c.f. final) common to all Pong instances (c.f. static)
     * defining the background color of the Pong
     */
    private static final Color backgroundColor = new Color(0x77, 0x77, 0x77);

    /**
     * Width of pong area
     */
    public static final int SIZE_PONG_X = 800;
    /**
     * Height of pong area
     */
    public static final int SIZE_PONG_Y = 600;
    /**
     * Time step of the simulation (in ms)
     */
    public static final int timestep = 10;

    public static final int JOUEUR_GAUCHE = 1;
    public static final int JOUEUR_DROITE = 2;

    /**
     * Objet : ball
     */
    public Ball ball;

    /**
     * Objet : Raquette joueur 1
     */
    public Racket racket1;
    /**
     * Objet : Raquette joueur 2
     */
    public Racket racket2;
    /**
     * Objet : petite raquette frontale joueur 1
     */
    public SmallRacket smallRacket1;
    /**
     * Objet : petite raquette frontale joueur 2
     */
    public SmallRacket smallRacket2;
    /**
     * Object : Affichage des points du joueur 1
     */
    public Image imagePoints1;
    /**
     * Object : Affichage des points du joueur 2
     */
    public Image imagePoints2;

    /**
     * Pixel data buffer for the Pong rendering
     */
    private Image buffer = null;
    /**
     * Graphic component context derived from buffer Image
     */
    private Graphics graphicContext = null;


    private Sound sound;

    private int racketMovement       = Racket.DO_NOT_MOVE;
    private int racket2Movement      = Racket.DO_NOT_MOVE;
    private int smallRacketMovement  = Racket.DO_NOT_MOVE;
    private int smallRacket2Movement = Racket.DO_NOT_MOVE;


    /**
     * Points des joueurs
     */    
    private int pointsJoueurGauche;    
    private int pointsJoueurDroite;

    /** 
     * Temps avant lequel la balel ne bouge pas
     */
    private Instant restartTime;

    private Sock socket;

    public Pong(String ipv4) {
        socket = new Sock(ipv4);

        if(socket.isHost())
            ball = new Ball(40, SIZE_PONG_Y / 2);
        else{
            ball = new Ball(SIZE_PONG_X - 40 - 1, SIZE_PONG_Y / 2);
            ball.setSpeedAbscisse(- ball.getSpeedAbscisse());
        }

        pointsJoueurGauche = 0;
        pointsJoueurGauche = 0;
        restartTime = Instant.now();
        //restartTime = (Instant.now()).plusSeconds(2);

        racket1 = new Racket(20, (SIZE_PONG_Y / 2) - 50, 1);
        racket2 = new Racket(SIZE_PONG_X - 20 - 1, (SIZE_PONG_Y / 2) - 50, 2);

        smallRacket1 = new SmallRacket(350, (SIZE_PONG_Y / 2) - 25, 1);
        smallRacket2 = new SmallRacket(SIZE_PONG_X - 350 - 1, (SIZE_PONG_Y / 2) - 25, 2);

        imagePoints1 = Toolkit.getDefaultToolkit().createImage(
                            ClassLoader.getSystemResource("image/0_rouge.png"));
        imagePoints2 = Toolkit.getDefaultToolkit().createImage(
                            ClassLoader.getSystemResource("image/0_vert.png"));

        this.setPreferredSize(new Dimension(SIZE_PONG_X, SIZE_PONG_Y));
        this.addKeyListener(this);

        sound = new Sound();
    }

    public boolean animate() {
        int move = racketMovement;
        racket1.update(move);
        Integer message = new Integer(move);
        message = (Integer)socket.communicate(message);
        racket2Movement = message;
        racket2.update(racket2Movement); 

        int smallMove = smallRacketMovement;
        smallRacket1.update(smallMove);
        message = new Integer(smallMove);
        message = (Integer)socket.communicate(message);
        smallRacket2Movement = message;
        smallRacket2.update(smallRacket2Movement); 

        if((Instant.now()).isBefore(restartTime)) { //temps d'attente service
            updateScreen();
            return true; // On continue a jouer
        } else if(ball.getSpeedAbscisse() == 0) {
            if(ball.getAbscisse() < SIZE_PONG_X / 2){
                ball.setSpeedAbscisse(5);
                ball.setSpeedOrdonnee(3);
            } else {
                ball.setSpeedAbscisse(-5);
                ball.setSpeedOrdonnee(3);
            }
        }

        int ballCollision = Ball.NO_COLLISION;

        if(ball.collision(racket1)) {
            sound.playSound(Sound.BALL_SOUND);
            if(move == Racket.MOVE_UP)
                ballCollision = Ball.COLLISION_GAUCHE_MONTE;
            if(move == Racket.MOVE_DOWN)
                ballCollision = Ball.COLLISION_GAUCHE_DECEND;
            if(move == Racket.DO_NOT_MOVE)
                ballCollision = Ball.COLLISION_GAUCHE_STABLE;
        }
        if (ball.collision(racket2)) {
            sound.playSound(Sound.BALL_SOUND);
            if(racket2Movement == Racket.MOVE_UP)
                ballCollision = Ball.COLLISION_DROITE_MONTE;
            if(racket2Movement == Racket.MOVE_DOWN)
                ballCollision = Ball.COLLISION_DROITE_DECEND;
            if(racket2Movement == Racket.DO_NOT_MOVE)
                ballCollision = Ball.COLLISION_DROITE_STABLE;
        }

        if(ball.collision(smallRacket1)) {
            if(ball.getSpeedAbscisse() < 0)
                sound.playSound(Sound.BALL_SOUND);
            if(smallMove == Racket.MOVE_UP)
                ballCollision = Ball.COLLISION_GAUCHE_MONTE;
            if(smallMove == Racket.MOVE_DOWN)
                ballCollision = Ball.COLLISION_GAUCHE_DECEND;
            if(smallMove == Racket.DO_NOT_MOVE)
                ballCollision = Ball.COLLISION_GAUCHE_STABLE;
        }
        if (ball.collision(smallRacket2)) {
            if(ball.getSpeedAbscisse() > 0)
                sound.playSound(Sound.BALL_SOUND);
            if(smallRacket2Movement == Racket.MOVE_UP)
                ballCollision = Ball.COLLISION_DROITE_MONTE;
            if(smallRacket2Movement == Racket.MOVE_DOWN)
                ballCollision = Ball.COLLISION_DROITE_DECEND;
            if(smallRacket2Movement == Racket.DO_NOT_MOVE)
                ballCollision = Ball.COLLISION_DROITE_STABLE;
        }
        
        if(ball.getOrdonnee() <= (ball.getImageHeigth() / 2))
            ballCollision = Ball.COLLISION_HAUTE;
        if (ball.getOrdonnee() >= SIZE_PONG_Y - (ball.getImageHeigth() / 2))
            ballCollision = Ball.COLLISION_BAS;

        ball.update(ballCollision);

        if(ball.getAbscisse() < 0)
            pointMarque(JOUEUR_DROITE);
        if (ball.getAbscisse() >= SIZE_PONG_X)
            pointMarque(JOUEUR_GAUCHE);

        if(pointsJoueurGauche > 7) {
            sound.playSound(Sound.WIN_SOUND);
            updateScreenVictory();
            return false; // Fin du jeu
        } 
        if(pointsJoueurDroite > 7) {
            sound.playSound(Sound.LOSE_SOUND);
            updateScreenVictory();
            return false; // Fin du jeu
        }

        updateScreen();
        return true; // On continue a jouer
    }


    public void pointMarque(int joueur) {
        ball.setSpeedAbscisse(0);
        ball.setSpeedOrdonnee(0);
        ball.setPosition(ball.getAbscisse(), SIZE_PONG_Y / 2);
        if(joueur == JOUEUR_GAUCHE){
            ball.setPosition(SIZE_PONG_X - 40 - 1, ball.getOrdonnee());
            pointsJoueurGauche += 1;
            String newImageFile = "image/";
            newImageFile += Integer.toString(pointsJoueurGauche);
            newImageFile += "_rouge.png";
            imagePoints1 = Toolkit.getDefaultToolkit().createImage(
                                   ClassLoader.getSystemResource(newImageFile));
        }
        if(joueur == JOUEUR_DROITE){
            ball.setPosition(40, ball.getOrdonnee());
            pointsJoueurDroite += 1;
            String newImageFile = "image/";
            newImageFile += Integer.toString(pointsJoueurDroite);
            newImageFile += "_vert.png";
            imagePoints2 = Toolkit.getDefaultToolkit().createImage(
                                   ClassLoader.getSystemResource(newImageFile));
        }
        restartTime = (Instant.now()).plusSeconds(2);



    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_KP_UP:
                smallRacketMovement = Racket.MOVE_UP;
                break;
            case KeyEvent.VK_Z:
            case KeyEvent.VK_W:
                racketMovement = Racket.MOVE_UP;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_KP_DOWN:
                smallRacketMovement = Racket.MOVE_DOWN;
                break;
            case KeyEvent.VK_S:
                racketMovement = Racket.MOVE_DOWN;
                break;
            default:
                System.out.println("Pressing this doesn't do anything ......");
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_KP_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_KP_DOWN:
                smallRacketMovement = Racket.DO_NOT_MOVE;
                break;
            case KeyEvent.VK_Z:
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                racketMovement = Racket.DO_NOT_MOVE;
        }
    }

    public void keyTyped(KeyEvent e) { }

    /*
     * (non-Javadoc) This method is called by the AWT Engine to paint what
     * appears in the screen. The AWT engine calls the paint method every time
     * the operative system reports that the canvas has to be painted. When the
     * window is created for the first time paint is called. The paint method is
     * also called if we minimize and after we maximize the window and if we
     * change the size of the window with the mouse.
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public void updateScreenVictory(){
        if(pointsJoueurGauche > 7)
            graphicContext.setColor(new Color(0xAA, 0x00, 0x00)); 
        else
            graphicContext.setColor(new Color(0x00, 0x99, 0x11)); 

        graphicContext.fillRect(0, 0, SIZE_PONG_X, SIZE_PONG_Y);

        this.repaint();
    }

    public void updateScreen() {
        if (buffer == null) {
            /* First time we get called with all windows initialized */
            buffer = createImage(SIZE_PONG_X, SIZE_PONG_Y);
            if (buffer == null)
                throw new RuntimeException("Could not instanciate graphics");
            else
                graphicContext = buffer.getGraphics();
        }
        /* Fill the area with grey */
        graphicContext.setColor(backgroundColor); 
        graphicContext.fillRect(0, 0, SIZE_PONG_X, SIZE_PONG_Y);


        /* Draw items */
        graphicContext.drawImage(imagePoints1, 
                                 300, 50,
                                 50, 50, null);
        graphicContext.drawImage(imagePoints2, 
                                 450, 50,
                                 50, 50, null);

        graphicContext.drawImage(ball.getImage(),
                                 ball.getAbscisse() - (ball.getImageWidth() / 2),
                                 ball.getOrdonnee() - (ball.getImageHeigth() / 2),
                                 ball.getImageWidth(), ball.getImageHeigth(), null);
        graphicContext.drawImage(racket1.getImage(),
                                 racket1.getAbscisse() - (racket1.getImageWidth() / 2),
                                 racket1.getOrdonnee(),
                                 racket1.getImageWidth(), racket1.getImageHeigth(), null);
        graphicContext.drawImage(racket2.getImage(),
                                 racket2.getAbscisse() - (racket2.getImageWidth() / 2),
                                 racket2.getOrdonnee(),
                                 racket2.getImageWidth(), racket2.getImageHeigth(), null);
        graphicContext.drawImage(smallRacket1.getImage(),
                                 smallRacket1.getAbscisse() - (smallRacket1.getImageWidth() / 2),
                                 smallRacket1.getOrdonnee(),
                                 smallRacket1.getImageWidth(), smallRacket1.getImageHeigth(), null);
        graphicContext.drawImage(smallRacket2.getImage(),
                                 smallRacket2.getAbscisse() - (smallRacket2.getImageWidth() / 2),
                                 smallRacket2.getOrdonnee(),
                                 smallRacket2.getImageWidth(), smallRacket2.getImageHeigth(), null);

        this.repaint();
    }

}