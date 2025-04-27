package test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.common.util.concurrent.AtomicDouble;

import poruke.ChatMessage;
import poruke.InfoMessage;
import poruke.KryoUtil;
import poruke.ListUsers;
import poruke.Login;
import poruke.WhoRequest;
import test1.Hotel;
import unos.HotelService;

public class SistemHotela implements Runnable {
	public static int DEFAULT_CLIENT_READ_BUFFER_SIZE = 1000000;
	public static int DEFAULT_CLIENT_WRITE_BUFFER_SIZE = 1000000;
	final Client client;
	final String hostName;
	final int portNumber;
	final String userName;
	private volatile Thread thread = null;

	private final Lock lock = new ReentrantLock();

	private final Hotel izabraniHotel;

	private AtomicInteger slobodneSobe;
	private AtomicDouble trenutnaCena;

	private AtomicInteger brojPoslovanja;
	private AtomicInteger brojNocenja;

	private AtomicDouble interniRacun;
	private AtomicDouble provizniRacun;

	volatile boolean running = false;
	private boolean up = false;

	public SistemHotela(Hotel hotel) {

		this.client = new Client(DEFAULT_CLIENT_WRITE_BUFFER_SIZE, DEFAULT_CLIENT_READ_BUFFER_SIZE);
		izabraniHotel = hotel;
		slobodneSobe = new AtomicInteger(hotel.getBrojSlobodnihMesta());
		trenutnaCena = new AtomicDouble(hotel.getTrenutnaCena());
		interniRacun = new AtomicDouble(0);
		provizniRacun = new AtomicDouble(0);
		this.hostName = "localhost";
		this.portNumber = 54555;
		this.userName = hotel.getIme();
		KryoUtil.registerKryoClasses(client.getKryo());
		registerListener();
		obavestenje();
	}

	public AtomicInteger getSlobodneSobe() {
		if (slobodneSobe.intValue() < 0)
			slobodneSobe.set(0);

		return slobodneSobe;
	}

	public void stanjeHotela() {

		System.out.println("brojPoslovanja= " + brojPoslovanja + " brojNocenja= " + brojNocenja + " brojSlobodnihMesta"
				+ slobodneSobe);

	}

	public void setSlobodneSobe(AtomicInteger slobodneSobe) {
		this.slobodneSobe = slobodneSobe;
	}

	public AtomicDouble getTrenutnaCena() {
		return trenutnaCena;
	}

	public void setTrenutnaCena(AtomicDouble trenutnaCena) {
		this.trenutnaCena = trenutnaCena;
	}

	public AtomicDouble getInterniRacun() {
		return interniRacun;
	}

	public void setInterniRacun(AtomicDouble interniRacun) {
		this.interniRacun = interniRacun;
	}

	public AtomicDouble getProvizniRacun() {
		return provizniRacun;
	}

	public void setProvizniRacun(AtomicDouble provizniRacun) {
		this.provizniRacun = provizniRacun;
	}

	private void obavestenje() {

		InfoMessage info = new InfoMessage(izabraniHotel.toString());

		lock.lock();
		try {
			client.sendTCP(info);
		} finally {
			lock.unlock();
		}
	}

	public void updateInterniRacun(double amount) {
		synchronized (lock) {
			interniRacun.addAndGet(amount);
			up = true;
			lock.notify();
		}
	}

	private void registerListener() {
		client.addListener(new Listener() {
			public void connected(Connection connection) {
				Login loginMessage = new Login(userName);
				client.sendTCP(loginMessage);
			}

			public void received(Connection connection, Object object) {
				if (object instanceof ListUsers) {
					ListUsers listUsers = (ListUsers) object;
					showOnlineUsers(listUsers.getUsers());
					return;
				}

				if (object instanceof InfoMessage) {
					InfoMessage message = (InfoMessage) object;
					showMessage("Server:" + message.getTxt());
					return;
				}

				if (object instanceof ChatMessage) {
					ChatMessage message = (ChatMessage) object;
					showMessage(message.getUser() + "r:" + message.getTxt());
					return;
				}
			}

			public void disconnected(Connection connection) {

			}
		});
	}

	private void showChatMessage(ChatMessage chatMessage) {
		System.out.println(chatMessage.getUser() + ":" + chatMessage.getTxt());
	}

	private void showMessage(String txt) {
		System.out.println(txt);
	}

	private void showOnlineUsers(String[] users) {
		System.out.print("Server:");
		for (int i = 0; i < users.length; i++) {
			String user = users[i];
			System.out.print(user);
			System.out.printf((i == users.length - 1 ? "\n" : ", "));
		}
	}

	public void start() throws IOException {
		client.start();
		connect();

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stop() {
		Thread stopThread = thread;
		thread = null;
		running = false;
		if (stopThread != null)
			stopThread.interrupt();
	}

	public void connect() throws IOException {
		client.connect(1000, hostName, portNumber);
	}

	public void run() {

		try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)) // Za čitanje sa standardnog
																							// ulaza - tastature!
		) {

			String userInput;
			running = true;
			System.out.println("1 stanje hotela,2 smanji broj slobodnih soba,3 povecaj broj slobodnih soba,");
			while (running) {
				userInput = stdIn.readLine();
				synchronized (lock) {
					try {
						while (!up) {
							lock.wait();
						}
						upMessage();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				if (userInput == null || "BYE".equalsIgnoreCase(userInput)) // userInput - tekst koji je unet sa
																			// tastature!
				{
					running = false;
				} else if ("WHO".equalsIgnoreCase(userInput)) {
					client.sendTCP(new WhoRequest());
				} else if ("1".equalsIgnoreCase(userInput)) {

					stanjeHotela();
				} else if ("2".equalsIgnoreCase(userInput)) {
					slobodneSobe.decrementAndGet();

				} else if ("3".equalsIgnoreCase(userInput)) {

					slobodneSobe.incrementAndGet();
				} else {
					ChatMessage message = new ChatMessage(userName, userInput);
					client.sendTCP(message);
				}

				if (!client.isConnected() && running)
					connect();

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			running = false;
			System.out.println("CLIENT SE DISCONNECTUJE");
			client.close();
			;
		}
	}

	private void upMessage() {

		InfoMessage upgradeHotel = new InfoMessage(izabraniHotel.getIme() + " je promenio nesto");

		HotelService.hotelMap.get(izabraniHotel.getIme()).newBuilder().setBrojSlobodnihMesta(slobodneSobe.intValue())
				.setTrenutnaCena(trenutnaCena.intValue()).setInterniRacun(interniRacun.intValue())
				.setProvizniRacun(provizniRacun.intValue()).build();
		synchronized (lock) {

			client.sendTCP(upgradeHotel);
			up = false;
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Unesi ime hotela");

		SistemHotela sh = new SistemHotela(HotelService.hotelMap.get(new Scanner(System.in).nextLine()));
		sh.start();

	}

}
