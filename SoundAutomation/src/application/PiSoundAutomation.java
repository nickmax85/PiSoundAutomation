package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
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

public class PiSoundAutomation extends JFrame {

	// GPIO
	private GpioController gpio;
	private GpioPinDigitalInput input00;
	private GpioPinDigitalInput input01;
	private GpioPinDigitalInput input02;
	private GpioPinDigitalInput input03;
	private GpioPinDigitalInput input04;
	private GpioPinDigitalInput input05;
	private GpioPinDigitalInput input06;

	// Music
	private final String SONG_01 = "standby.mp3";
	private final String SONG_02 = "coinInserted.mp3";
	private final String SONG_03 = "playing.mp3";
	private final String SONG_04 = "winner.mp3";
	private final String SONG_05 = "looser.mp3";

	private Soundman soundman;
	private SoundmanThread soundmanThread;

	private JList<String> list;
	private DefaultListModel<String> listModel;

	public static void main(String args[]) {

		new PiSoundAutomation();

	}

	public PiSoundAutomation() {

		initUi();
		initWalkman();
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

	private void initWalkman() {

		soundman = new Soundman();

		SoundmanThread soundmanThread = new SoundmanThread(soundman);

		Thread th = new Thread(soundmanThread);
		th.start();

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
		input06 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN);

		// set shutdown state for this input pin
		input00.setShutdownOptions(true);
		input01.setShutdownOptions(true);
		input02.setShutdownOptions(true);
		input03.setShutdownOptions(true);
		input04.setShutdownOptions(true);
		input05.setShutdownOptions(true);
		input06.setShutdownOptions(true);

		// create and register gpio pin listener
		input00.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					if (soundman.isPlay())
						soundman.stop();
					soundman.play(SONG_01);

				}

			}

		});

		input01.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					if (soundman.isPlay())
						soundman.stop();
					soundman.play(SONG_02);

				}

			}

		});
		input02.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					if (soundman.isPlay())
						soundman.stop();
					soundman.play(SONG_03);

				}

			}

		});
		input03.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					if (soundman.isPlay())
						soundman.stop();
					soundman.play(SONG_04);
				}

			}

		});
		input04.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					if (soundman.isPlay())
						soundman.stop();
					soundman.play(SONG_05);
				}

			}

		});

		input05.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					soundman.stop();
				}

			}

		});

		input06.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

				if (event.getState() == PinState.HIGH) {
					soundman.setRepeat(true);
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

}
