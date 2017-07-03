import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import sun.applet.Main;

public class SFX {

	boolean volume = true;
	Clip nara, glass;
	Clip kevBGM, kevEnd, cutlery, guitar, mug, pot;
	Clip markBGM, markEnd, fuckingYurt, gwan;
	Clip rawnyBGM, rawnyEnd;

	public SFX() {
		try { 
			// NARA
			URL defaultSound = Main.class.getResource("/Audio/Nara.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			nara = AudioSystem.getClip();
			nara.open(audioInputStream);
			
			// glass
			defaultSound = Main.class.getResource("/Audio/glass.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			glass = AudioSystem.getClip();
			glass.open(audioInputStream);

			// KEV BGM
			defaultSound = Main.class.getResource("/kevGame/Audio/kevGame-BGM.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			kevBGM = AudioSystem.getClip();
			kevBGM.open(audioInputStream);

			// KEV END
			defaultSound = Main.class.getResource("/kevGame/Audio/FuckSake.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			kevEnd = AudioSystem.getClip();
			kevEnd.open(audioInputStream);

			// KEV guitar
			defaultSound = Main.class.getResource("/kevGame/Audio/guitar.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			guitar = AudioSystem.getClip();
			guitar.open(audioInputStream);

			// KEV cutlery
			defaultSound = Main.class.getResource("/kevGame/Audio/cutlery.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			cutlery = AudioSystem.getClip();
			cutlery.open(audioInputStream);

			// KEV mug
			defaultSound = Main.class.getResource("/kevGame/Audio/mug.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			mug = AudioSystem.getClip();
			mug.open(audioInputStream);

			// KEV pot
			defaultSound = Main.class.getResource("/kevGame/Audio/pot.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			pot = AudioSystem.getClip();
			pot.open(audioInputStream);

			// MARK BGM
			defaultSound = Main.class.getResource("/markGame/Audio/markGame-BGM.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			markBGM = AudioSystem.getClip();
			markBGM.open(audioInputStream);

			// MARK END
			defaultSound = Main.class.getResource("/markGame/Audio/Fucked.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			markEnd = AudioSystem.getClip();
			markEnd.open(audioInputStream);

			// MARK fuckingYurt
			defaultSound = Main.class.getResource("/markGame/Audio/FuckingYurt.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			fuckingYurt = AudioSystem.getClip();
			fuckingYurt.open(audioInputStream);

			// MARK gwan
			defaultSound = Main.class.getResource("/markGame/Audio/G'wan.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			gwan = AudioSystem.getClip();
			gwan.open(audioInputStream);

			// RAWNY BGM
			defaultSound = Main.class.getResource("/rawnyGame/Audio/rawnyGame-BGM.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			rawnyBGM = AudioSystem.getClip();
			rawnyBGM.open(audioInputStream);

			// RAWNY END
			defaultSound = Main.class.getResource("/rawnyGame/Audio/Yurt.wav");
			audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			rawnyEnd = AudioSystem.getClip();
			rawnyEnd.open(audioInputStream);

		} catch (Exception ex) {
			System.out.println("There was a problem playing audiofile.");
		}
	}

	public void playNara() {
		if (volume) {
			nara.setMicrosecondPosition(0);
			nara.start();
			nara.loop(nara.LOOP_CONTINUOUSLY);
		}
	}

	public void playglass() {
		if (volume) {
			glass.setMicrosecondPosition(0);
			glass.start();
		}
	}

	public void playKevBGM() {
		if (volume) {
			kevBGM.setMicrosecondPosition(0);
			kevBGM.start();
			kevBGM.loop(kevBGM.LOOP_CONTINUOUSLY);
		}
	}

	public void playKevEnd() {
		if (volume) {
			kevEnd.setMicrosecondPosition(0);
			kevEnd.start();
		}
	}

	public void playCutlery() {
		if (volume) {
			cutlery.setMicrosecondPosition(0);
			cutlery.start();
		}
	}

	public void playGuitar() {
		if (volume) {
			guitar.setMicrosecondPosition(0);
			guitar.start();
		}
	}

	public void playMug() {
		if (volume) {
			mug.setMicrosecondPosition(0);
			mug.start();
		}
	}

	public void playPot() {
		if (volume) {
			pot.setMicrosecondPosition(0);
			pot.start();
		}
	}

	public void playMarkBGM() {
		if (volume) {
			markBGM.setMicrosecondPosition(0);
			markBGM.start();
			markBGM.loop(markBGM.LOOP_CONTINUOUSLY);
		}
	}

	public void playMarkEND() {
		if (volume) {
			markEnd.setMicrosecondPosition(0);
			markEnd.start();
		}
	}

	public void playFuckingYurt() {
		if (volume) {
			fuckingYurt.setMicrosecondPosition(0);
			fuckingYurt.start();
		}
	}

	public void playGwan() {
		if (volume) {
			gwan.setMicrosecondPosition(0);
			gwan.start();
		}
	}

	public void playRawnyBGM() {
		if (volume) {
			rawnyBGM.setMicrosecondPosition(0);
			rawnyBGM.start();
			rawnyBGM.loop(rawnyBGM.LOOP_CONTINUOUSLY);
		}
	}

	public void playRawnyEND() {
		if (volume) {
			rawnyEnd.setMicrosecondPosition(0);
			rawnyEnd.start();
			rawnyEnd.loop(rawnyEnd.LOOP_CONTINUOUSLY);
		}
	}
}
