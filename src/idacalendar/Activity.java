package idacalendar;

/**
 * Activity represents an activity that can be added to a day in the calendar.
 * It is possible to change the starting and ending times, title, text and place
 * of the activity.
 * 
 * @author Isak Stensö, David Bergling & Andreas Rosenback
 * @version 2014-05-15
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

	/**
	 * Returns a string with the starting time.
	 * 
	 * @return The starting time.
	 */
	public String getStartTime() {
		StringBuilder sb = new StringBuilder();
		if (startHour == 0) {
			sb.append("00");
		} else if (startHour < 10){
			sb.append("0" + startHour);
		} else {
			sb.append(startHour);
		}
		sb.append(":");
		if (startMinute == 0) {
			sb.append("00");
		} else if (startMinute < 10){
			sb.append("0" + startMinute);
		} else {
			sb.append(startMinute);
		}
		return sb.toString();
	}
	
	/**
	 * Returns the year of the activity
	 * 
	 * @return The year of the activity. 
	 */
	public int getYear() {
		return YEAR;
	}

	/**
	 * Returns the month of the activity
	 * 
	 * @return The month of the activity. 
	 */
	public int getMonth() {
		return MONTH;
	}

	/**
	 * Returns the day of the activity
	 * 
	 * @return The day of the activity. 
	 */
	public int getDay() {
		return DAY;
	}

	/**
	 * Returns the starting hour of the activity
	 * 
	 * @return The starting hour of the activity. 
	 */
	public int getStartHour() {
		return startHour;
	}

	/**
	 * Returns the starting minute of the activity
	 * 
	 * @return The starting minute of the activity. 
	 */
	public int getStartMinute() {
		return startMinute;
	}

	/**
	 * Returns the ending hour of the activity
	 * 
	 * @return The ending hour of the activity. 
	 */
	public int getEndHour() {
		return endHour;
	}

	/**
	 * Returns the ending minute of the activity
	 * 
	 * @return The ending minute of the activity. 
	 */
	public int getEndMinute() {
		return endMinute;
	}
	
	/**
	 * Returns the title of the activity
	 * 
	 * @return The title of the activity. 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the notes of the activity
	 * 
	 * @return The notes of the activity. 
	 */
	public String getText() {
		return text;
	}

	/**
	 * Returns the location of the activity
	 * 
	 * @return The location of the activity. 
	 */
	public String getPlace() {
		return place;
	}

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
