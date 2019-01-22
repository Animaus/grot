package nl.zoethout.grot.util;

<<<<<<< HEAD
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

@SuppressWarnings("unused")
public final class DatetimeHelper {

	private static final DatetimeHelper INSTANCE = new DatetimeHelper();

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

=======
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public final class DatetimeHelper {
	private static final Locale LOCALE = new Locale("nl", "NL");

	private static DateTimeFormatter getFormatter(String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withLocale(LOCALE);
		return formatter;
	}

	/**
	 * 2018-43
	 * 
	 * @param input
	 * @return
	 */
	public static String getKey(LocalDateTime input) {
		return input.format(getFormatter("yyyy-ww"));
	}

	/**
	 * 1-7
	 * 
	 * @param input
	 * @return
	 */
	public static int getDayNumber(LocalDateTime input) {
		return input.getDayOfWeek().getValue();
	}

	/**
	 * List of formatted dates in a chosen week
	 * 
	 * @param chosenYear
	 * @param chosenWeek
	 * @return
	 */
	public static List<String> getDatedWeekDays(int chosenYear, int chosenWeek) {
		List<LocalDateTime> weekDays = getWeekDays(chosenYear, chosenWeek);
		List<String> datedWeekDays = new ArrayList<String>();
		for (LocalDateTime weekDay : weekDays) {
			datedWeekDays.add(getDatedDay(weekDay));
		}
>>>>>>> develop/Grot.190119.1252
		return datedWeekDays;
	}

	/**
<<<<<<< HEAD
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

=======
	 * List of LocalDateTime
	 * 
	 * @param chosenYear
	 * @param chosenWeek
	 * @return
	 */
	public static List<LocalDateTime> getWeekDays(int chosenYear, int chosenWeek) {
		List<LocalDateTime> localDates = new ArrayList<>();
		LocalDateTime dtLocal = LocalDateTime.of(chosenYear, 1, 1, 0, 0, 0)
				.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, chosenWeek)
				.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		// Working days only
		for (int count = 0; count < 5; count++) {
			localDates.add(dtLocal.plusDays(count));
		}
		return localDates;
	}

	/**
	 * List of java.sql.Date in a chosen week
	 */
	public static List<Date> getWeekDates(int chosenYear, int chosenWeek) {
		List<Date> result = new ArrayList<Date>();
>>>>>>> develop/Grot.190119.1252
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
<<<<<<< HEAD

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

	private static ArrayList<String> getNamedDaysAbbr(){
		ArrayList<String> namedList = new ArrayList<String>();
		namedList.add("ma");
		namedList.add("di");
		namedList.add("wo");
		namedList.add("do");
		namedList.add("vr");
		namedList.add("za");
		namedList.add("zo");
		return namedList;
	}

	private static ArrayList<String> getNamedMonthsAbbr(){
		ArrayList<String> namedList = new ArrayList<String>();
		namedList.add("jan");
		namedList.add("feb");
		namedList.add("mrt");
		namedList.add("apr");
		namedList.add("mei");
		namedList.add("jun");
		namedList.add("jul");
		namedList.add("aug");
		namedList.add("sep");
		namedList.add("okt");
		namedList.add("nov");
		namedList.add("dec");
		return namedList;
	}

	// Volledige dag- en maandnamen, bijvoorbeeld: "woensdag 10 juli"
	
	public static String getTodayFull(){
		Date dtDay = new Date();
		return getNamedDayFull(dtDay);
	}

	public static String getTodayWeekFull() {
		Date dtDay = new Date();
		DateTime dtWeekday = new DateTime();
		String datedDay = getNamedDayFull(dtDay);
		datedDay = datedDay + " " + dtWeekday.getYear() + ", week "
				+ dtWeekday.getWeekOfWeekyear();
		return datedDay;
	}

	public static String getNamedDayFull(Date datum){
		DateTime dtWeekday = new DateTime(datum);
		
		String dayString ="";
		String timeString = getTimeString(dtWeekday);
		
		dayString = getNamedDaysFull().get(dtWeekday.getDayOfWeek()-1) + " ";
		dayString = TextUtil.toProperCase(dayString);
		
		dayString = dayString + dtWeekday.getDayOfMonth() + " ";
		dayString = dayString + getNamedMonthsFull().get(dtWeekday.getMonthOfYear()-1) + " ";
		
		dayString = dayString + timeString;
		
		return dayString;
	}

	public static String getFormattedDateFull(Date datum){
		DateTime dtWeekday = new DateTime(datum);
		String strYear = TextUtil.addPreZeros(dtWeekday.getYear(),4);
		String strMonth = getNamedMonthsFull().get(dtWeekday.getMonthOfYear()-1);
		String strDay = TextUtil.addPreZeros(dtWeekday.getDayOfMonth(),2);
		String strWeekDay = getNamedDaysFull().get(dtWeekday.getDayOfWeek()-1) + " ";
		String dateString = strWeekDay + strDay + "-"  + strMonth + "-" + strYear ;
		return dateString;
	}

	private static ArrayList<String> getNamedDaysFull(){
		ArrayList<String> namedList = new ArrayList<String>();
		namedList.add("maandag");
		namedList.add("dinsdag");
		namedList.add("woensdag");
		namedList.add("donderdag");
		namedList.add("vrijdag");
		namedList.add("zaterdag");
		namedList.add("zondag");
		return namedList;
	}

	private static ArrayList<String> getNamedMonthsFull(){
		ArrayList<String> namedList = new ArrayList<String>();
		namedList.add("januari");
		namedList.add("febuari");
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
		return namedList;
	}

	public static String getTimeString(DateTime dateTime) {
		if (dateTime == null) {
			return "";
		} else {
			DateTimeFormatter dtFormat = DateTimeFormat
					.forPattern("HH:mm:ss");
			return dateTime.toString(dtFormat);
		}
	}

=======
		// Working days only
		for (int i = 0; i < 5; ++i) {
			// Conversion to SQL-Date
			Date sqlDate = new Date(calendar.getTime().getTime());
			result.add(sqlDate);
			calendar.add(Calendar.DATE, 1);
		}
		return result;
	}

	/**
	 * Tuesday 23 october
	 * 
	 * @return
	 */
	public static String getToday() {
		return getDatedDay(LocalDateTime.now());
	}

	/**
	 * Tuesday 23 october 2018, week 43
	 * 
	 * @return
	 */
	public static String getTodayWeek() {
		LocalDateTime now = LocalDateTime.now();
		return getDatedDay(now) + " " + now.format(getFormatter("yyyy")) + ", week " + now.format(getFormatter("ww"));
	}

	/**
	 * Tuesday 23 october
	 * 
	 * @param input
	 * @return
	 */
	public static String getDatedDay(LocalDateTime input) {
		String result = TextUtil.toProperCase(input.format(getFormatter("EEEE ")));
		result += input.format(getFormatter("d MMMM")).toLowerCase();
		return result;
	}

	/**
	 * Tuesday 23 october 17:53:54 2018, week 43
	 */
	public static String getTodayWeekFull() {
		LocalDateTime now = LocalDateTime.now();
		String result = TextUtil.toProperCase(now.format(getFormatter("EEEE ")));
		result += now.format(getFormatter("d MMMM HH:mm:ss yyyy")).toLowerCase() + ", week " + now.format(getFormatter("ww"));
		return result;
	}

	/**
	 * Tuesday 23 october 17:53:54
	 * 
	 * @return
	 */
	public static String getTodayFull() {
		LocalDateTime now = LocalDateTime.now();
		return getNamedDayFull(now);
	}

	/**
	 * Tuesday 23 october 17:53:54
	 * 
	 * @param input
	 * @return
	 */
	public static String getNamedDayFull(LocalDateTime input) {
		String result = TextUtil.toProperCase(input.format(getFormatter("EEEE ")));
		result += input.format(getFormatter("d MMMM HH:mm:ss")).toLowerCase();
		return result;
	}

	/**
	 * 17:57:59
	 * 
	 * @param input
	 * @return
	 */
	public static String getTimeString(LocalDateTime input) {
		if (input == null) {
			return "";
		} else {
			DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			return input.format(dtFormat);
		}
	}

	/**
	 * monday 08-march-1965
	 * 
	 * @param input
	 * @return
	 */
	public static String getFormattedDateFull(LocalDateTime input) {
		return input.format(getFormatter("EEEE dd-MMMM-yyyy")).toLowerCase();
	}
>>>>>>> develop/Grot.190119.1252
}