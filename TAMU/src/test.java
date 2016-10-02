import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.*;
import javafx.scene.media.*;
import javafx.stage.*;

public class test{

  public static void main(String[] args) {
	  play("music/snare.wav");
  }
  public static void play(String s) {
		try {
			File f = new File(s);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(f));
			clip.start();
			System.out.println("test");
			Thread.sleep(clip.getMicrosecondLength());
		}catch(Exception e) {System.out.println("balls: " + e.getMessage());}
	}
}
