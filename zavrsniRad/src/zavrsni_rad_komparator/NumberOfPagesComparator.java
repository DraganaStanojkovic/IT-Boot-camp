package zavrsni_rad_komparator;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class NumberOfPagesComparator implements Comparator<Map.Entry<String, Integer>> {
	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		if (o1.getValue() < o2.getValue()) {
			return -1;
		} else if (o1.getValue() == o2.getValue()) {
			return 0;
		}

		return 1;
	}
}