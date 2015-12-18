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
        stopSound();
    	switch(sound){
    		case 1 :
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

     private void stopSound(){
        sound_ball.stop();
        sound_win.stop();
        sound_lose.stop();
    }
}