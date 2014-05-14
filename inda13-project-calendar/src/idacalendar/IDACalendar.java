package idacalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

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
	}

	public void addActivity(int startHour, int startMinute, int endHour,
			int endMinute, String title, String text, String place) {
		if (startHour > 23 || startHour < 0 || startMinute > 59
				|| startMinute < 0 || endHour > 23 || endHour < 0
				|| endMinute > 59 || endMinute < 0) {
			throw new IllegalArgumentException("The given time is illegal");
		}
		Activity a = new Activity(year, month, day, startHour, startMinute,
				endHour, endMinute, title, text, place);
		int hash = hash(a);
		activities[hash].add(a);
	}

	public void removeActivity(Activity a) {
		List<Activity> list = activities[hash(a)];
		list.remove(a);
	}

	public void changeActivity(Activity a, int newStartHour,
			int newStartMinute, int newEndHour, int newEndMinute,
			String newTitle, String newText, String newPlace) {
		if (newStartHour >= 0 && newStartHour <= 23) {
			a.changeStartHour(newStartHour);
		}

		if (newStartMinute >= 0 && newStartMinute <= 59) {
			a.changeStartMinute(newStartMinute);
		}

		if (newEndHour >= 0 && newEndHour <= 23) {
			a.changeStartHour(newEndHour);
		}

		if (newEndMinute >= 0 && newEndMinute <= 59) {
			a.changeEndMinute(newEndMinute);
		}

		if (newTitle != "") {
			a.changeTitle(newTitle);
		}

		if (newText != "") {
			a.changeText(newText);
		}

		if (newPlace != "") {
			a.changePlace(newPlace);
		}
	}

	public Activity[] activitiesPerDay() {
		LinkedList<Activity> acts = new LinkedList<Activity>();
		List<Activity> tableActs = activities[calendar
				.get(Calendar.DAY_OF_YEAR) - 1];

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

	private int hash(Activity a) {
		return calendar.get(Calendar.DAY_OF_YEAR) - 1;
	}

	public void setCurrentDay(int year, int month, int day) {
		calendar.set(year, month, day);
		changeDate();
	}

	public void tomorrow() {
		calendar.roll(Calendar.DATE, true);
		changeDate();
	}

	public void yesterday() {
		calendar.roll(Calendar.DATE, false);
		changeDate();
	}

	public int getCurrentDay() {
		return day;
	}

	public int getCurrentMonth() {
		return month;
	}

	public int getCurrentYear() {
		return year;
	}

	public int daysInMonth(int year, int month) {
		GregorianCalendar newCal = new GregorianCalendar(year, month, 1);
		return newCal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	private void changeDate() {
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DATE);
	}

	private boolean compareDate(int year, int month, int day) {
		return this.year == year && this.month == month && this.day == day;
	}
}



