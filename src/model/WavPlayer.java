package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

public class WavPlayer {

	public void play(String filename) {
		try {
			String filePath = "src/documents/" + filename;
			File audioFile = new File(filePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			// If you want to wait for the sound to finish playing before returning,
			// uncomment the following line:
			// Thread.sleep(clip.getMicrosecondLength() / 1000);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} // Uncomment the following lines if you uncommented the Thread.sleep() line
			// above
			// catch (InterruptedException e) {
			// e.printStackTrace();
			// }
	}
}
