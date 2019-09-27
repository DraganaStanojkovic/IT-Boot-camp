package zavrsni_rad;

import java.util.ArrayList;
import java.util.HashMap;

public class GlavniProgram {

	public static void main(String[] args) {
		Recnik recnik = new Recnik("jdbc:sqlite:C:\\Users\\korisnik\\Desktop\\all\\Zavrsni rad\\Dictionary.db");
		recnik.connect();

		recnik.upisReciIzRecnika();

		Knjiga knjiga = new Knjiga("jdbc:sqlite:C:\\Users\\korisnik\\Desktop\\all\\Zavrsni rad\\Dictionary.db");

		knjiga.connect();

		knjiga.citanjeKnjige("knjiga");

		ArrayList<String> listaReciKojeNisuURecniku = knjiga.reciKojeNisuURecniku(recnik);

		// knjiga.tabelaReciKojihNemaURecniku(listaReciKojeNisuURecniku);

		HashMap<String, Integer> pojavljivanjeUKnjizi = recnik.isteReci(knjiga);

		for (String rec : pojavljivanjeUKnjizi.keySet()) {
			if (pojavljivanjeUKnjizi.get(rec) > 0) {

				System.out.println(rec + " , ponavljanja: " + pojavljivanjeUKnjizi.get(rec));
			}
		}

		knjiga.sortiratiPoVrednosti();

		ArrayList<String> listaRecnik = recnik.getListuReci();

		ArrayList<String> ukupnaLista = knjiga.reciKojeNisuURecniku(recnik);

		ukupnaLista.addAll(listaRecnik);

		Knjiga.ispisSortiranihReci((ArrayList<String>) ukupnaLista);

		recnik.disconnect();
		knjiga.disconnect();
	}
}
