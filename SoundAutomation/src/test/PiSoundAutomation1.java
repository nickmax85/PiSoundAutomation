package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

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

public class PiSoundAutomation1 extends JFrame {

	// GPIO
	private GpioController gpio;

	private GpioPinDigitalInput input00;
	private GpioPinDigitalInput input01;
	private GpioPinDigitalInput input02;
	private GpioPinDigitalInput input03;
	private GpioPinDigitalInput input04;

	// Music
	private String pathToMusic = System.getProperty("user.home") + File.separator + "Music" + File.separator;
	private String filename;
	private Player player;

	private int lastSong;
	private int playSong;

	private final String SONG_01 = "standby.mp3";
	private final String SONG_02 = "coinInserted.mp3";
	private final String SONG_03 = "playing.mp3";
	private final String SONG_04 = "winner.mp3";
	private final String SONG_05 = "looser.mp3";

	private Thread playerThread;
	private static boolean playerStart;

	private JList<String> list;
	private DefaultListModel<String> listModel;

	public static void main(String args[]) {

		new PiSoundAutomation1();

	}

	public PiSoundAutomation1() {

		initUi();
		initGpio();

	}

	private void initUi() {

		setTitle("PiSoundAutomation by Markus Thaler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));
		setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setFont(new Font("Arial", Font.PLAIN, 14));

		JScrollPane sp = new JScrollPane(list);
		sp.setAutoscrolls(true);

		getContentPane().add(sp, BorderLayout.CENTER);

		setVisible(true);

	}

	private void initGpio() {

		System.out.println("initGpio");
		updateList("initGpio");

		gpio = GpioFactory.getInstance();

		input00 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
		input01 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		input02 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		input03 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
		input04 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);

		// set shutdown state for this input pin
		input00.setShutdownOptions(true);
		input01.setShutdownOptions(true);
		input02.setShutdownOptions(true);
		input03.setShutdownOptions(true);
		input04.setShutdownOptions(true);

		// create and register gpio pin listener
		input00.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList("GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					playSong = 1;
					play();

				}

				if (event.getState() == PinState.LOW) {
					stop();

				}

			}

		});

		input01.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList("GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {
					playSong = 2;
					play();

				}

				if (event.getState() == PinState.LOW) {
					stop();

				}

			}

		});
		input02.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList("GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					playSong = 3;
					play();

				}

				if (event.getState() == PinState.LOW) {
					stop();

				}

			}

		});
		input03.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList("GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					playSong = 4;
					play();
				}

				if (event.getState() == PinState.LOW) {
					stop();

				}

			}

		});
		input04.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList("GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					playSong = 5;
					play();
				}

				if (event.getState() == PinState.LOW) {
					stop();

				}

			}

		});

		startPlayerThread();

		// Programm laufen lassen (CTRL-C)
		// while (true) {
		// try {
		// Thread.sleep(500);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

	}

	private synchronized void play() {

		switch (playSong) {
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
		case 5:
			filename = SONG_05;
			break;
		default:
			break;
		}

		System.out.println("Letztes Lied: " + lastSong + "; Neues Lied: " + playSong);
		updateList("Letztes Lied: " + lastSong + "; Neues Lied: " + playSong);

		stop();

		try {
			FileInputStream fileInputStream = new FileInputStream(pathToMusic + filename);
			player = new Player(fileInputStream);

			playerStart = true;

		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void stop() {

		if (player != null) {
			player.close();

			if (playerStart) {

				System.out.println("Player wurde gestoppt");
				updateList("Player wurde gestoppt");
			}

		}
		playerStart = false;
		System.err.println("Methode stop(): playerStart= " + playerStart);
	}

	private void startPlayerThread() {

		playerThread = new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println(Thread.currentThread().getName() + ": Start");
				updateList(Thread.currentThread().getName() + ": Start");

				while (!Thread.currentThread().isInterrupted()) {

					// System.out.println(Thread.currentThread().getName() + ": while() Beginn");

					if (player != null)
						try {

							System.err.println("Methode startPlayerThread(): playerStart= " + playerStart);

							if (playerStart && playSong != lastSong) {
								System.out.println("Neues Lied wird ausgewaehlt: " + filename);
								updateList("Neues Lied wird ausgewaehlt: " + filename);
							}

							if (playerStart && playSong == lastSong) {
								System.out.println("Lied wird wiederholt: " + filename);
								updateList("Lied wird wiederholt: " + filename);

								FileInputStream fileInputStream = new FileInputStream(pathToMusic + filename);
								player = new Player(fileInputStream);

							}

							if (playerStart) {

								System.out.println("Player wird gestartet");
								updateList("Player wird gestartet");

								player.play();

								System.out.println("Player ist fertig");
								updateList("Player ist fertig");

								lastSong = playSong;
							}

						} catch (JavaLayerException e1) {

							e1.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					try {
						Thread.sleep(1);

					} catch (InterruptedException e) {

						e.printStackTrace();
						Thread.currentThread().interrupt();

					}

					// System.out.println(Thread.currentThread().getName() + ": while() Ende");

				}

			}
		});

		playerThread.start();

	}

	private void updateList(String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

				listModel.add(0, simpleDateFormat.format(date) + ": " + text);
				// listModel.addElement(text);
			}
		});
	}

}
