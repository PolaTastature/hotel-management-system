package test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import poruke.ChatMessage;
import poruke.InfoMessage;
import poruke.KryoUtil;
import poruke.ListUsers;
import poruke.Login;
import poruke.WhoRequest;
import test1.Hotel;
import test1.HotelServiceGrpc;
import test1.Rezervacija;
import test1.RezervacijaInfo;
import test1.UpitZaHotele;
import unos.HotelService;

public class GRPCKlijent implements Runnable {

	public static int DEFAULT_CLIENT_READ_BUFFER_SIZE = 1000000;
	public static int DEFAULT_CLIENT_WRITE_BUFFER_SIZE = 1000000;

	private ManagedChannel channel;
	private HotelServiceGrpc.HotelServiceBlockingStub blockingStub;
	private HotelServiceGrpc.HotelServiceStub asyncStub;

	private volatile Thread thread = null;
	volatile boolean running = false;
	double novac = 1000;
	final Client client;
	final String hostName;
	final int portNumber;
	final String userName;
	static Scanner sc = new Scanner(System.in);

	public GRPCKlijent(String userName) {
		this.client = new Client(DEFAULT_CLIENT_WRITE_BUFFER_SIZE, DEFAULT_CLIENT_READ_BUFFER_SIZE);

		this.hostName = "localhost";
		this.portNumber = 54555;
		this.userName = userName;

		this.channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
		this.asyncStub = HotelServiceGrpc.newStub(channel);
		this.blockingStub = HotelServiceGrpc.newBlockingStub(channel);

		blockingStub = HotelServiceGrpc.newBlockingStub(channel);

		KryoUtil.registerKryoClasses(client.getKryo());
		registerListener();
	}

	public void upit() {

		System.out.println("Grad ,max udaljenost i min kategorije");
		String[] ulaz = sc.nextLine().split(" ");

		UpitZaHotele request = UpitZaHotele.newBuilder().setGrad(ulaz[0]).setMaxUdaljenost(Integer.valueOf(ulaz[1]))
				.setMinZvezde(Integer.valueOf(ulaz[2])).build();

		Iterator<Hotel> hoteli = blockingStub.upit(request);
		System.out.println(request.getGrad() + " " + request.getMaxUdaljenost() + " " + request.getMinZvezde());
		try {
			while (hoteli.hasNext()) {
				Hotel h = hoteli.next();
				ispisHotela(h);
			}
		} catch (StatusRuntimeException e) {
			System.err.println(e.getStatus());
		}

	}

	private static void ispisHotela(Hotel h) {
		System.out.println(h.getId() + " " + h.getIme() + " " + h.getUdaljenostiOdCentra() + " " + h.getZvezdice());

	}

	private static void PlatiIliOtkazi() {
		final CountDownLatch finishLatch = new CountDownLatch(1);
		new StreamObserver<Hotel>() {

			@Override
			public void onNext(Hotel value) {

			}

			@Override
			public void onError(Throwable t) {
				Status status = Status.fromThrowable(t);
				System.err.println("greska:" + status);
				finishLatch.countDown();

			}

			@Override
			public void onCompleted() {
				finishLatch.countDown();

			}
		};
		new StreamObserver<RezervacijaInfo>() {

			@Override
			public void onNext(RezervacijaInfo value) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable t) {
				Status status = Status.fromThrowable(t);
				System.err.println("greska:" + status);
				finishLatch.countDown();

			}

			@Override
			public void onCompleted() {
				finishLatch.countDown();

			}
		};

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

			while (running) {
				userInput = stdIn.readLine();
				if (userInput == null || "BYE".equalsIgnoreCase(userInput)) // userInput - tekst koji je unet sa
																			// tastature!
				{
					running = false;
				} else if ("WHO".equalsIgnoreCase(userInput)) {
					client.sendTCP(new WhoRequest());
				} else if ("upit".equalsIgnoreCase(userInput)) {
					upit();
				} else if ("rezervisi".equalsIgnoreCase(userInput)) {

					rezervisi();
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

		}
	}

	private void rezervisi() {
		System.out.println("ime hotela");
		String imeHotela = sc.nextLine();
		System.out.println("kad pocinje");
		long pocetak = sc.nextLong();
		System.out.println("koliko dana");
		int brojDana = sc.nextInt();
		RezervacijaInfo request = RezervacijaInfo.newBuilder().setHotel(HotelService.hotelMap.get(imeHotela))
				.setPocetakBoravka(pocetak).setBrojDana(brojDana).build();
		Rezervacija rezervacija = blockingStub.rezervisi(request);
		if (rezervacija == null) {
			System.out.println("nema slobodnih mesta");
		}
		System.out.println("ako se ne plati uskoro puca rezervacija");

	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println("userName");
		String userName = sc.nextLine();

		try {
			GRPCKlijent chatClient = new GRPCKlijent(userName);
			chatClient.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error:" + e.getMessage());
			System.exit(-1);
		}
		Thread th = new Thread();
		th.start();

	}

}
