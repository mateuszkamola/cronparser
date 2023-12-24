package pl.mkamola.cronparser;

import java.util.List;

public class CronParser {

	public static void main(String[] args) {
		if (args.length != 1) {
			printUsage();
			System.exit(1);
		}
		try {
			CronLine cronLine = new CronParser().parse(args[0]);
			System.out.println(cronLine);
			System.exit(0);
		} catch (InvalidFormatException | NumberFormatException e) {
			System.err.println(e.getMessage());
			printUsage();
			System.exit(2);
		}
	}

	public static void printUsage() {
		System.out.printf("Usage:%n\tjava -jar cronparser.jar \"* * * * * /bin/ls -l\"%n");
	}

	public CronLine parse(String inputLine) throws InvalidFormatException {
		String[] input = inputLine.split(" ", 6);
		if (input.length != 6) {
			throw new InvalidFormatException("Error while parsing input line. Input line must have 6 values separated by space.");
		}
		List<CronTimeDescriptor> descriptorList = List.of(
				new CronTimeDescriptor("minute", 0, 59),
				new CronTimeDescriptor("hour", 0, 59),
				new CronTimeDescriptor("day of month", 1, 31),
				new CronTimeDescriptor("month", 1, 12),
				new CronTimeDescriptor("day of week", 0, 6)
		);
		CronLineParser parser = new CronLineParser(descriptorList);
		List<String> cronTimeStringList = List.of(input[0], input[1], input[2], input[3], input[4]);
		String command = input[5];
		return parser.parse(cronTimeStringList, command);
	}


}