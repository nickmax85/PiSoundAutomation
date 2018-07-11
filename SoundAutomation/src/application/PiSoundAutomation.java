package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class PiSoundAutomation extends JFrame {

	// GPIO
	private GpioController gpio;
	private GpioPinDigitalInput input00;
	private GpioPinDigitalInput input01;
	private GpioPinDigitalInput input02;
	private GpioPinDigitalInput input03;
	private GpioPinDigitalInput input04;
	private GpioPinDigitalInput input05;

	// Music
	private String path = System.getProperty("user.home") + File.separator + "Music" + File.separator;
	private final String SONG_01 = "standby.mp3";
	private final String SONG_02 = "coinInserted.mp3";
	private final String SONG_03 = "playing.mp3";
	private final String SONG_04 = "winner.mp3";
	private final String SONG_05 = "looser.mp3";
	private List<Song> songs;

	private SoundPlayer soundPlayer;

	private JList<String> list;
	private static DefaultListModel<String> listModel;

	public static void main(String args[]) {

		new PiSoundAutomation();

	}

	public PiSoundAutomation() {

		initUi();
		initSongs();
		initGpio();
		initThread();
	}

	private void initUi() {

		setTitle("PiSoundAutomation");
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

	private void initThread() {

		soundPlayer = new SoundPlayer();
		SoundPlayerThread soundPlayerThread = new SoundPlayerThread(soundPlayer);

		Thread thread = new Thread(soundPlayerThread);
		thread.start();

	}

	private void initSongs() {

		songs = new ArrayList<Song>();
		songs.add(new Song(path + SONG_01, false));
		songs.add(new Song(path + SONG_02, false));
		songs.add(new Song(path + SONG_03, false));
		songs.add(new Song(path + SONG_04, true));
		songs.add(new Song(path + SONG_05, false));

	}

	private void initGpio() {

		System.out.println("initGpio");

		gpio = GpioFactory.getInstance();

		input00 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
		input01 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		input02 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		input03 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
		input04 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
		input05 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);

		// set shutdown state for this input pin
		input00.setShutdownOptions(true);
		input01.setShutdownOptions(true);
		input02.setShutdownOptions(true);
		input03.setShutdownOptions(true);
		input04.setShutdownOptions(true);
		input05.setShutdownOptions(true);

		// create and register gpio pin listener
		input00.addListener(new GpioPinListenerDigital() {
			// @Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList(event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {

					soundPlayer.play(songs.get(0));

				}

			}

		});

		input01.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList(event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {

					soundPlayer.play(songs.get(1));

				}

			}

		});
		input02.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList(event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {

					soundPlayer.play(songs.get(2));

				}

			}

		});
		input03.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList(event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {

					soundPlayer.play(songs.get(3));

				}

			}

		});
		input04.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList(event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {

					soundPlayer.play(songs.get(4));
				}

			}

		});

		input05.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				updateList(event.getPin() + " = " + event.getState());
				if (event.getState() == PinState.HIGH) {
					soundPlayer.stop();
				}

			}

		});

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

	public static void updateList(String text) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

				listModel.add(0, simpleDateFormat.format(date) + ": " + text);

			}
		});
	}

}
