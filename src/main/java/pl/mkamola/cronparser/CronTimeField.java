package pl.mkamola.cronparser;

import java.util.List;
import java.util.TreeSet;

/**
 * Represents cron time field after parsing input string.
 */
public class CronTimeField {

	private final String label;
	private final List<IntValueRange> intValueRangeList;

	public CronTimeField(String label, List<IntValueRange> intValueRangeList) {
		this.label = label;
		this.intValueRangeList = intValueRangeList;
	}

	public String getLabel() {
		return label;
	}

	public List<Integer> getValues() {
		TreeSet<Integer> treeSet = new TreeSet<>();
		for (IntValueRange v : intValueRangeList) {
			treeSet.addAll(v.getValues());
		}
		return treeSet.stream().toList();
	}
}
