package pong.src;

import java.lang.Thread;
import java.applet.Applet;
import java.applet.AudioClip;

public class Sound extends Thread{
	private AudioClip sound_ball;

    private AudioClip sound_win;

    private AudioClip sound_lose;

    public static final int BALL_SOUND = 1;

    public static final int WIN_SOUND = 2;

    public static final int LOSE_SOUND = 3;

    public Sound(){
    	sound_ball = Applet.newAudioClip(getClass().getResource("../../sound/sound_ball.wav"));
    	sound_win = Applet.newAudioClip(getClass().getResource("../../sound/sound_win.wav"));
    	sound_lose = Applet.newAudioClip(getClass().getResource("../../sound/sound_lose.wav"));
    }

    public void playSound(int sound){
    	switch(sound){
    		case 1 :
    			System.out.println("etape suivante");
    			sound_ball.play();
    			break;
    		case 2 :
    			sound_win.play();
    			break;
    		case 3 :
    			sound_lose.play();
    			break;
    	}
    }
}