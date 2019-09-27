package zavrsni_rad;

import java.sql.*;
import java.util.*;

public class Recnik {
	String connectionString;
	Connection con;
	HashMap<String, Rec> recnik = new HashMap<String, Rec>();

	public Recnik(String conStr) {
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

	public HashMap<String, Rec> getRecnik() {
		return recnik;
	}

	public void upisReciIzRecnika() {
		try {
			String upit = " SELECT *\r\n" + " FROM entries";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(upit);

			while (rs.next()) {

				String word = rs.getString("word");
				String wordtype = rs.getString("wordtype");
				String definition = rs.getString("definition");

				recnik.put(word.toLowerCase(), new Rec(word, wordtype, definition));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> isteReci(Knjiga knjiga) {
		HashMap<String, Integer> rezultat = new HashMap<String, Integer>();
		for (String rec : recnik.keySet()) {
			if (knjiga.getReci().containsKey(rec)) {
				rezultat.put(rec, knjiga.getReci().get(rec));
			} else {
				rezultat.put(rec, 0);
			}
		}

		return rezultat;
	}

	public ArrayList<String> getListuReci() {
		ArrayList<String> lista = new ArrayList<>();
		for (String rec : recnik.keySet()) {
			lista.add(rec);
		}
		return lista;
	}

}
