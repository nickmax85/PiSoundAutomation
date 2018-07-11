package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SoundPlayerThread implements Runnable {

	private SoundPlayer soundPlayer;

	public SoundPlayerThread(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;

	}

	@Override
	public void run() {

		Player player = null;

		System.out.println(Thread.currentThread().getName() + ": Start");
		PiSoundAutomation.updateList(Thread.currentThread().getName() + ": Start");

		while (!Thread.currentThread().isInterrupted()) {

			try {

				if (soundPlayer.isPlay()) {

					System.out.println("gewaehltes Lied: " + soundPlayer.getSong());
					PiSoundAutomation.updateList("gewaehltes Lied: " + soundPlayer.getSong());

					// while (soundPlayer.isPlay() && soundPlayer.getSong().isRepeat()) {
					// System.out.println("Wiederholschleife Lied: " + soundPlayer.getSong());
					// PiSoundAutomation.updateList("Wiederholschleife Lied: " +
					// soundPlayer.getSong());
					// FileInputStream fileInputStream = new
					// FileInputStream(soundPlayer.getSong().getFilename());
					// player = new Player(fileInputStream);
					// soundPlayer.setPlayer(player);
					//
					// System.out.println("Player wird gestartet");
					// PiSoundAutomation.updateList("Player wird gestartet");
					// soundPlayer.getPlayer().play();
					// System.out.println("Player ist fertig");
					// PiSoundAutomation.updateList("Player ist fertig");
					//
					// }

					// if (!soundPlayer.getSong().isRepeat()) {
					// FileInputStream fileInputStream = new
					// FileInputStream(soundPlayer.getSong().getFilename());
					// player = new Player(fileInputStream);
					// soundPlayer.setPlayer(player);
					//
					// System.out.println("Player starten ...");
					// PiSoundAutomation.updateList("Player starten ...");
					// soundPlayer.getPlayer().play();
					// System.out.println("Player ist fertig");
					// PiSoundAutomation.updateList("Player ist fertig");
					//
					// soundPlayer.setPlay(false);
					//
					// }

					FileInputStream fileInputStream = new FileInputStream(soundPlayer.getSong().getFilename());
					player = new Player(fileInputStream);
					soundPlayer.setPlayer(player);

					System.out.println("Player wird gestartet");
					PiSoundAutomation.updateList("Player wird gestartet");
					soundPlayer.getPlayer().play();
					System.out.println("Player ist fertig");
					PiSoundAutomation.updateList("Player ist fertig");

					if (soundPlayer.getSong().isRepeat()) {
						while (soundPlayer.isPlay()) {
							System.out.println("Wiederholschleife Lied: " + soundPlayer.getSong());
							PiSoundAutomation.updateList("Wiederholschleife Lied: " + soundPlayer.getSong());
							FileInputStream fileInputStream2 = new FileInputStream(soundPlayer.getSong().getFilename());
							player = new Player(fileInputStream2);
							soundPlayer.setPlayer(player);

							System.out.println("Player wird gestartet");
							PiSoundAutomation.updateList("Player wird gestartet");
							soundPlayer.getPlayer().play();
							System.out.println("Player ist fertig");
							PiSoundAutomation.updateList("Player ist fertig");

						}

					}

					if (!soundPlayer.getSong().isRepeat())
						soundPlayer.setPlay(false);

					soundPlayer.setLastSong(soundPlayer.getSong());

					Thread.sleep(10);

				}

			} catch (JavaLayerException e1) {

				e1.printStackTrace();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {

				player.close();
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}

		}

	}

}
