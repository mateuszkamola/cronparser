package pl.mkamola.cronparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents integer range of values. Starting with first, every step values, until value larger than last is reached.
 * Formula for values: [first, first + step, first + step * 2, ..., first + step * n]; for n where first + step * (n + 1) > last.
 */
public class IntValueRange {

	private final int first;
	private final int last;
	private final int step;

	public IntValueRange(int singleValue) {
		first = singleValue;
		last = singleValue;
		step = 1;
	}

	public IntValueRange(int first, int last, int step) {
		this.first = first;
		this.last = last;
		this.step = step;
	}

	public List<Integer> getValues() {
		List<Integer> result = new ArrayList<>();
		for (int i = first; i <= last; i += step) {
			result.add(i);
		}
		return result;
	}
}
