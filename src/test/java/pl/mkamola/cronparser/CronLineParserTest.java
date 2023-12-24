package pl.mkamola.cronparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class CronLineParserTest {

	@Test
	void parse_whenTwoDescriptors() throws InvalidFormatException {
		CronTimeDescriptor desc1 = new CronTimeDescriptor("test", 0, 59);
		CronTimeDescriptor desc2 = new CronTimeDescriptor("test2", 1, 12);
		CronLineParser parser = new CronLineParser(List.of(desc1, desc2));
		List<String> inputStrings = List.of("*/15,31", "1-6,9");
		String command = "testCommand withspace";
		CronLine line = parser.parse(inputStrings, command);
		String expected = "test          0 15 30 31 45\ntest2         1 2 3 4 5 6 9\ncommand       testCommand withspace";
		assertEquals(expected, line.toString());
	}

	@Test
	void parse_whenEmptyInputList() throws InvalidFormatException {
		CronTimeDescriptor desc1 = new CronTimeDescriptor("test", 0, 59);
		CronTimeDescriptor desc2 = new CronTimeDescriptor("test2", 1, 12);
		CronLineParser parser = new CronLineParser(List.of(desc1, desc2));
		List<String> inputStrings = List.of();
		String command = "testCommand withspace";
		CronLine line = parser.parse(inputStrings, command);
		String expected = "command       testCommand withspace";
		assertEquals(expected, line.toString());
	}

	@Test
	void parse_whenEmptyDescriptors() throws InvalidFormatException {
		CronLineParser parser = new CronLineParser(List.of());
		List<String> inputStrings = List.of("*/15,31", "1-6,9");
		String command = "testCommand withspace";
		CronLine line = parser.parse(inputStrings, command);
		String expected = "command       testCommand withspace";
		assertEquals(expected, line.toString());
	}

	@Test
	void parseCronTimeString_singleValue() throws InvalidFormatException {
		internalParseCronTimeString_3To10("8", List.of(8));
	}

	@Test
	void parseCronTimeString_singleValueOutsideBounds() throws InvalidFormatException {
		internalParseCronTimeString_3To10("12", List.of(12));
	}

	@Test
	void parseCronTimeString_rangeValue() throws InvalidFormatException {
		internalParseCronTimeString_3To10("3-5", List.of(3, 4, 5));
	}

	@Test
	void parseCronTimeString_someRangeValueOutsideBounds() throws InvalidFormatException {
		internalParseCronTimeString_3To10("8-12", List.of(8, 9, 10));
	}

	@Test
	void parseCronTimeString_allRangeValueOutsideBounds() throws InvalidFormatException {
		internalParseCronTimeString_3To10("12-14", List.of());
	}

	@Test
	void parseCronTimeString_asterisk() throws InvalidFormatException {
		internalParseCronTimeString_3To10("*", List.of(3, 4, 5, 6, 7, 8, 9, 10));
	}

	@Test
	void parseCronTimeString_asteriskStep5() throws InvalidFormatException {
		internalParseCronTimeString_3To10("*/5", List.of(3, 8));
	}

	@Test
	void parseCronTimeString_rangeStep3() throws InvalidFormatException {
		internalParseCronTimeString_3To10("4-15/3", List.of(4, 7, 10));
	}

	@Test
	void parseCronTimeString_listSingleValues() throws InvalidFormatException {
		internalParseCronTimeString_3To10("3,5,10,12", List.of(3, 5, 10, 12));
	}

	@Test
	void parseCronTimeString_listRangeAndSingleValue() throws InvalidFormatException {
		internalParseCronTimeString_3To10("3-5,7,8-10", List.of(3, 4, 5, 7, 8, 9, 10));
	}

	@Test
	void parseCronTimeString_listRangeAndAsterisk() throws InvalidFormatException {
		internalParseCronTimeString_3To10("3-5,*", List.of(3, 4, 5, 6, 7, 8, 9, 10));
	}

	@Test
	void parseCronTimeString_listRangeStepAndAsteriskStep() throws InvalidFormatException {
		internalParseCronTimeString_3To10("3-9/3,*/2", List.of(3, 5, 6, 7, 9));
	}

	@Test
	void parseCronTimeString_listRangeStepAndAsteriskStep_0To24() throws InvalidFormatException {
		internalParseCronTimeString_customRange(0, 24, "3-14/3,*/2",
				List.of(0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 16, 18, 20, 22, 24));
	}

	private void internalParseCronTimeString_3To10(String input, List<Integer> expected) throws InvalidFormatException {
		internalParseCronTimeString_customRange(3, 10, input, expected);
	}

	private void internalParseCronTimeString_customRange(int first, int last, String input, List<Integer> expected)
			throws InvalidFormatException {
		CronTimeDescriptor desc = new CronTimeDescriptor("test", first, last);
		CronLineParser parser = new CronLineParser(List.of());
		CronTimeField field = parser.parseCronTimeString(desc, input);
		assertEquals(expected, field.getValues());
	}
}