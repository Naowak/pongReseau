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

import javax.swing.JPanel;

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

	/**
	 * Objet : ball
	 */
	public PongObjectInterface ball;

	/**
	 * Objet : Raquette joueur 1
	 */
	public PongObjectInterface racket1;
	/**
	 * Objet : Raquette joueur 2
	 */
	public PongObjectInterface racket2;

	/**
	 * Pixel data buffer for the Pong rendering
	 */
	private Image buffer = null;
	/**
	 * Graphic component context derived from buffer Image
	 */
	private Graphics graphicContext = null;

	private int racketMovement = DO_NOT_MOVE;

	public Pong() {
		ball = new Ball(40, SIZE_PONG_Y / 2);
		racket1 = new Racket(10, (SIZE_PONG_Y /2) - 50, 1);
		racket2 = new Racket(SIZE_PONG_X - 30, (SIZE_PONG_Y / 2) - 50, 2);

		this.setPreferredSize(new Dimension(SIZE_PONG_X, SIZE_PONG_Y));
		this.addKeyListener(this);
	}

	public void animate() {
		racket1.update(racketMovement);
		if(ball.collision(racket1)){
			ball.update(COLLISION_GAUCHE);
		} else if (ball.collision(racket2)) {
			ball.update(COLLISION_DROITE);
		}
		if(ball.getOrdonnee() <= (ball.getHeigth() / 2){
			ball.update(COLLISION_HAUTE);
		} else if (ball.getOrdonnee() >= SIZE_PONG_Y - (ball.getHeigth() / 2) {
			ball.update(COLLISION_BAS);
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				racketMovement = MOVE_UP;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				racketMovement = MOVE_DOWN;
				break;
			default:
				System.out.println("got release "+e);
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				racket_speed = DO_NOT_MOVE;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				racket_speed = DO_NOT_MOVE;
				break;
			default:
				System.out.println("got release "+e);
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

	public void updateScreen() {
		if (buffer == null) {
			/* First time we get called with all windows initialized */
			buffer = createImage(SIZE_PONG_X, SIZE_PONG_Y);
			if (buffer == null)
				throw new RuntimeException("Could not instanciate graphics");
			else
				graphicContext = buffer.getGraphics();
		}
		/* Fill the area with blue */
		graphicContext.setColor(backgroundColor);
		graphicContext.fillRect(0, 0, SIZE_PONG_X, SIZE_PONG_Y);

		/* Draw items */
		graphicContext.drawImage(ball.getImage(),
								 ball.getAbscisse() - (ball.getWidth() / 2),
		                         ball.getOrdonnee() - (ball.getHeigth() / 2),
		                         ball.getWigth(), ball.getHeight(), null);
		graphicContext.drawImage(racket1.getImage(),
			                     racket1.getAbscisse() - (racket1.getWidth() / 2),
			                     racket1.getOrdonnee() - (racket1.getHeigth() / 2),
			                     racket1.getWidth(), racket1.getHeight(), null);

		this.repaint();
	}