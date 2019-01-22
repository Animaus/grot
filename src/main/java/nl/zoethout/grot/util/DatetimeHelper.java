package nl.zoethout.grot.util;

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
		return datedWeekDays;
	}

	/**
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
}