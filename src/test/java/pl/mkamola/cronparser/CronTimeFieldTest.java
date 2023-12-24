package pl.mkamola.cronparser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;

class CronTimeFieldTest {

	@Test
	void getValues_correctSingleTimeValue() {
		IntValueRange rangeMock = mock(IntValueRange.class);
		when(rangeMock.getValues()).thenReturn(List.of(1,2,3));
		CronTimeField field = new CronTimeField("test", List.of(rangeMock));
		List<Integer> expected = List.of(1,2,3);
		assertEquals(expected, field.getValues());
	}

	@Test
	void getValues_correctMergeMultipleTimeValues() {
		IntValueRange rangeMock1 = mock(IntValueRange.class);
		when(rangeMock1.getValues()).thenReturn(List.of(1,2,3));
		IntValueRange rangeMock2 = mock(IntValueRange.class);
		when(rangeMock2.getValues()).thenReturn(List.of(2,3,4,5,10));
		IntValueRange rangeMock3 = mock(IntValueRange.class);
		when(rangeMock3.getValues()).thenReturn(List.of(10));
		CronTimeField ctd = new CronTimeField("test", List.of(rangeMock1, rangeMock2, rangeMock3));
		List<Integer> expected = List.of(1,2,3,4,5,10);
		assertEquals(expected, ctd.getValues());
	}

	@Test
	void getValues_noTimeValues() {
		CronTimeField field = new CronTimeField("test", List.of());
		List<Integer> expected = List.of();
		assertEquals(expected, field.getValues());
	}
}