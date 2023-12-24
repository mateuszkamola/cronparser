package pl.mkamola.cronparser;

import java.util.List;
import java.util.stream.Collectors;

public class CronLine {

	private final List<CronTimeField> cronTimeFieldList;
	private final String command;

	public CronLine(List<CronTimeField> cronTimeFieldList, String command) {
		this.cronTimeFieldList = cronTimeFieldList;
		this.command = command;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (CronTimeField desc : cronTimeFieldList) {
			sb.append(String.format("%-14s%s%n", desc.getLabel(), desc.getValues().stream()
					.map(String::valueOf)
					.collect(Collectors.joining(" "))));
		}
		sb.append(String.format("%-14s%s", "command", command));
		return sb.toString();
	}
}
