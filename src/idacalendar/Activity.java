package idacalendar;

/**
 * Activity represents an activity that can be added to a day in the calendar.
 * It is possible to change the starting and ending times, title, text and place
 * of the activity.
 * 
 * @author Isak Stensö
 * @version 2014-05-02
 */

public class Activity implements Comparable<Activity> {
	private final int YEAR;
	private final int MONTH;
	private final int DAY;
	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;
	private String title;
	private String text;
	private String place;

	public Activity(int year, int month, int day, int startHour,
			int startMinute, int endHour, int endMinute, String title,
			String text, String place) {
		YEAR = year;
		MONTH = month;
		DAY = day;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.title = title;
		this.text = text;
		this.place = place;
	}

	/**
	 * Changes the current starting hour to a new one.
	 * 
	 * @param newStartHour
	 *            The new starting hour.
	 */
	public void changeStartHour(int newStartHour) {
		startHour = newStartHour;
	}

	/**
	 * Changes the current starting minute to a new one.
	 * 
	 * @param newStartMinute
	 *            The new starting minute.
	 */
	public void changeStartMinute(int newStartMinute) {
		startMinute = newStartMinute;
	}

	/**
	 * Changes the current ending hour to a new one.
	 * 
	 * @param newEndHour
	 *            The new ending hour.
	 */
	public void changeEndHour(int newEndHour) {
		endHour = newEndHour;
	}

	/**
	 * Changes the current ending minute to a new one.
	 * 
	 * @param newEndMinute
	 *            The new ending minute.
	 */
	public void changeEndMinute(int newEndMinute) {
		endMinute = newEndMinute;
	}

	/**
	 * Changes the current title to a new one.
	 * 
	 * @param newTitle
	 *            The new title.
	 */
	public void changeTitle(String newTitle) {
		title = newTitle;
	}

	/**
	 * Changes the current text to a new one.
	 * 
	 * @param newText
	 *            The new text.
	 */
	public void changeText(String newText) {
		text = newText;
	}

	/**
	 * Changes the current place to a new one.
	 * 
	 * @param newPlace
	 *            The new place.
	 */
	public void changePlace(String newPlace) {
		place = newPlace;
	}

	public int getYear() {
		return YEAR;
	}

	public int getMonth() {
		return MONTH;
	}

	public int getDay() {
		return DAY;
	}

	public int getStartHour() {
		return startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public int getEndHour() {
		return endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public String getPlace() {
		return place;
	}

	// @Override public int hashCode() {
	// return YEAR/97 + 2*MONTH + 15*DAY;
	// }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Activity)) {
			return false;
		}
		Activity a = (Activity) o;
		return a == this;
	}

	public int compareTo(Activity a) {
		int start = startHour * 60 + startMinute;
		int otherStart = a.getStartHour() * 60 + a.getStartMinute();
		return start - otherStart;
	}
}
