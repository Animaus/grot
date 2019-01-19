package nl.zoethout.grot.util;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

public final class DatetimeHelper {

	private static final DatetimeHelper INSTANCE = new DatetimeHelper();

	@SuppressWarnings("unused")
	private static DatetimeHelper getInstance() {
		return INSTANCE;
	}

	public static int getDayNumber(Date testDate){
		DateTime testDateTime = new DateTime(testDate);
		return testDateTime.getDayOfWeek();
	}

	/**
	 * Vergelijken van twee datums
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static boolean compareDates(Date firstDate, Date secondDate) {

		// Als "java.util.Date"
		// tijdsregister : 2014-02-26 00:00:00.0
		// werkdag : Wed Feb 26 00:00:00 CET 2014

		// Als "org.joda.time.DateTime"
		// tijdsregister : 2014-02-26T00:00:00.000+01:00
		// werkdag : 2014-02-26T00:00:00.000+01:00

		DateTime firstDateTime = new DateTime(firstDate);
		DateTime secondDateTime = new DateTime(secondDate);

		if (firstDateTime.equals(secondDateTime)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sleutel naar een weekstaat, combinatie jaar-weeknummer (b.v. 2013-49)
	 */
	public static String makeKey(DateTime chosenDate) {
		int chosenWeek = chosenDate.getWeekOfWeekyear();
		int chosenYear = chosenDate.getYear();
		String key = chosenYear + "-" + chosenWeek;
		return key;
	}

	/**
	 * Lijst met nederlandstalig geformatteerde datums in een gekozen week
	 */
	public static List<String> getDatedWeekDays(int chosenYear, int chosenWeek) {
		List<DateTime> weekDays = getWeekDays(chosenYear, chosenWeek);
		List<String> datedWeekDays = new ArrayList<String>();

		for (DateTime weekDay : weekDays) {
			datedWeekDays.add(getDatedDay(weekDay));
		}

		return datedWeekDays;
	}

	/**
	 * Lijst met datums (org.joda.time.DateTime) in een gekozen week
	 */
	public static List<DateTime> getWeekDays(int chosenYear, int chosenWeek) {
		List<DateTime> result = new ArrayList<DateTime>();

		// We know week number and year.
		// Get calendar, clear it and set week number and year.
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, chosenWeek);
		calendar.set(Calendar.YEAR, chosenYear);

		// Now get the first day of week.
		DateTime chosenDateTime = new DateTime(calendar.getTime());

		while (chosenDateTime.getWeekOfWeekyear() == chosenWeek) {
			result.add(chosenDateTime);
			chosenDateTime = chosenDateTime.plusDays(1);
		}

		// Alleen werkdagen tonen
		while (result.size() > 5) {
			result.remove(result.size() - 1);
		}

		return result;
	}

	/**
	 * Lijst met datums (java.util.Date) in een gekozen week
	 */
	public static List<java.sql.Date> getWeekDates(int chosenYear, int chosenWeek) {
		List<java.sql.Date> result = new ArrayList<java.sql.Date>();

		// We know week number and year.
		// Get calendar, clear it and set week number and year.
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, chosenWeek);
		calendar.set(Calendar.YEAR, chosenYear);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		for (int i = 0; i < 7; ++i) {
			// Conversie naar SQL-Date
			java.sql.Date sqlDate = new java.sql.Date(calendar.getTime().getTime());
			result.add(sqlDate);
			calendar.add(Calendar.DATE, 1);
		}

		// Alleen werkdagen tonen
		while (result.size() > 5) {
			result.remove(result.size() - 1);
		}

		return result;
	}

	public static String getToday() {
		DateTime dtWeekday = new DateTime();
		return getDatedDay(dtWeekday);
	}

	public static String getTodayWeek() {
		DateTime dtWeekday = new DateTime();
		String datedDay = getDatedDay(dtWeekday);
		datedDay = datedDay + " " + dtWeekday.getYear() + ", week "
				+ dtWeekday.getWeekOfWeekyear();

		return datedDay;
	}

	public static String getDatedDay(Date dtWeekday) {
		DateTime dtDay = new DateTime(dtWeekday);
		return getDatedDay(dtDay);
	}

	/**
	 * Gedateerde naam van weekdag (b.v Woensdag 10 Juli)
	 */
	public static String getDatedDay(DateTime dtWeekday) {
		String dayString = "";
		dayString = dayString
				+ getNamedDays().get(dtWeekday.getDayOfWeek() - 1) + " ";
		dayString = dayString + dtWeekday.getDayOfMonth() + " ";
		dayString = dayString
				+ getNamedMonths().get(dtWeekday.getMonthOfYear() - 1);
		return dayString;
	}

	private static ArrayList<String> getNamedDays() {
		// instantiëring
		ArrayList<String> namedList = new ArrayList<String>();
		// arraylist met namen van weekdagen
		namedList.add("Maandag");
		namedList.add("Dinsdag");
		namedList.add("Woensdag");
		namedList.add("Donderdag");
		namedList.add("Vrijdag");
		namedList.add("Zaterdag");
		namedList.add("Zondag");
		// resultaat
		return namedList;
	}

	private static ArrayList<String> getNamedMonths() {
		// instantiëring
		ArrayList<String> namedList = new ArrayList<String>();
		// arraylist met namen van maanden
		namedList.add("januari");
		namedList.add("februari");
		namedList.add("maart");
		namedList.add("april");
		namedList.add("mei");
		namedList.add("juni");
		namedList.add("juli");
		namedList.add("augustus");
		namedList.add("september");
		namedList.add("oktober");
		namedList.add("november");
		namedList.add("december");
		// resultaat
		return namedList;
	}

}