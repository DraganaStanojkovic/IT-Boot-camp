package zavrsni_rad_komparator;

import java.util.Comparator;

public class LexographicComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		return o1.compareToIgnoreCase(o2);
	}
}
