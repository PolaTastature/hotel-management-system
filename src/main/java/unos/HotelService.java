package unos;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import test1.Hotel;
import test1.Rezervacija;

public class HotelService {

	public final static ConcurrentHashMap<String, Hotel> hotelMap = new ConcurrentHashMap<String, Hotel>();
	public final static ConcurrentHashMap<Integer, Rezervacija> rezervacijaMapa = new ConcurrentHashMap<Integer, Rezervacija>();
	private static final AtomicInteger idRezervacija = new AtomicInteger();

	public HotelService() {

		init();
	}

	protected void init() {

		for (Hotel h : Data.initHotel()) {
			hotelMap.put(h.getIme(), h);
		}
	}

	public static ConcurrentHashMap<String, Hotel> getHotelMap() {
		if (hotelMap == null)
			return null;
		return hotelMap;
	}

	public ConcurrentHashMap<Integer, Rezervacija> getRezervacijaMap() {
		if (rezervacijaMapa == null)
			return null;
		return rezervacijaMapa;
	}

	public synchronized void smanjiBrojSlobodnihMesta(String imeHotela, int broj) {
		Hotel hotel = hotelMap.get(imeHotela);
		if (hotel != null) {
			hotel.toBuilder().setBrojSlobodnihMesta(hotelMap.get(imeHotela).getBrojSlobodnihMesta() - 1).build();
			int brojslobodnigMests = hotel.getBrojSlobodnihMesta();
			if (brojslobodnigMests < 4 && brojslobodnigMests == 3) {
				hotel.toBuilder().setTrenutnaCena(hotel.getTrenutnaCena() * 125 / 100);

			} else if (brojslobodnigMests >= 4 && brojslobodnigMests == 4) {

				hotel.toBuilder().setTrenutnaCena(hotel.getTrenutnaCena() * 75 / 100);

			}
			hotelMap.remove(hotelMap.get(imeHotela));
			hotelMap.put(imeHotela, hotel);
		} else {
			System.out.println("Hotel " + imeHotela + " ne postoji.");
		}
	}

	public synchronized void povecajBrojSlobodnihMesta(String imeHotela, int broj) {
		Hotel hotel = hotelMap.get(imeHotela);
		if (hotel != null) {
			hotel.toBuilder().setBrojSlobodnihMesta(hotelMap.get(imeHotela).getBrojSlobodnihMesta() + 1).build();
			int brojslobodnigMests = hotel.getBrojSlobodnihMesta();
			if (brojslobodnigMests < 4 && brojslobodnigMests == 3) {
				hotel.toBuilder().setTrenutnaCena(hotel.getTrenutnaCena() * 125 / 100);

			} else if (brojslobodnigMests >= 4 && brojslobodnigMests == 4) {

				hotel.toBuilder().setTrenutnaCena(hotel.getTrenutnaCena() * 75 / 100);

			}
			hotelMap.remove(hotelMap.get(imeHotela));
			hotelMap.put(imeHotela, hotel);
		} else {
			System.out.println("Hotel " + imeHotela + " ne postoji.");
		}
	}

	public Rezervacija napraviRezervaciju(Hotel hotel, long pocetak, int brojDana) {

		Integer resId = idRezervacija.getAndIncrement();

		smanjiBrojSlobodnihMesta(hotel.getIme(), brojDana);
		if (hotel.getBrojSlobodnihMesta() == 0)
			return null;
		Rezervacija rezervacija = Rezervacija.newBuilder().setId(resId).setHotel(hotel).setPocetakBoravka(pocetak)
				.setBrojDana(brojDana).setRokZaPlacanje(pocetak + 10000).build();

		rezervacijaMapa.put(resId, rezervacija);
		return rezervacija;

	}
}
