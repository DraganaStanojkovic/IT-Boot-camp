package zavrsni_rad;

import java.io.*;
import java.sql.*;
import java.util.*;
import zavrsni_rad_komparator.LexographicComparator;
import zavrsni_rad_komparator.NumberOfPagesComparator;

public class Knjiga {
	String connectionString;
	Connection con;
	HashMap<String, Integer> reci = new HashMap<>();

	public Knjiga(String conStr) {
		connectionString = conStr;
	}

	public void connect() {
		try {
			con = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> getReci() {
		return reci;
	}

	public void citanjeKnjige(String tekst) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("knjiga"));

			String linija = br.readLine();
			while (linija != null) {
				if (linija.isEmpty()) {
					linija = br.readLine();
					continue;
				}
				linija = linija.replaceAll("[^A-Za-z\' '-]", " ");
				linija = linija.replace("'", "");

				String[] reciULiniji = linija.split("\\s+");

				if (reciULiniji.length == 0) {
					linija = br.readLine();
					continue;
				}
				if (nastavakReci(reciULiniji)) {
					reciULiniji = spajanjeLinije(reciULiniji, br);
				}

				for (int i = 0; i < reciULiniji.length; i++) {
					if (reci.containsKey(reciULiniji[i])) {
						int brPonavljanja = reci.get(reciULiniji[i]);
						brPonavljanja++;
						reci.put(reciULiniji[i], brPonavljanja);
					} else {
						reci.put(reciULiniji[i], 1);
					}
				}
				linija = br.readLine();
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean nastavakReci(String[] reciULiniji) {
		return reciULiniji[reciULiniji.length - 1].charAt(reciULiniji[reciULiniji.length - 1].length() - 1) == '-';
	}

	private String[] spajanjeLinije(String[] reciULiniji, BufferedReader br) throws IOException {
		String polaReci = reciULiniji[reciULiniji.length - 1];
		polaReci = polaReci.replace("-", "");
		reciULiniji[reciULiniji.length - 1] = "";
		String novaLinija = br.readLine();
		novaLinija = novaLinija.replaceAll("[^A-Za-z\' '-]", "");
		String[] reciUNovojLiniji = novaLinija.split("\\s+");
		polaReci = polaReci + reciUNovojLiniji[0];
		polaReci = polaReci.replaceAll("[^A-Za-z\' '-]", "");
		reciUNovojLiniji[0] = polaReci;
		reciULiniji = spojiNizove(reciULiniji, reciUNovojLiniji);
		if (nastavakReci(reciULiniji)) {
			return spajanjeLinije(reciULiniji, br);
		}
		return reciULiniji;
	}

	private String[] spojiNizove(String[] niz1, String[] niz2) {
		String[] rezultat = new String[niz1.length + niz2.length];
		for (int i = 0; i < niz1.length; i++) {
			rezultat[i] = niz1[i];
		}
		int brojac = niz1.length;
		for (int j = 0; j < niz2.length; j++) {
			rezultat[brojac] = niz2[j];
			brojac++;
		}
		return rezultat;
	}

	public ArrayList<String> reciKojeNisuURecniku(Recnik recnik) {
		ArrayList<String> rezultat = new ArrayList<String>();
		for (String rec : reci.keySet()) {
			if (!recnik.getRecnik().containsKey(rec.toLowerCase())) {
				rezultat.add(rec);
			}
		}
		return rezultat;
	}

	public void tabelaReciKojihNemaURecniku(ArrayList<String> listaReciKojeNisuURecniku) {
		try {
			String upit = "CREATE TABLE reci_iz_knjige (reci varchar(25) NOT NULL COLLATE NOCASE)";
			Statement stm = con.createStatement();
			stm.executeUpdate(upit);
			for (String rec : listaReciKojeNisuURecniku) {
				rec = rec.replace("'", "");
				String insertUpit = "INSERT INTO reci_iz_knjige (reci) VALUES ('" + rec + "')";
				Statement stmUU = con.createStatement();
				stmUU.executeUpdate(insertUpit);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sortiratiPoVrednosti() {
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(reci.entrySet());
		Collections.sort(list, new NumberOfPagesComparator());

		for (int i = list.size() - 1; i > list.size() - 21; i--) {
			System.out.println(list.get(i));
		}
	}

	public static void ispisSortiranihReci(ArrayList<String> ukupnaLista) {
		Collections.sort(ukupnaLista, new LexographicComparator());
		ukupnaLista.sort(new LexographicComparator());

		try {
			FileWriter fw = new FileWriter("reciRecnikKnjiga");
			for (String s : ukupnaLista) {
				fw.write(s.toString() + "\n");
			}
			fw.flush();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
