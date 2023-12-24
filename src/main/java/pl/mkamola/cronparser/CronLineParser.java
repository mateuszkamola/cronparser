package pl.mkamola.cronparser;

import java.util.ArrayList;
import java.util.List;

/**
 * When parsing a line we assume grammar as follows:
 * <field> <field> <field> <field> <field> <command>
 * <command> ::= string
 * <field> ::= <cron_time> | <cron_time>,<field>
 * <cron_time> ::= <cron_time_value> | <cron_time_value>/<step>
 * <step> ::= number
 * <cron_time_value> ::= <range> | number | '*'
 * <range> ::= number-number
 * <p>
 * This approach allows for parsing semantically correct but nonsensical inputs like "1-5,1-5/5,*,*", but we leave it for user to provide
 * correct input and will try to work correctly with any data provided.
 */
public class CronLineParser {

	/**
	 * List of descriptors used to parse cron time fields.
	 */
	private final List<CronTimeDescriptor> descriptorList;

	public CronLineParser(List<CronTimeDescriptor> descriptorList) {
		this.descriptorList = descriptorList;
	}

	/**
	 * cronTimeStringList and descriptorList sizes should be the same. If they are not, the shorter list size is used to parse inputs.
	 *
	 * @param cronTimeStringList
	 * @param command
	 * @return
	 * @throws InvalidFormatException
	 */
	public CronLine parse(List<String> cronTimeStringList, String command)
			throws InvalidFormatException {
		List<CronTimeField> fields = new ArrayList<>();
		for (int i = 0; i < Math.min(descriptorList.size(), cronTimeStringList.size()); i++) {
			String cronTimeString = cronTimeStringList.get(i);
			CronTimeDescriptor descriptor = descriptorList.get(i);
			CronTimeField field = parseCronTimeString(descriptor, cronTimeString);
			fields.add(field);
		}
		return new CronLine(fields, command);
	}

	CronTimeField parseCronTimeString(CronTimeDescriptor descriptor, String input) throws InvalidFormatException {
		List<IntValueRange> rangeList = new ArrayList<>();
		for (String singleInput : input.split(",")) {
			rangeList.add(parseCronTimeValue(descriptor.getFirst(), descriptor.getLast(), singleInput));
		}
		return new CronTimeField(descriptor.getName(), rangeList);
	}

	private IntValueRange parseCronTimeValue(int first, int last, String value) throws InvalidFormatException {
		int step = 1;
		if (value.contains("/")) {
			String[] parts = value.split("/");
			value = parts[0];
			step = Integer.parseInt(parts[1]);
		}
		if (value.contains("-")) {
			return parseRangeCronTimeValue(first, last, step, value);
		}
		if (value.equals("*")) {
			return new IntValueRange(first, last, step);
		}
		int singleValue = Integer.parseInt(value);
		return new IntValueRange(singleValue);
	}

	private IntValueRange parseRangeCronTimeValue(int first, int last, int step, String value) throws InvalidFormatException {
		String[] parts = value.split("-");
		if (parts.length != 2) {
			throw new InvalidFormatException("Range operator must have two operands, first-last.");
		}
		int min = Integer.parseInt(parts[0]);
		int max = Integer.parseInt(parts[1]);
		first = Math.max(first, min);
		last = Math.min(last, max);
		return new IntValueRange(first, last, step);
	}

}

