package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SoundPlayer {

	// GPIO
	private GpioController gpio;

	private GpioPinDigitalInput input00;
	private GpioPinDigitalInput input01;
	private GpioPinDigitalInput input02;
	private GpioPinDigitalInput input03;

	// Music
	private String pathToMusic = System.getProperty("user.home") + File.separator + "Music" + File.separator;
	private String filename;
	private Player player;

	private int lastSong;
	private int newSong;

	private final String SONG_01 = "standby.mp3";
	private final String SONG_02 = "coinInserted.mp3";
	private final String SONG_03 = "playing.mp3";
	private final String SONG_04 = "coinInserted.mp3";

	private Thread playerThread;
	private boolean playerStarted;

	public static void main(String args[]) {

		new SoundPlayer();

	}

	public SoundPlayer() {

		initGpio();

	}

	private void initGpio() {

		System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

		gpio = GpioFactory.getInstance();

		input00 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
		input01 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		input02 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		input03 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);

		// set shutdown state for this input pin
		input00.setShutdownOptions(true);
		input01.setShutdownOptions(true);
		input02.setShutdownOptions(true);
		input03.setShutdownOptions(true);

		// create and register gpio pin listener
		input00.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					newSong = 1;
					play();

				}

				if (event.getState() == PinState.LOW) {
					stopPlayerThread();

				}

			}

		});

		input01.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {
					newSong = 2;
					play();

				}

				if (event.getState() == PinState.LOW) {
					stopPlayerThread();

				}

			}

		});
		input02.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					newSong = 3;
					play();

				}

				if (event.getState() == PinState.LOW) {
					stopPlayerThread();

				}

			}

		});
		input03.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					newSong = 4;
					play();
				}

				if (event.getState() == PinState.LOW) {
					stopPlayerThread();

				}

			}

		});

		System.out.println(" ... see the listener feedback here in the console.");

		// keep program running until user aborts (CTRL-C)
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void play() {

		switch (newSong) {
		case 1:
			filename = SONG_01;
			break;
		case 2:
			filename = SONG_02;
			break;
		case 3:
			filename = SONG_03;
			break;
		case 4:
			filename = SONG_04;
			break;
		default:
			break;
		}

		System.out.println("Letztes Lied: " + lastSong);
		System.out.println("Neues Lied: " + newSong);

		stopPlayerThread();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// FileInputStream fileInputStream = new FileInputStream(pathToMusic +
		// filename);
		// player = new Player(fileInputStream);

		startPlayerThread();

		// } catch (JavaLayerException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	private void startPlayerThread() {

		playerThread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (!Thread.currentThread().isInterrupted()) {

					System.out.println("Thread is running ...");

					try {
						FileInputStream fileInputStream = new FileInputStream(pathToMusic + filename);
						player = new Player(fileInputStream);

						if (player.isComplete() || newSong != lastSong) {

						}

						playerStarted = true;

						System.out.println("Player wird gestartet");
						player.play();
						System.out.println("Player ist fertig");

						lastSong = newSong;

					} catch (JavaLayerException e1) {

						e1.printStackTrace();
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}

					try {
						Thread.sleep(1000);

					} catch (InterruptedException e) {

						e.printStackTrace();
						Thread.currentThread().interrupt();

					}

				}

			}
		});
		playerThread.start();

	}

	private void stopPlayerThread() {

		if (playerThread != null) {
			playerThread.interrupt();
			player.close();

			System.out.println("Player wurde gestoppt");

			playerStarted = false;
		}
	}

}
