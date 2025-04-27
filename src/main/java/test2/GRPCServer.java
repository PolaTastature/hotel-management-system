package test2;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import poruke.ChatMessage;
import poruke.InfoMessage;
import poruke.KryoUtil;
import poruke.ListUsers;
import poruke.Login;
import poruke.WhoRequest;
import test1.Hotel;
import test1.HotelServiceGrpc.HotelServiceImplBase;
import test1.Rezervacija;
import test1.RezervacijaInfo;
import test1.UpitZaHotele;
import unos.HotelService;

public class GRPCServer extends HotelServiceImplBase implements Runnable {
	private ExecutorService executor = Executors.newSingleThreadExecutor();;

	HotelService hotelService;
	private volatile Thread thread = null;

	volatile boolean running = false;
	com.esotericsoftware.kryonet.Server socketServer;
	int portNumber;
	ConcurrentMap<String, Connection> userConnectionMap = new ConcurrentHashMap<String, Connection>();
	ConcurrentMap<Connection, String> connectionUserMap = new ConcurrentHashMap<Connection, String>();

	protected GRPCServer(HotelService hs) {

		this.hotelService = hs;

	}

	public GRPCServer() {

		this.socketServer = new com.esotericsoftware.kryonet.Server();

		this.portNumber = 54555;
		KryoUtil.registerKryoClasses(socketServer.getKryo());
		registerListener();
	}

	private void registerListener() {
		socketServer.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof Login) {
					Login login = (Login) object;
					newUserLogged(login, connection);
					connection.sendTCP(new InfoMessage("Hello " + login.getUserName()));
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}

				if (object instanceof ChatMessage) {
					ChatMessage chatMessage = (ChatMessage) object;
					System.out.println(chatMessage.getUser() + ":" + chatMessage.getTxt());
					broadcastChatMessage(chatMessage, connection);
					return;
				}

				if (object instanceof WhoRequest) {
					ListUsers listUsers = new ListUsers(getAllUsers());
					connection.sendTCP(listUsers);
					return;
				}
			}

			public void disconnected(Connection connection) {
				String user = connectionUserMap.get(connection);
				connectionUserMap.remove(connection);
				userConnectionMap.remove(user);
				showTextToAll(user + " has disconnected!", connection);
			}
		});
	}

	String[] getAllUsers() {
		String[] users = new String[userConnectionMap.size()];
		int i = 0;
		for (String user : userConnectionMap.keySet()) {
			users[i] = user;
			i++;
		}

		return users;
	}

	void newUserLogged(Login loginMessage, Connection conn) {
		userConnectionMap.put(loginMessage.getUserName(), conn);
		connectionUserMap.put(conn, loginMessage.getUserName());
		showTextToAll("User " + loginMessage.getUserName() + " has connected!", conn);
	}

	private void broadcastChatMessage(ChatMessage message, Connection exception) {
		for (Connection conn : userConnectionMap.values()) {
			if (conn.isConnected() && conn != exception)
				conn.sendTCP(message);
		}
	}

	private void showTextToAll(String txt, Connection exception) {
		System.out.println(txt);
		for (Connection conn : userConnectionMap.values()) {
			if (conn.isConnected() && conn != exception)
				conn.sendTCP(new InfoMessage(txt));
		}
	}

	public void start() throws IOException {
		socketServer.start();
		socketServer.bind(portNumber);

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

	@Override
	public void run() {
		running = true;

		while (running) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void upit(UpitZaHotele request, StreamObserver<Hotel> responseObserver) {

		String grad = request.getGrad();
		Integer maxU = request.getMaxUdaljenost();
		Integer zveMin = request.getMinZvezde();

		for (String hotelIme : HotelService.hotelMap.keySet()) {
			Hotel hotel = HotelService.hotelMap.get(hotelIme);

			// Provera uslova
			if (hotel != null && hotel.getGrad().equals(grad) && hotel.getUdaljenostiOdCentra() <= maxU
					&& hotel.getZvezdice() >= zveMin) {
				System.out.println(hotel.toString());

			}

		}

		responseObserver.onCompleted();
	}

	@Override
	public void rezervisi(RezervacijaInfo request, StreamObserver<Rezervacija> responseObserver) {

		Runnable reservationTask = () -> {
			Hotel hotel = request.getHotel();
			long pocetakBoravka = request.getPocetakBoravka();
			int brojDana = request.getBrojDana();

			Rezervacija rez = hotelService.napraviRezervaciju(hotel, pocetakBoravka, brojDana);

			responseObserver.onNext(rez);
			responseObserver.onCompleted();
		};

		executor.submit(reservationTask);

	}

	public static void main(String[] args) throws IOException, InterruptedException {

		HotelService hs = new HotelService();

		Server server = ServerBuilder.forPort(8090).addService(new GRPCServer(hs)).build();

		server.start();
		System.out.println("Server started on port 8090 gRPC");
		try {
			GRPCServer chatServer = new GRPCServer();
			chatServer.start();

			chatServer.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		server.awaitTermination();
	}

}
