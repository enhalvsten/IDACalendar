package idacalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

/**
 * IDACalendar simulates a calendar. It is constantly situated on a specific day, and has methods for
 * changing dates. It also contains a hash table, which is built as an array containing several LinkedLists.
 * This is made for containing all the activities of all days.
 * 
 * @author Isak Stensö, David Bergling & Andreas Rosenback
 * @version 2014-05-15
 */
public class IDACalendar {
	private GregorianCalendar calendar;
	private List<Activity>[] activities;
	private final int SIZE = 366;
	private int year;
	private int month;
	private int day;

	public IDACalendar() {
		calendar = new GregorianCalendar(Locale.GERMANY);

		@SuppressWarnings("unchecked")
		List<Activity>[] t = new LinkedList[SIZE];
		activities = t;
		changeDate();
	}
	
	/**
	 * Adds an Activity to the calendar, by generating a hash value for the activity, and
	 * then placing it in the list of activities.
	 * 
	 * @param startingHour The hour the activity starts.
	 * @param startingMinute The exact minute the activity starts.
	 * @param endingHour The hour the activity ends.
	 * @param endingMinute The exact minute the activity ends.
	 * @param title The name of the activity.
	 * @param text Extra notes about the activity.
	 * @param place The location of the activity.
	 */
	public void addActivity(String startingHour, String startingMinute, String endingHour,
			String endingMinute, String title, String text, String place) {
		int startHour;
		int startMinute;
		int endHour;
		int endMinute;
		try {
			startHour = Integer.parseInt(startingHour);
			startMinute = Integer.parseInt(startingMinute);
			endHour = Integer.parseInt(endingHour);
			endMinute = Integer.parseInt(endingMinute);
		}
		catch (NumberFormatException e){
			return;
		}
		if (startHour > 23 || startHour < 0 || startMinute > 59
				|| startMinute < 0 || endHour > 23 || endHour < 0
				|| endMinute > 59 || endMinute < 0) {
			throw new IllegalArgumentException("The given time is illegal");
		}
		Activity a = new Activity(year, month, day, startHour, startMinute,
				endHour, endMinute, title, text, place);
		int hash = hash(a);
		if (activities[hash] == null) {
			activities[hash] = new LinkedList<Activity>();
		}
		activities[hash].add(a);
	}

	/**
	 * Removes an activity from the list of activities.
	 * 
	 * @param a The activity to be removed.
	 */
	public void removeActivity(Activity a) {
		List<Activity> list = activities[hash(a)];
		list.remove(a);
	}
	
	/**
	 * Returns the week day of the current date.
	 * 
	 * @return The current day of the week.
	 */
	public String getWeekDay() {
		String s = "";
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayIndex == 1) { 
			s = "Sunday";
		}
		if (dayIndex == 2) { 
			s = "Monday";
		}
		if (dayIndex == 3) { 
			s = "Tuesday";
		}
		if (dayIndex == 4) { 
			s = "Wednesday";
		}
		if (dayIndex == 5) { 
			s = "Thursday";
		}
		if (dayIndex == 6) { 
			s = "Friday";
		} 
		if (dayIndex == 7) { 
			s = "Saturday";
		}
		return s;
	}
	
	/**
	 * Returns a list of all activities in the current day. It first creates a list 
	 * of all activities that share the given place in the array of all activities. Then
	 * it creates a list of all those that happen on the current day, in order to be able to
	 * create an array of the same activities (we must know the amount).
	 * 
	 * @return An array of all activities of the current day.
	 */
	public Activity[] activitiesPerDay() {
		LinkedList<Activity> acts = new LinkedList<Activity>();
		List<Activity> tableActs = activities[calendar.get(Calendar.DAY_OF_YEAR) - 1];

		if (tableActs == null) {
			return null;
		}
		for (int i = 0; i < tableActs.size(); i++) {
			Activity a = tableActs.get(i);
			if (compareDate(a.getYear(), a.getMonth(), a.getDay())) {
				acts.add(a);
			}
		}
		Activity[] activitiesPerDay = new Activity[acts.size()];
		for (int i = 0; i < acts.size(); i++) {
			activitiesPerDay[i] = acts.get(i);
		}
		Arrays.sort(activitiesPerDay);
		return activitiesPerDay;
	}

	/**
	 * Generates a hash value for an activity, that can be used to find the index of an activity in 
	 * the array of all activities.
	 * 
	 * @param a The activity which generates a hash value.
	 * @return The hash value.
	 */
	private int hash(Activity a) {
		return calendar.get(Calendar.DAY_OF_YEAR) - 1;
	}

	/**
	 * Sets the current date of the calendar .
	 * 
	 * @param year The chosen year.
	 * @param month The chosen month. 
	 * @param day The chosen day.
	 */
	public void setCurrentDay(int year, int month, int day) {
		calendar.set(year, month, day);
		changeDate();
	}

	/**
	 * Changes the selected date of the calendar to the next day.
	 */
	public void tomorrow() {
		calendar.add(Calendar.DATE, 1);
		
		changeDate();
	}

	/**
	 * Changes the selected date of the calendar to the previous day.
	 */
	public void yesterday() {
		calendar.add(Calendar.DATE, -1);
		changeDate();
	}

	/**
	 * Returns the current day, ranging from 1 to 31.
	 * 
	 * @return The current day.
	 */
	public int getCurrentDay() {
		return day;
	}

	/**
	 * Returns the current month.
	 * 
	 * @return The current month.
	 */
	public int getCurrentMonth() {
		return month;
	}

	/**
	 * Returns the current year.
	 * 
	 * @return The current year.
	 */
	public int getCurrentYear() {
		return year;
	}

	/**
	 * Calculates the number of days in the current month.
	 * 
	 * @param year The selected year.
	 * @param month The selected month
	 * @return The number of days in the selected month.
	 */
	public int daysInMonth(int year, int month) {
		GregorianCalendar newCal = new GregorianCalendar(year, month, 1);
		return newCal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Updates the date of the fields: year, month and day.
	 */
	private void changeDate() {
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Compares a given date with the one of the calendar. Returns true if the dates are the same.
	 * 
	 * @param year The given year.
	 * @param month The given month.
	 * @param day The given day.
	 * @return Whether the dates are the same.
	 */
	private boolean compareDate(int year, int month, int day) {
		return this.year == year && this.month == month && this.day == day;
	}
}



