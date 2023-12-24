package pl.mkamola.cronparser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class IntValueRangeTest {

	@Test
	public void getValues_correctStep1() {
		IntValueRange range = new IntValueRange(10, 20, 1);
		List<Integer> expected = List.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
		assertEquals(expected, range.getValues());
	}

	@Test
	public void getValues_correctStep7() {
		IntValueRange range = new IntValueRange(4, 19, 7);
		List<Integer> expected = List.of(4, 11, 18);
		assertEquals(expected, range.getValues());
	}

	@Test
	public void getValues_firstLowerThanLast() {
		IntValueRange range = new IntValueRange(3, 1, 1);
		List<Integer> expected = List.of();
		assertEquals(expected, range.getValues());
	}

	@Test
	public void getValues_singleValue() {
		IntValueRange range = new IntValueRange(5, 5, 1);
		List<Integer> expected = List.of(5);
		assertEquals(expected, range.getValues());
	}

}