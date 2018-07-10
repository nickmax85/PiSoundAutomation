package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class SoundmanThread implements Runnable {

	private Soundman soundman;

	public SoundmanThread(Soundman soundman) {
		this.soundman = soundman;
	}

	@Override
	public void run() {

		System.out.println(Thread.currentThread().getName() + ": Start");

		while (!Thread.currentThread().isInterrupted()) {

			// System.out.println(Thread.currentThread().getName() + ": while() Beginn");

			try {

				System.out.println("Methode run(): playerStart= " + soundman.isPlay());

				if (soundman.isPlay()) {

					if (soundman.getPlaySong() == soundman.getLastSong()) {
						System.out.println("Wiederholung Lied: " + soundman.getFilename());

						FileInputStream fileInputStream = new FileInputStream(
								soundman.getPathToMusic() + soundman.getFilename());

						Player pl = new Player(fileInputStream);

						soundman.setPlayer(pl);
					}

					if (soundman.getPlaySong() != soundman.getLastSong()) {
						System.out.println("Neues Lied: " + soundman.getFilename());

						FileInputStream fileInputStream = new FileInputStream(
								soundman.getPathToMusic() + soundman.getFilename());

						Player pl = new Player(fileInputStream);

						soundman.setPlayer(pl);
					}

					System.out.println("Player starten ...");
					soundman.getPlayer().play();

					System.out.println("Player ist fertig");

					soundman.setLastSong(soundman.getPlaySong());
				}

			} catch (JavaLayerException e1) {

				e1.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(100);

			} catch (InterruptedException e) {

				e.printStackTrace();
				Thread.currentThread().interrupt();

			}

			// System.out.println(Thread.currentThread().getName() + ": while() Ende");

		}

	}

}
