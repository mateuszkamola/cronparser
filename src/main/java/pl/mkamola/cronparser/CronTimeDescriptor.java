package pl.mkamola.cronparser;

/**
 * Describes cron time field. Gives the name that will be used when printing information about cron line and range for values.
 * First and last values are inclusive - they will be part of resulting values it they are not filtered by cron time string.
 */
public class CronTimeDescriptor {

	/**
	 * Name of the field.
	 */
	private final String name;
	/**
	 * Smallest value allowed for this field, inclusive. ie. 0 for minutes, 1 for months
	 */
	private final int first;
	/**
	 * Largest value allowed for this field, inclusive. ie. 59 for minutes, 12 for months
	 */
	private final int last;

	public CronTimeDescriptor(String name, int first, int last) {
		this.name = name;
		this.first = first;
		this.last = last;
	}

	public String getName() {
		return name;
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}
}
