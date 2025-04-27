package unos;

import java.util.concurrent.atomic.AtomicInteger;

import test1.Hotel;
import test1.Rezervacija;

public class Data {

	private static final AtomicInteger id = new AtomicInteger();

	static String[][] hotelData = { { "Hotel Moskva", "4", "320", "15", "200", "Beograd" },
			{ "Hotel Metropol", "3", "420", "12", "300", "Beograd" },
			{ "Hotel Crowne Plaza", "5", "520", "10", "400", "Beograd" },
			{ "The Ritz London", "3", "320", "8", "250", "London" },
			{ "Hilton London Metropole", "4", "420", "18", "350", "London" },
			{ "Shangri-La Hotel", "5", "520", "16", "450", "London" },
			{ "Marina Bay Sands", "3", "320", "7", "300", "Singapur" },
			{ "Raffles Hotel", "4", "420", "11", "400", "Singapur" },
			{ "The Fullerton Hotel", "5", "520", "9", "500", "Singapur" },
			{ "Hotel Arts Barcelona", "3", "320", "13", "350", "Barcelona" },
			{ "W Barcelona", "4", "420", "20", "450", "Barcelona" },
			{ "Mandarin Oriental", "5", "520", "14", "550", "Barcelona" },
			{ "Four Seasons Hotel Istanbul", "3", "320", "19", "400", "Istanbul" },
			{ "Swissotel The Bosphorus", "4", "420", "6", "500", "Istanbul" },
			{ "Çırağan Palace Kempinski", "5", "520", "17", "600", "Istanbul" },
			{ "The Peninsula Beijing", "3", "320", "5", "450", "Peking" },
			{ "Grand Hyatt Beijing", "4", "420", "12", "550", "Peking" },
			{ "Waldorf Astoria Beijing", "5", "520", "16", "650", "Peking" },
			{ "Hotel Chinzanso Tokyo", "3", "320", "9", "500", "Tokyo" },
			{ "The Tokyo Station Hotel", "4", "420", "11", "600", "Tokyo" } };

	public static Hotel[] initHotel() {
		Hotel[] hot = new Hotel[hotelData.length];
		int i = 0;

		for (String[] data : hotelData) {
			Hotel h = Hotel.newBuilder().setIme(data[0]).setZvezdice(Integer.valueOf(data[1]))
					.setUdaljenostiOdCentra(Integer.valueOf(data[2])).setBrojSlobodnihMesta(Integer.valueOf(data[3]))
					.setTrenutnaCena(Integer.valueOf(data[4])).setGrad(data[5]).setId(id.incrementAndGet())
					.setInterniRacun(0).setProvizniRacun(0).build();
			hot[i++] = h;

		}

		return hot;
	}

	public static Rezervacija[] initRezervacija() {
		Rezervacija[] hot = new Rezervacija[hotelData.length];

		return hot;
	}

}
