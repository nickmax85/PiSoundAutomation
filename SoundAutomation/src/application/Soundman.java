package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.sql.rowset.spi.SyncResolver;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Soundman {

	private Player player;

	private boolean play;
	private boolean repeat;

	private String pathToMusic = System.getProperty("user.home") + File.separator + "Music" + File.separator;
	private String filename;

	private String playSong;
	private String lastSong;

	public void play(String filename) {

		this.filename = filename;
		this.playSong = filename;

		System.out.println("Letztes Lied: " + lastSong + "; Neues Lied: " + playSong);

		play = true;

	}

	public void stop() {

		play = false;
		repeat = false;

		if (player != null) {
			player.close();
			System.out.println("Player wurde gestoppt");
		}

		System.out.println("Methode stop(): playerStart= " + play);
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isPlay() {
		return play;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public String getPathToMusic() {
		return pathToMusic;
	}

	public String getFilename() {
		return filename;
	}

	public String getPlaySong() {
		return playSong;
	}

	public String getLastSong() {
		return lastSong;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void setPathToMusic(String pathToMusic) {
		this.pathToMusic = pathToMusic;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setPlaySong(String playSong) {
		this.playSong = playSong;
	}

	public void setLastSong(String lastSong) {
		this.lastSong = lastSong;
	}

}
