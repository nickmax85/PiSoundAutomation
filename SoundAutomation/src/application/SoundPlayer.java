package application;

import javazoom.jl.player.Player;

public class SoundPlayer {

	private Player player;

	private boolean play;

	private Song song;
	private Song lastSong;

	public void play(Song song) {

		this.song = song;

		stop();

		try {
			Thread.sleep(500);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Player start");
		PiSoundAutomation.updateList("Player start");
		setPlay(true);

	}

	public void stop() {

		setPlay(false);

		if (player != null) {
			player.close();
			System.out.println("Player stop");
			PiSoundAutomation.updateList("Player stop");
		}

	}

	public Player getPlayer() {
		return player;
	}

	public boolean isPlay() {
		return play;
	}

	public Song getSong() {
		return song;
	}

	public Song getLastSong() {
		return lastSong;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public void setLastSong(Song lastSong) {
		this.lastSong = lastSong;
	}

}
