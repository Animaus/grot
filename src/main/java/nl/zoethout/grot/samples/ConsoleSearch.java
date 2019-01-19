package nl.zoethout.grot.samples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.text.StringEscapeUtils;

@SuppressWarnings({ "unused" })
public class ConsoleSearch {
	private void debug(String msg) {
		System.out.println("*** " + msg);
	}

	// Scherm-layout
	private static final String LINE_FAT = "\n================================================================\n";
	private static final String LINE_THIN = "\n----------------------------------------------------------------\n";
	private static final String LINE_THIN_FIRST = "\n------------------------- Record: ";
	private static final String LINE_THIN_LAST = " -------------------------\n";
	private static final String SEP = System.getProperty("file.separator");
	private static final String MSG_END = "...";
	// Velden
	private static final ConsoleSearch ME = new ConsoleSearch();
	private final Console c = System.console();
	private Database dbase = new Database("fg_genial", "cursist", "password");
	private Statement stmt = dbase.getStatement();
	private ResultSet rs;
	private boolean running = true;
	private String input = "";
	private String root;
	private final String root_default;
	private final String root_program;
	private final String root_desktop;
	private TargetFile output;
	private boolean write = false;
	private final Locale defaultLoc = new Locale("nl");
	private Locale currentLoc;
	private String iniName = "ConsoleSearch.ini";
	// Zoekopdrachten
	private String schema;
	private String table;
	private String userName;
	// Scherm-output
	private boolean showHeader;
	private boolean showFooter;
	private boolean showColumnName;
	private boolean showRecordNumber;
	private boolean toggledHeader;
	private boolean toggledFooter;
	private boolean toggledColumnName;
	private boolean toggledRecordNumber;
	// Internationalisatie - fouten
	private ResourceBundle bundle;
	private String error_msg;
	private String error_connection;
	// private String error_no_file;
	private String error_reason;
	private String error_statement;
	// Internationalisatie - validatie
	private String invalid_command;
	private String invalid_delete;
	private String invalid_exist;
	private String invalid_result;
	private String invalid_size;
	private String invalid_source;
	private String invalid_string;
	private String invalid_type;
	// Internationalisatie - bestands I/O
	private String reader_no_read;
	private String reader_no_close;
	private String writer_no_write;
	private String writer_no_close;
	private String writer_no_flush;
	// Internationalisatie - meldingen
	private String msg_settings;
	private String msg_settings_save;
	private String msg_settings_reset;
	private String msg_settings_default;
	private String msg_connection_closed;
	private String msg_connection_opened;
	private String msg_custom;
	private String msg_custom_explanation;
	private String msg_custom_all;
	private String msg_backup;
	private String msg_backup_explanation;
	private String msg_backup_all;
	private String msg_deleted;
	private String msg_input;
	private String msg_language;
	private String msg_link;
	private String msg_link_explanation;
	private String msg_link_all;
	private String msg_novalue;
	private String msg_root;
	private String msg_table;
	private String msg_useless;
	private String msg_user;
	private String msg_written;
	// Internationalisatie - programma variabelen
	private String sys_help;
	private String sys_prompt;
	// Variabelen uit het ini-bestand
	private Map<String, List<String>> backupColumns = new LinkedHashMap<String, List<String>>();
	private Map<String, String> backupHelp = new LinkedHashMap<String, String>();
	private Map<String, String> customQuery = new LinkedHashMap<String, String>();
	private Map<String, String> customHelp = new LinkedHashMap<String, String>();
	private Map<String, String> linkItem = new LinkedHashMap<String, String>();
	private Map<String, String> linkHelp = new LinkedHashMap<String, String>();

	// Constructor
	private ConsoleSearch() {
		// System.setProperty("sun.jnu.encoding", "UTF8");
		// System.setProperty("file.encoding", "UTF8");
		// Uitvoermap standaard
		root_default = System.getProperty("user.dir") + SEP + "data";
		root_program = System.getProperty("user.dir");
		root_desktop = System.getProperty("user.home") + SEP + "Desktop";
		// Standaard waarden (o.a. meldingen)
		settingsDefault();
		// Openen INI
		settingsRead();
		mainHelp();
		// Controle op connectie
		if (dbase.getConnection() == null) {
			screenFeedback(error_connection + MSG_END);
		}
	}

	public static void main(String[] args) throws FileException {
		me().run();
	}

	private static ConsoleSearch me() throws FileException {
		return ME;
	}

	private void run() throws FileException {
		while (running) {
			input = c.readLine("%11s", sys_prompt);
			inputCheck();
			if (running && input != null) {
				mainSearch(true);
			}
		}
	}

	private void read() {
		write = false;
		sys_prompt = sys_prompt.toLowerCase();
	}

	private void write() {
		write = true;
		sys_prompt = sys_prompt.toUpperCase();
	}

	private void mainBackup(String option) throws FileException {
		// Regeleinde
		String lineBreak = screenLinebreak();
		// Invoercontrole
		switch (option) {
		case "":
		case "?":
		case "sh":
		case "show":
			// Header
			screenHeader(msg_backup, lineBreak);
			// Uitleg
			screenSingle(msg_backup_explanation, lineBreak);
			// Witregel
			screenOutput(lineBreak);
			// Commando's
			screenMulti(4, 24, lineBreak, "/b=all", msg_backup_all);
			for (String cmd : backupColumns.keySet()) {
				String help = backupHelp.get(cmd);
				if (help == null) {
					help = msg_novalue;
				}
				screenMulti(4, 24, lineBreak, "/b=" + cmd, help);
			}
			// Footer
			screenOutput(LINE_THIN + lineBreak);
			// Controle op schrijf-modus
			if (write) {
				// Write-modus=OFF
				outputClose(true);
			}
			break;
		case "all":
			// Witregel
			screenSystem("");
			// Uitvoeren (alle commando's)
			for (String key : backupColumns.keySet()) {
				List<String> columns = backupColumns.get(key);
				backupStart(key, columns);
			}
			// Witregel
			screenSystem("");
			break;
		default:
			// Afkorting opvragen
			String shortcut = getShortcut(option, backupColumns.keySet());
			// Controle afkorting
			if (shortcut.equals(":error:")) {
				// Geen waarde
				screenFeedback(msg_backup + ". " + invalid_command + MSG_END);
			} else {
				// Opvragen waarde via afkorting
				List<String> columns = backupColumns.get(shortcut);
				// Witregel
				screenSystem("");
				// Uitvoeren (specifiek commando)
				backupStart(shortcut, columns);
				// Witregel
				screenSystem("");
			}
			break;
		}
		read(); // Write-modus=OFF
	}

	private void mainConnect(boolean connect) {
		String schema = "fg_genial";
		read(); // Write-modus=OFF
		if (connect) {
			dbase = new Database(schema, "cursist", "password");
			stmt = dbase.getStatement();
			if (stmt != null) {
				screenFeedback(msg_connection_opened + ": " + schema + MSG_END);
			}
		} else {
			try {
				dbase.getConnection().close();
				screenFeedback(msg_connection_closed + ": " + schema + MSG_END);
			} catch (SQLException e) {
				screenFeedback(error_msg + ": " + e.getMessage() + MSG_END);
			}
		}
	}

	private void mainCustom(String option) throws FileException {
		// Regeleinde
		String lineBreak;
		boolean searchWrite = this.write;
		// Invoercontrole
		switch (option) {
		case "":
		case "?":
		case "sh":
		case "show":
			// Regeleinde
			lineBreak = screenLinebreak();
			// Header
			screenHeader(msg_custom, lineBreak);
			// Uitleg
			screenSingle(msg_custom_explanation, lineBreak);
			// Witregel
			screenOutput(lineBreak);
			// Commando's
			screenMulti(4, 24, lineBreak, "/c=all", msg_custom_all);
			for (String cmd : customQuery.keySet()) {
				String help = customHelp.get(cmd);
				if (help == null) {
					help = msg_novalue;
				}
				screenMulti(4, 24, lineBreak, "/c=" + cmd, help);
			}
			// Footer
			screenOutput(LINE_THIN + lineBreak);
			// Schrijfcontrole
			if (write) {
				// Uitvoer afsluiten
				outputClose(true);
			}
			break;
		case "h":
		case "help":
			customHelp(true);
			break;
		case "a":
		case "all":
			// Witregel
			screenSystem("");
			// Uitvoeren (alle commando's)
			for (String key : customQuery.keySet()) {
				if (searchWrite) {
					write();
				}
				switch (key) {
				case "help":
					customHelp(false);
					break;
				default:
					// Uitvoeren (specifiek commando)
					input = customQuery.get(key);
					mainSearch(false);
					break;
				}
			}
			// Nieuwe invoer mogelijk maken
			input = null;
			// Witregel
			screenSystem("");
			break;
		default:
			// Afkorting opvragen
			String shortcut = getShortcut(option, customQuery.keySet());
			// Controle afkorting
			if (shortcut.equals(":error:")) {
				// Geen waarde
				screenFeedback(msg_custom + ". " + invalid_command + MSG_END);
			} else {
				// Opvragen waarde via afkorting
				input = customQuery.get(shortcut);
				// Uitvoeren (specifiek commando)
				mainSearch(true);
				// Nieuwe invoer mogelijk maken
				input = null;
			}
			break;
		}
		read(); // Write-modus=OFF
	}

	private void mainDelete() {
		read(); // Write-modus=OFF
		try {
			screenSystem("");
			MyVisitor visitor = new MyVisitor("output", "txt");
			Files.walkFileTree(Paths.get(root), visitor);
			if (visitor.deleted == 0) {
				screenSystem(invalid_delete + MSG_END);
			}
		} catch (IOException e) {
			screenSystem(error_msg + ": " + e.getMessage() + MSG_END);
		}
		screenSystem("");
	}

	private void mainHelp() {
		String lineBreak = screenLinebreak();
		if (write) {
			helpInfo(lineBreak);
			screenOutput(sys_help);
			outputClose(true);
			read();
		} else {
			helpInfo(lineBreak);
			screenSystem(sys_help);
		}
	}

	private void mainLink(String option) {
		// Regeleinde
		String lineBreak = screenLinebreak();
		boolean searchWrite = this.write;
		// Invoercontrole
		switch (option) {
		case "":
		case "?":
		case "sh":
		case "show":
			// Header
			screenHeader(msg_link, lineBreak);
			// Uitleg
			screenSingle(msg_link_explanation, lineBreak);
			// Witregel
			screenOutput(lineBreak);
			// Commando's
			screenMulti(4, 24, lineBreak, "/l=all", msg_link_all);
			for (String cmd : linkItem.keySet()) {
				String help = linkHelp.get(cmd);
				if (help == null) {
					help = msg_novalue;
				}
				screenMulti(4, 24, lineBreak, "/l=" + cmd, help);
			}
			// Footer
			screenOutput(LINE_THIN + lineBreak);
			// Controle op schrijf-modus
			if (write) {
				// Write-modus=OFF
				outputClose(true);
			}
			break;
		case "a":
		case "all":
			// Witregel
			screenSystem("");
			// Uitvoeren (alle commando's)
			for (String key : linkItem.keySet()) {
				if (searchWrite) {
					write();
				}
				String bookmark = linkItem.get(key);
				linkItem(bookmark, false);
			}
			// Witregel
			screenSystem("");
			break;
		default:
			// Afkorting opvragen
			String shortcut = getShortcut(option, linkItem.keySet());
			// Controle afkorting
			if (shortcut.equals(":error:")) {
				// Geen waarde
				screenFeedback(msg_link + ". " + invalid_command + MSG_END);
			} else {
				// Opvragen waarde via afkorting
				String bookmark = linkItem.get(shortcut);
				// Witregel
				screenOutput(lineBreak);
				// Uitvoeren (specifiek commando)
				linkItem(bookmark, true);
				// Witregel
				screenOutput(lineBreak);
			}
			break;
		}
		read(); // Write-modus=OFF
	}

	private String getShortcut(String option, Set<String> keySet) {
		String result = ":error:";
		int partial = 0;
		Map<String, String> part = new LinkedHashMap<String, String>();
		for (String key : keySet) {
			if (key.indexOf(option) == 0) {
				part.put(option, key);
				partial += 1;
			}
		}
		if (partial == 1) {
			result = part.get(option);
		}
		return result;
	}

	private void mainLanguage(String language, boolean showHelp) {
		read(); // Write-modus=OFF
		String lang = language.toLowerCase();
		if (lang.length() == 2) {
			setLanguageCurrent(lang, showHelp);
		} else {
			screenFeedback(invalid_source + ": " + language + MSG_END);
		}
	}

	private void mainRoot(String option, boolean showHelp) {
		read(); // Write-modus=OFF
		switch (option) {
		case "def":
		case "default":
			option = root_default;
			if (Files.notExists(Paths.get(option))) {
				try {
					Files.createDirectory(Paths.get(option));
				} catch (IOException e) {
					screenFeedback(error_msg + ": " + e.getMessage() + MSG_END);
				}
			}
			break;
		case "prog":
		case "program":
			option = root_program;
			break;
		case "desktop":
			option = root_desktop;
			break;
		default:
			break;
		}
		String feedback = "";
		try {
			if (option.equals("")) {
				feedback = invalid_string;
			} else if (Files.notExists(Paths.get(option))) {
				feedback = invalid_exist + ": " + option;
			} else {
				root = option;
				if (showHelp)
					mainHelp();
			}
		} catch (Exception e) {
			feedback = error_msg + ": " + e.getMessage();
		}
		if (!feedback.equals(""))
			screenFeedback(feedback + MSG_END);
	}

	private void mainSearch(boolean whitelines) {
		String query = "";
		List<String> columns = null;
		String subQuery0;
		String subQuery1;
		String subQuery2;
		String subQuery3;
		String key = table.toLowerCase();
		switch (key) {
		case "answer":
			screenFeedback(msg_useless + table + MSG_END);
			input = null;
			break;
		case "module":
			screenFeedback(msg_useless + table + MSG_END);
			input = null;
			break;
		case "question":
			subQuery0 = "SELECT * FROM " + table + " WHERE question LIKE '%";
			subQuery1 = " AND question LIKE \'%";
			subQuery2 = " OR explanation LIKE \'%";
			subQuery3 = " OR options LIKE \'%";
			query = searchComposeQuery(subQuery0, subQuery1, subQuery2, subQuery3);
			columns = getVarList("question", "options", "explanation");
			showColumnName = true;
			break;
		case "textentry":
			subQuery0 = "SELECT * FROM " + table + " WHERE user_name = '" + userName + "' AND text LIKE '%";
			subQuery1 = " AND text LIKE \'%";
			query = searchComposeQuery(subQuery0, subQuery1);
			columns = getVarList("date_input", "text");
			break;
		case "user_modules":
			screenFeedback(msg_useless + table + MSG_END);
			input = null;
			break;
		default:
			screenFeedback(msg_useless + table + MSG_END);
			input = null;
			break;
		}
		if (!query.equals("")) {
			searchExecuteQuery(query, columns, whitelines);
		}
	}

	private void mainSettings(String option) {
		String lineBreak = screenLinebreak();
		switch (option) {
		case "":
		case "?":
		case "sh":
		case "show":
			// Header
			screenHeader(msg_settings, lineBreak);
			// Instellingen
			screenSingle("lang=" + currentLoc.getLanguage(), lineBreak);
			screenSingle("user=" + userName, lineBreak);
			screenSingle("schema=" + schema, lineBreak);
			screenSingle("table=" + table, lineBreak);
			screenSingle("root=" + root, lineBreak);
			screenSingle("header=" + showHeader, lineBreak);
			screenSingle("footer=" + showFooter, lineBreak);
			screenSingle("name=" + showColumnName, lineBreak);
			screenSingle("number=" + showRecordNumber, lineBreak);
			// Witregel
			screenOutput(lineBreak);
			// Commando's
			screenMulti(4, 24, lineBreak, "/s=save", msg_settings_save);
			screenMulti(4, 24, lineBreak, "/s=reset", msg_settings_reset);
			screenMulti(4, 24, lineBreak, "/s=default", msg_settings_default);
			// Footer
			screenOutput(LINE_THIN + lineBreak);
			// Controle op schrijf-modus
			if (write) {
				// Write-modus=OFF
				read();
				// Afsluiten uitvoerbestand
				outputClose(true);
			}
			break;
		case "sv":
		case "save":
			settingsWrite();
			break;
		case "d":
		case "def":
		case "default":
			settingsDefault();
			mainHelp();
			break;
		case "r":
		case "res":
		case "reset":
			settingsReset();
			break;
		default:
			screenFeedback(msg_settings + ". " + invalid_command + MSG_END);
			break;
		}
	}

	private void mainSystem() {
		String lineBreak = "";
		if (write) {
			lineBreak = "\n";
			// Doelbestand openen
			String fileName = "properties.txt";
			output = new TargetFile(root, fileName);
		}
		// Output voorbereiden
		Properties p = System.getProperties();
		Set<String> props = p.stringPropertyNames();
		List<String> properties = new ArrayList<String>();
		String prop;
		for (Object obj : props.toArray()) {
			prop = obj.toString();
			properties.add(prop);
		}
		Sorter sorter = new Sorter();
		Collections.sort(properties, sorter);
		// Tekstregel
		String line = "";
		// Output
		screenOutput(lineBreak);
		for (String s : properties) {
			line = s + "\t:\t" + System.getProperty(s);
			screenOutput(line + lineBreak);
		}
		screenOutput(lineBreak);
		if (write) {
			// Doelbestand sluiten
			outputClose(true);
			read(); // Write-modus=OFF
		}
	}

	private void mainTable(String option) {
		read(); // Write-modus=OFF
		String key = option.toLowerCase();
		switch (key) {
		case "question":
		case "textentry":
			table = option;
			mainHelp();
			break;
		default:
			screenFeedback(msg_useless + option + MSG_END);
		}
	}

	private void mainToggle(String option) {
		read(); // Write-modus=OFF
		switch (option) {
		case "h":
		case "header":
			showHeader = toggle(showHeader);
			break;
		case "f":
		case "footer":
			showFooter = toggle(showFooter);
			break;
		case "nm":
		case "name":
			showColumnName = toggle(showColumnName);
			break;
		case "nr":
		case "number":
			showRecordNumber = toggle(showRecordNumber);
			break;
		}
		mainHelp();
	}

	private void mainUser(String option) {
		read(); // Write-modus=OFF
		userName = option;
		mainHelp();
	}

	private void inputCheck() throws FileException {
		String option = inputOption();
		switch (input.trim()) {
		case "":
			input = null;
			break;
		case "/bu":
		case "/bck":
		case "/backup":
			// Reservekopie - helpscherm
			input = null;
			mainBackup("show");
			break;
		case "/b":
			// Reservekopie - specifieke tabel
			input = null;
			mainBackup(option);
			break;
		case "/co":
		case "/con":
		case "/connect":
			input = null;
			mainConnect(true);
			break;
		case "/cu":
		case "/custom":
			// Opgeslagen zoekopdrachten
			input = null;
			mainCustom("show");
			break;
		case "/c":
			// Opgeslagen zoekopdrachten - specifieke zoekopdracht
			input = null;
			mainCustom(option);
			break;
		case "/del":
		case "/delete":
			input = null;
			mainDelete();
			break;
		case "/dis":
		case "/disconnect":
			input = null;
			mainConnect(false);
			break;
		case "/?":
		case "/h":
		case "/help":
			input = null;
			mainHelp();
			break;
		case "/lang":
			input = null;
			mainLanguage(option, true);
			break;
		case "/li":
		case "/link":
			// Koppelingen - helpscherm
			input = null;
			mainLink("show");
			break;
		case "/l":
			// Koppelingen - specifieke koppeling
			input = null;
			mainLink(option);
			break;
		case "/r":
		case "/read":
			input = null;
			read();
			break;
		case "/rt":
		case "/root":
			input = null;
			mainRoot(option, true);
			break;
		case "/set":
			// instellingen van het programma
			input = null;
			mainSettings(option);
			break;
		case "/s":
			// instellingen van het programma - specifieke instelling
			input = null;
			mainSettings(option);
			break;
		case "/sys":
		case "/system":
			input = null;
			mainSystem();
			break;
		case "/table":
			input = null;
			mainTable(option);
			break;
		case "/toggle":
			input = null;
			mainToggle(option);
			break;
		case "/user":
			input = null;
			mainUser(option);
			break;
		case "/w":
		case "/write":
			input = null;
			write();
			break;
		case "/q":
		case "/quit":
			// System.setProperty("sun.jnu.encoding", "Cp1252");
			// System.setProperty("file.encoding", "Cp1252");
			running = false;
			break;
		default:
			if (input.indexOf("/") == 0) {
				input = null;
				screenFeedback(msg_input + ". " + invalid_command + MSG_END);
			} else if (input.length() < 4) {
				input = null;
				screenFeedback(msg_input + ". " + invalid_size + MSG_END);
			}
			running = true;
			break;
		}
	}

	private String inputOption() {
		String result = "";
		int length = input.length();
		// lang
		if (input.indexOf("/lang=") == 0) {
			result = input.substring(6, length);
			input = input.substring(0, 5);
			// reservekopie
		} else if (input.indexOf("/b=") == 0) {
			result = input.substring(3, length);
			input = input.substring(0, 2);
			// custom
		} else if (input.indexOf("/c=") == 0) {
			result = input.substring(3, length);
			input = input.substring(0, 2);
			// link
		} else if (input.indexOf("/l=") == 0) {
			result = input.substring(3, length);
			input = input.substring(0, 2);
			// root
		} else if (input.indexOf("/root=") == 0) {
			result = input.substring(6, length);
			input = input.substring(0, 5);
			// settings
		} else if (input.indexOf("/s=") == 0) {
			result = input.substring(3, length);
			input = input.substring(0, 2);
			// table
		} else if (input.indexOf("/table=") == 0) {
			result = input.substring(7, length);
			input = input.substring(0, 6);
			// toggle
		} else if (input.indexOf("/toggle=") == 0) {
			result = input.substring(8, length);
			input = input.substring(0, 7);
			// user
		} else if (input.indexOf("/user=") == 0) {
			result = input.substring(6, length);
			input = input.substring(0, 5);
		}
		result = result.trim();
		return result;
	}

	private void screenHeader(String line, String lineBreak) {
		screenOutput(LINE_THIN);
		screenOutput(lineBreak + i(3, line) + lineBreak);
		screenOutput(LINE_THIN + lineBreak);
	}

	/**
	 * @param indent
	 *            Indent for complete line
	 * @param lenght
	 *            Length for first line
	 * @param lineBreak
	 *            Linebreak
	 * @param line1
	 *            First line
	 * @param line2
	 *            Second line
	 */
	private void screenMulti(int indent, int lenght, String lineBreak, String line1, String line2) {
		screenOutput(i(indent, lenght, line1) + line2 + lineBreak);
	}

	private void screenSingle(String line, String lineBreak) {
		screenOutput(i(3, line) + lineBreak);
	}

	private void screenOutput(String line) {
		if (write) {
			if (output != null) {
				try {
					output.write(line);
				} catch (FileException e) {
					screenFeedback(error_msg + ": " + e.getMessage() + MSG_END);
				}
			}
		} else {
			screenSystem(line);
		}
	}

	private void screenSystem(String line) {
		System.out.println(line);
	}

	private void screenFeedback(String line) {
		System.out.println("");
		System.out.println(line);
		System.out.println("");
	}

	/**
	 * Geeft het regeleinde terug. Witregel bij schermweergave, rijterugkeer bij
	 * uitvoer naar schijf. Initialiseert tevens het uitvoerbestand.
	 * 
	 * @return regeleinde
	 */
	private String screenLinebreak() {
		String lineBreak = "";
		if (write) {
			lineBreak = "\n";
			outputInit();
		}
		return lineBreak;
	}

	private String i(int space, int min, String line) {
		String indent = String.format("%" + space + "s", "");
		return String.format("%-" + min + "s", indent + line);
	}

	private String i(int space, String line) {
		String indent = String.format("%" + space + "s", "");
		return indent + line;
	}

	private void outputInit() {
		// Bestandsnaam
		String timekey = getTimekey();
		String fileName = "output_" + timekey + ".txt";
		// Doelbestand openen
		output = new TargetFile(root, fileName);
	}

	private void outputClose(boolean whitelines) {
		if (output != null) {
			String fileName = output.filePath;
			try {
				output.terminate();
				String msg = msg_written + ": " + fileName;
				if (whitelines) {
					screenFeedback(msg + MSG_END);
				} else {
					screenSystem(msg + MSG_END);
				}
			} catch (NullPointerException e) {
				// Niets geschreven
			} catch (FileException e) {
				screenFeedback(error_msg + ": " + e.getMessage() + MSG_END);
			}
		}
	}

	private void backupStart(String option, List<String> columns) throws FileException {
		try {
			backup(option, columns);
		} catch (SQLException e) {
			screenSystem(error_msg + ": " + e.getMessage().toLowerCase() + MSG_END);
		}
	}

	private void backup(String table, List<String> columns) throws SQLException, FileException {
		String query = "SELECT * FROM " + table;
		backupSql(table, query, columns);
	}

	private void backupSql(String tableName, String query, List<String> columns) throws SQLException, FileException {
		// Records opvragen
		rs = stmt.executeQuery(query);
		// Veldwaarden doorlopen
		int count = 1;
		while (rs.next()) {
			count++;
		}
		if (count > 1) {
			rs.beforeFirst();
			count = 1;
			// Write-modus=ON
			write = true;
			// Doelbestand openen
			String timekey = getTimekey();
			output = new TargetFile(root, tableName + "-" + timekey + ".sql");
			// Definitie tabel
			screenOutput("INSERT INTO `fg_genial`.`" + tableName + "`(");
			String sep = "`";
			for (String name : columns) {
				screenOutput(sep);
				screenOutput(name);
				sep = "`,`";
			}
			screenOutput("`) VALUES\n\n");
			while (rs.next()) {
				sep = "'";
				screenOutput("\t(");
				for (String textEntry : columns) {
					screenOutput(sep);
					backupRecord(count, textEntry);
					sep = "', '";
				}
				screenOutput("'),\n");
				// indexOnly();
				count++;
			}
			// Doelbestand sluiten
			outputClose(false);
		}
		if (count == 1) {
			screenSystem(invalid_result + ": " + tableName + MSG_END);
		}
	}

	/**
	 * Wanneer slechts de index van "textentry" gewenst is
	 * 
	 * @throws SQLException
	 */
	private void indexOnly() throws SQLException {
		String value = rs.getString("text");
		int start = value.indexOf("<strong>");
		int finish = value.indexOf("</strong>");
		if (start > -1) {
			value = value.substring(start, finish + 9);
			screenOutput(value + "\n");
		}
	}

	private void backupRecord(int count, String name) throws SQLException, FileException {
		if (name.equals("id")) {
			screenOutput(count + "");
		} else {
			String value = backupGetValue(name);
			screenOutput(value + "");
		}
	}

	private String backupGetValue(String name) throws SQLException, FileException {
		Object obj = rs.getObject(name);
		Object value = obj.getClass().getName();
		if (obj instanceof Integer) {
			value = rs.getInt(name);
		} else if (obj instanceof String) {
			value = rs.getString(name);
			value = replaceEscape(value.toString());
		} else if (obj instanceof Long) {
			value = rs.getLong(name);
		} else if (obj instanceof Boolean) {
			value = rs.getBoolean(name);
			if (Boolean.parseBoolean(value.toString()) == true) {
				value = 1;
			} else {
				value = 0;
			}
		} else if (obj instanceof Timestamp) {
			value = rs.getTimestamp(name);
		} else {
			throw new FileException(invalid_type + ": " + obj.getClass() + MSG_END);
		}
		return "" + value;
	}

	private void customHelp(boolean whitelines) {
		// Toggle start
		toggleAll(true);
		// Business
		showHeader = false;
		showFooter = false;
		showColumnName = false;
		showRecordNumber = true;
		// Query voor opvragen toelichting uit database
		String bookmark = customQuery.get("help");
		String query = "SELECT * FROM textEntry WHERE user_name = '" + userName + "' AND date_input LIKE '%" + bookmark
				+ "%'";
		// Te tonen velden on output
		List<String> columns = getVarList("text");
		// Uitvoeren query
		searchExecuteQuery(query, columns, whitelines);
		// Toggle finish
		toggleAll(false);
	}

	private void helpInfo(String lineBreak) {
		screenOutput(LINE_FAT + lineBreak);
		screenOutput(i(2, "Genial textentries") + lineBreak);
		screenOutput(LINE_FAT + lineBreak);
		screenOutput(i(2, msg_language + currentLoc.getDisplayLanguage(currentLoc)) + lineBreak);
		screenOutput(i(2, msg_user + userName) + lineBreak);
		screenOutput(i(2, msg_table + table) + lineBreak);
		screenOutput(i(2, msg_root + root) + lineBreak);
		screenOutput(lineBreak);
	}

	private void linkItem(String bookmark, boolean whitelines) {
		// Toggle start
		toggleAll(true);
		// Query voor opvragen toelichting uit database
		String query = "SELECT * FROM textEntry WHERE user_name = '" + userName + "' AND date_input LIKE '%" + bookmark
				+ "%'";
		// Te tonen velden bij uitvoer
		List<String> columns = getVarList("text");
		// Uitvoeren query
		searchExecuteQuery(query, columns, whitelines);
	}

	private String searchComposeQuery(String... subQueries) {
		input = replace(input, "\'", "&#39;");
		String[] keys = getStringArray(input);
		int length = subQueries.length;
		List<String> queries = getVarList(subQueries);
		String result = queries.get(0);
		String start = "";
		for (String key : keys) {
			switch (length) {
			case 2:
				result += start + key + "%\'";
				break;
			case 3:
				result += start + key + "%\'";
				result += queries.get(2) + key + "%\'";
				break;
			case 4:
				result += start + key + "%\'";
				result += queries.get(2) + key + "%\'";
				result += queries.get(3) + key + "%\'";
				break;
			case 5:
				result += start + key + "%\'";
				result += queries.get(2) + key + "%\'";
				result += queries.get(3) + key + "%\'";
				result += queries.get(4) + key + "%\'";
				break;
			}
			start = queries.get(1);
		}
		return result;
	}

	private void searchExecuteQuery(String query, List<String> columns, boolean whitelines) {
		int count = 0;
		try {
			// Records opvragen
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count++;
			}
			if (count > 0) {
				searchShowResult(query, columns, whitelines);
			}
			read(); // Write-modus=OFF
		} catch (SQLException e) {
			screenFeedback(error_msg + ": " + e.getMessage().toLowerCase() + MSG_END);
			count = -1;
		}
		if (count == 0) {
			String msg = invalid_result + ": " + input + MSG_END + "" + query;
			if (whitelines) {
				screenFeedback(msg);
			} else {
				screenSystem(msg);
			}
		}
	}

	private void searchShowResult(String query, List<String> columns, boolean whitelines) throws SQLException {
		String lineBreak = screenLinebreak();
		String line = "";
		screenOutput("Query: " + query + lineBreak);
		rs.beforeFirst();
		// Veldwaarden doorlopen
		while (rs.next()) {
			if (showHeader) {
				// Header wordt getoond
				if (showRecordNumber) {
					// Record nummer wordt getoond
					line = LINE_THIN_FIRST + String.format("%-4s", rs.getString("id")) + LINE_THIN_LAST;
				} else {
					// Record nummer wordt niet getoond
					line = LINE_THIN;
				}
				screenOutput(line + lineBreak);
			}
			try {
				for (String column : columns) {
					if (showColumnName) {
						// Kolom (veld) naam wordt getoond
						screenOutput(column.toUpperCase() + lineBreak);
						screenOutput(lineBreak);
					}
					line = backupGetValue(column);
					line = replace(line, ", ", "&g&");
					String[] parts = line.split(",");
					for (String part : parts) {
						line = replace(part, "&g&", ", ");
						line = parseHTML(line);
						screenOutput(line + lineBreak);
					}
				}
			} catch (FileException e) {
				screenSystem(error_msg + ": " + e.getMessage());
			}
			if (showFooter) {
				// Footer wordt getoond
				screenOutput(LINE_THIN);
			}
		}
		// Schrijfcontrole
		if (write) {
			// Uitvoer afsluiten
			outputClose(whitelines);
		}
	}

	private void settingsRead() {
		String root = root_program;
		String fileName = iniName;
		SourceFile ini = settingsGet(root, fileName);
		// Controle bestaan INI
		if (ini == null) {
			try {
				// INI maken
				settingsInit(root, fileName);
				// INI doorgeven
				ini = settingsGet(root, fileName);
			} catch (FileException e) {
				// Andere fout
				screenFeedback(error_msg + ": " + e.getMessage() + MSG_END);
			}
		}
		// Opvragen waarden uit INI
		try {
			String key;
			String value;
			// Eerste regel lezen
			String line = ini.read();
			// Alle regels doorlopen
			while (line != null) {
				// Overslaan: regels met dash
				int ignore = line.indexOf("#");
				// Controle: parameter
				int pos = line.indexOf("=");
				// Controle: waarden zetten
				if (ignore == -1 && pos > -1) {
					int length = line.length();
					key = line.substring(0, pos);
					value = line.substring(pos + 1, length);
					// Waarden zetten
					settingsSetValue(key, value);
				}
				// Volgende regel
				line = ini.read();
			}
			// Afsluiten INI
			ini.terminate();
		} catch (FileNotFoundException e) {
			// INI niet gevonden
			screenFeedback(invalid_exist + ": " + ini.getFilePath() + MSG_END);
		} catch (FileException e) {
			// Andere fout
			screenFeedback(error_statement + ". " + e.getMessage() + MSG_END);
		}
	}

	private void settingsSetValue(String key, String value) {
		key = key.trim();
		value = value.trim();
		switch (key) {
		case "lang":
			mainLanguage(value, false);
			break;
		case "user":
			userName = value;
			break;
		case "schema":
			schema = value;
			break;
		case "table":
			table = value;
			break;
		case "root":
			mainRoot(value, false);
			break;
		case "header":
			showHeader = Boolean.parseBoolean(value);
			break;
		case "footer":
			showFooter = Boolean.parseBoolean(value);
			break;
		case "number":
			showRecordNumber = Boolean.parseBoolean(value);
			break;
		case "name":
			showColumnName = Boolean.parseBoolean(value);
			break;
		case "":
			break;
		default:
			int cmd = key.indexOf(":");
			if (cmd > -1) {
				String type = key.substring(0, cmd).trim();
				String command = key.substring(cmd + 1, key.length()).trim();
				int cnt = value.indexOf("|");
				String content = "";
				String help = "";
				if (cnt > -1) {
					content = value.substring(0, cnt).trim();
					help = value.substring(cnt + 1, value.length()).trim();
				} else {
					content = value;
				}
				switch (type) {
				case "backup":
					backupColumns.put(command, getArgList(content, ", "));
					if (!help.equals(""))
						backupHelp.put(command, help);
					break;
				case "custom":
					customQuery.put(command, content);
					if (!help.equals(""))
						customHelp.put(command, help);
					break;
				case "link":
					linkItem.put(command, content);
					if (!help.equals(""))
						linkHelp.put(command, help);
					break;
				}
			}
			break;
		}
	}

	private SourceFile settingsGet(String root, String fileName) {
		SourceFile result = new SourceFile(root, fileName);
		try {
			result.read();
		} catch (FileNotFoundException | FileException e) {
			result = null;
		}
		return result;
	}

	private void settingsInit(String root, String fileName) throws FileException {
		TargetFile target = new TargetFile(root, fileName);
		target.write("----------------------------------------------------------\n");
		target.write("Genial textentries\n");
		target.write("----------------------------------------------------------\n");
		target.write("\n");
		target.write("# General settings.\n");
		target.write("\n");
		target.write("lang=nl\n");
		target.write("user=zoetg00\n");
		target.write("schema=fg_genial\n");
		target.write("table=textentry\n");
		target.write("root=default\n");
		target.write("\n");
		target.write("# Layout of records.\n");
		target.write("\n");
		target.write("header=true\n");
		target.write("footer=true\n");
		target.write("number=true\n");
		target.write("name=false\n");
		target.write("\n");
		target.write("# Custom options\n");
		target.write("# Syntax : [type]:[command]=[content]|[help]\n");
		target.write("\n");
		target.write("# Custom backup. Must start with : \"backup:\".\n");
		target.write("\n");
		target.write("# Custom queries. Must start with : \"custom:\".\n");
		target.write("\n");
		target.write("# Custom bookmarks. Must start with : \"link:\".\n");
		target.write("\n");
		target.terminate();
	}

	private void settingsDefault() {
		if (sys_prompt != null) {
			read(); // Write-modus=OFF
		}
		setLanguageDefault();
		setDefaultRoot();
		table = "textEntry";
		userName = "zoetg00";
		showHeader = true;
		showFooter = true;
		showColumnName = false;
		showRecordNumber = true;
	}

	private void settingsReset() {
		settingsRead();
		mainHelp();
	}

	private void settingsWrite() {
		TargetFile target = new TargetFile(root_program, iniName);
		try {
			target.write("----------------------------------------------------------\n");
			target.write("Genial textentries\n");
			target.write("----------------------------------------------------------\n");
			target.write("\n");
			target.write("# General settings.\n");
			target.write("\n");
			target.write("lang=" + currentLoc.getLanguage() + "\n");
			target.write("user=" + userName + "\n");
			target.write("schema=" + schema + "\n");
			target.write("table=" + table + "\n");
			if (root.equals(root_default)) {
				target.write("root=default\n");
			} else if (root.equals(root_program)) {
				target.write("root=program\n");
			} else if (root.equals(root_desktop)) {
				target.write("root=desktop\n");
			} else {
				target.write("root=" + root + "\n");
			}
			target.write("\n");
			target.write("# Layout of records.\n");
			target.write("\n");
			target.write("header=" + showHeader + "\n");
			target.write("footer=" + showFooter + "\n");
			target.write("number=" + showRecordNumber + "\n");
			target.write("name=" + showColumnName + "\n");
			target.write("\n");
			target.write("# Custom options\n");
			target.write("# Syntax : [type]:[command]=[content]|[help]\n");
			target.write("\n");
			target.write("# Custom backup. Must start with : \"backup:\".\n");
			target.write("\n");
			String type = "backup:";
			for (String command : backupColumns.keySet()) {
				String line = "";
				String help = "";
				line += type + command + "=";
				List<String> content = backupColumns.get(command);
				String cols = "";
				String sep = "";
				for (String c : content) {
					cols += sep + c;
					sep = ", ";
				}
				line += cols;
				help = backupHelp.get(command);
				if (help != null) {
					line += "|" + help;
				}
				target.write(line + "\n");
			}
			target.write("\n");
			target.write("# Custom queries. Must start with : \"custom:\".\n");
			target.write("\n");
			type = "custom:";
			for (String command : customQuery.keySet()) {
				String line = "";
				String help = "";
				line += type + command + "=" + customQuery.get(command);
				help = customHelp.get(command);
				if (help != null) {
					line += "|" + help;
				}
				target.write(line + "\n");
			}
			target.write("\n");
			target.terminate();
			screenFeedback(msg_written + ": " + target.getFilePath() + MSG_END);
		} catch (FileException e) {
			screenFeedback(error_msg + ". " + e.getMessage() + MSG_END);
		}
	}

	private void setDefaultRoot() {
		String dir = root_default;
		if (Files.notExists(Paths.get(dir))) {
			// printFeedback(invalid_exist + ": " + dir + MSG_END);
		} else {
			root = dir;
		}
	}

	private void setLanguageDefault() {
		bundle = ResourceBundle.getBundle("language", defaultLoc);
		currentLoc = defaultLoc;
		setLanguageMsg();
	}

	private void setLanguageCurrent(String lang, boolean showHelp) {
		Locale loc = new Locale(lang);
		Locale.setDefault(loc);
		try {
			bundle = ResourceBundle.getBundle("language", loc);
			currentLoc = loc;
			setLanguageMsg();
			if (showHelp) {
				mainHelp();
			}
		} catch (MissingResourceException e) {
			bundle = ResourceBundle.getBundle("language", defaultLoc);
			currentLoc = defaultLoc;
			screenFeedback(bundle.getString("invalid_source") + loc.getDisplayLanguage(loc));
			Locale.setDefault(defaultLoc);
			setLanguageMsg();
		}
	}

	private void setLanguageMsg() {
		error_msg = bundle.getString("error_msg");
		error_connection = bundle.getString("error_connection");
		error_reason = bundle.getString("error_reason");
		error_statement = bundle.getString("error_statement");
		invalid_command = bundle.getString("invalid_command");
		invalid_delete = bundle.getString("invalid_delete");
		invalid_exist = bundle.getString("invalid_exist");
		invalid_result = bundle.getString("invalid_result");
		invalid_size = bundle.getString("invalid_size");
		invalid_source = bundle.getString("invalid_source");
		invalid_string = bundle.getString("invalid_string");
		invalid_type = bundle.getString("invalid_type");
		reader_no_close = bundle.getString("reader_no_close");
		reader_no_read = bundle.getString("reader_no_read");
		writer_no_close = bundle.getString("writer_no_close");
		writer_no_flush = bundle.getString("writer_no_flush");
		writer_no_write = bundle.getString("writer_no_write");
		msg_settings = bundle.getString("msg_settings");
		msg_settings_save = bundle.getString("msg_settings_save");
		msg_settings_reset = bundle.getString("msg_settings_reset");
		msg_settings_default = bundle.getString("msg_settings_default");
		msg_connection_closed = bundle.getString("msg_connection_closed");
		msg_connection_opened = bundle.getString("msg_connection_opened");
		msg_custom = bundle.getString("msg_custom");
		msg_custom_explanation = bundle.getString("msg_custom_explanation");
		msg_custom_all = bundle.getString("msg_custom_all");
		msg_input = bundle.getString("msg_input");
		msg_backup = bundle.getString("msg_backup");
		msg_backup_explanation = bundle.getString("msg_backup_explanation");
		msg_backup_all = bundle.getString("msg_backup_all");
		msg_deleted = bundle.getString("msg_deleted");
		msg_language = bundle.getString("msg_language");
		msg_link = bundle.getString("msg_link");
		msg_link_explanation = bundle.getString("msg_link_explanation");
		msg_link_all = bundle.getString("msg_link_all");
		msg_novalue = bundle.getString("msg_novalue");
		msg_root = bundle.getString("msg_root");
		msg_table = bundle.getString("msg_table");
		msg_useless = bundle.getString("msg_useless");
		msg_user = bundle.getString("msg_user");
		msg_written = bundle.getString("msg_written");
		sys_help = bundle.getString("sys_help");
		sys_prompt = bundle.getString("sys_prompt");
		if (write) {
			sys_prompt = sys_prompt.toUpperCase();
		}
	}

	private void toggleAll() {
		showHeader = toggle(showHeader);
		showFooter = toggle(showFooter);
		showColumnName = toggle(showColumnName);
		showRecordNumber = toggle(showRecordNumber);
	}

	private void toggleAll(boolean on) {
		if (on) {
			toggledHeader = showHeader;
			toggledFooter = showFooter;
			toggledColumnName = showColumnName;
			toggledRecordNumber = showRecordNumber;
		} else {
			showHeader = toggledHeader;
			showFooter = toggledFooter;
			showColumnName = toggledColumnName;
			showRecordNumber = toggledRecordNumber;
		}
	}

	private boolean toggle(boolean b) {
		if (b) {
			return false;
		} else {
			return true;
		}
	}

	private void clearScreen() {
		// Werkt niet
		// try {
		// Runtime.getRuntime().exec("cmd /c cls");
		// } catch (IOException e1) {
		// System.out.println("Unable to clear screen...");
		// }
		// Werkt ook niet
		// try {
		// new ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
		// .waitFor();
		// } catch (InterruptedException | IOException e) {
		// System.out.println("Unable to clear screen...");
		// }
	}

	private List<String> getVarList(String... args) {
		List<String> result = new ArrayList<String>();
		for (String s : args) {
			result.add(s);
		}
		return result;
	}

	private List<String> getArgList(String str, String separator) {
		List<String> result = new ArrayList<String>();
		String[] args = str.split(separator);
		for (String s : args) {
			result.add(s);
		}
		return result;
	}

	private String[] getStringArray(String line) {
		String quote = "\"";
		int pos = line.indexOf(quote);
		String add = "";
		String remainder = line;
		List<String> list = new ArrayList<String>();
		String[] result = null;
		if (pos > -1) {
			while (pos > -1) {
				if (pos == 0) {
					add = remainder.substring(1, remainder.length());
					pos = add.indexOf(quote);
					add = add.substring(0, pos).trim();
					if (remainder.length() - add.length() == 2) {
						remainder = "";
					} else {
						remainder = remainder.substring(add.length() + 3, remainder.length());
					}
				} else {
					add = remainder.substring(0, pos).trim();
					remainder = remainder.substring(pos + 1, remainder.length());
				}
				if (add.length() > 1)
					list.add(add);
				pos = remainder.indexOf(quote);
			}
			remainder = remainder.trim();
			if (!remainder.equals("")) {
				list.add(remainder);
			}
			result = list.toArray(new String[0]);
		} else {
			result = remainder.split(" ");
		}
		return result;
	}

	private String getTimekey() {
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		String result = stamp.toString();
		result = replace(result, "-", "");
		result = replace(result, " ", "");
		result = replace(result, ":", "");
		result = replace(result, ".", "");
		return result;
	}

	private String parseHTML(String text) {
		String result = text;
		result = replace(result, "</p><p>", "\n\n");
		result = replace(result, "<br>", "\n");
		result = replace(result, "<li>", "\t");
		result = replace(result, "</blockquote>", "\n\n");
		result = replace(result, "<ol>", "\n\n");
		result = replace(result, "</ol>", "\n");
		result = replace(result, "</li>", "\n");
		result = replace(result, "<tr><td>", "\n\t");
		result = replace(result, "<td>", "\t");
		result = replace(result, "&nbsp;", " ");
		result = replace(result, "&ndash;", "-");
		result = replace(result, "&rsquo;", "\'");
		result = replace(result, "&ldquo;", "\"");
		result = replace(result, "&rdquo;", "\"");
		result = StringEscapeUtils.unescapeHtml4(result);
		result = result.replaceAll("\\<.*?>", "");
		result = replace(result, " \n", "\n");
		result = replace(result, "\n\n\n", "\n\n");
		return result;
	}

	private String replace(String text, String from, String to) {
		String result = text;
		int replace = result.indexOf(from);
		while (replace > -1) {
			result = result.replace(from, to);
			replace = result.indexOf(from);
		}
		return result;
	}

	private String replaceEscape(String text) {
		String result = text;
		// \t Insert a tab in the text at this point.
		result = replace(result, "\\t", "&#92;t");
		// \b Insert a backspace in the text at this point.
		result = replace(result, "\\b", "&#92;b");
		// \n Insert a newline in the text at this point.
		result = replace(result, "\\n", "&#92;n");
		// \r Insert a carriage return in the text at this point.
		result = replace(result, "\\r", "&#92;r");
		// \f Insert a formfeed in the text at this point.
		result = replace(result, "\\f", "&#92;f");
		// \' Insert a single quote character in the text at this point.
		result = replace(result, "'", "&#39;");
		// \" Insert a double quote character in the text at this point.
		// result = replace(result, "\"", "&#34;");
		// \\ Insert a backslash character in the text at this point.
		result = replace(result, "\\", "&#92;");
		return result;
	}

	private class Sorter implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	}

	class Database {
		// Velden
		private Connection connection;
		private Statement statement;

		// Constructor
		public Database(String dbase, String usr, String pwd) {
			this.connection = getMySQLConnection(dbase, usr, pwd);
			try {
				if (connection != null) {
					this.statement = connection.createStatement();
				}
			} catch (SQLException e) {
				screenFeedback(error_statement + ". " + e.getMessage() + MSG_END);
			}
		}

		private Connection getMySQLConnection(String dbase, String usr, String pwd) {
			Connection connection = null;
			String url = "jdbc:mysql://localhost:3306/" + dbase;
			try {
				connection = DriverManager.getConnection(url, usr, pwd);
			} catch (SQLException e) {
				String msg = e.getMessage();
				int pos = msg.indexOf("\n");
				screenSystem("");
				screenSystem(error_connection + MSG_END);
				if (pos > -1) {
					msg = msg.substring(0, pos);
				}
				screenSystem(error_reason + ": " + msg + MSG_END);
				screenSystem("");
				// running = false;
			}
			return connection;
		}

		public Connection getConnection() {
			return connection;
		}

		public Statement getStatement() {
			return statement;
		}
	}

	public class FileException extends Exception {
		private static final long serialVersionUID = 1L;

		FileException() {
			super();
		}

		FileException(String s) {
			super(s);
		}
	}

	private class SourceFile {
		private BufferedReader reader = null;
		private String rootPath = null;
		private String fileName = null;
		private String filePath = null;

		private String getRootPath() {
			return rootPath;
		}

		private String getFileName() {
			return fileName;
		}

		private String getFilePath() {
			return filePath;
		}

		private SourceFile(String rootPath, String fileName) {
			this.rootPath = rootPath;
			this.fileName = fileName;
			this.filePath = rootPath + SEP + fileName;
		}

		private void getBufferedReader() throws FileNotFoundException {
			if (reader == null) {
				this.reader = new BufferedReader(getFileReader());
			}
		}

		private FileReader getFileReader() throws FileNotFoundException {
			FileReader fr = new FileReader(filePath);
			return fr;
		}

		private String read() throws FileNotFoundException, FileException {
			String line = "";
			getBufferedReader();
			try {
				line = reader.readLine();
			} catch (IOException e) {
				throw new FileException(reader_no_read + ": " + e.getMessage());
			}
			return line;
		}

		private void terminate() throws FileException {
			close();
		}

		private void close() throws FileException {
			try {
				reader.close();
			} catch (IOException e) {
				throw new FileException(reader_no_close);
			}
		}
	}

	private class TargetFile {
		private BufferedWriter writer = null;
		private String rootPath = null;
		private String fileName = null;
		private String filePath = null;

		private String getRootPath() {
			return rootPath;
		}

		private String getFileName() {
			return fileName;
		}

		private String getFilePath() {
			return filePath;
		}

		private TargetFile(String rootPath, String fileName) {
			this.rootPath = rootPath;
			this.fileName = fileName;
			this.filePath = rootPath + SEP + fileName;
		}

		private void getBufferedWriter() throws FileException {
			if (writer == null) {
				this.writer = new BufferedWriter(getPrintWriter());
			}
		}

		private PrintWriter getPrintWriter() throws FileException {
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(filePath);
			} catch (FileNotFoundException e) {
				throw new FileException(invalid_exist + ": " + filePath);
			}
			return pw;
		}

		private void write(String line) throws FileException {
			getBufferedWriter();
			try {
				writer.write(line);
			} catch (IOException e) {
				throw new FileException(writer_no_write + ": " + line);
			}
		}

		private void terminate() throws FileException {
			flush();
			close();
		}

		private void flush() throws FileException {
			try {
				writer.flush();
			} catch (IOException e) {
				throw new FileException(writer_no_flush);
			}
		}

		private void close() throws FileException {
			try {
				writer.close();
			} catch (IOException e) {
				throw new FileException(writer_no_close);
			}
		}
	}

	private class MyVisitor extends SimpleFileVisitor<Path> {
		private final FileSystem fs = FileSystems.getDefault();
		private PathMatcher pm;
		int deleted = 0;

		public MyVisitor(String fileName, String fileExtension) {
			String glob = "glob:";
			if (!fileName.equals("")) {
				glob += "*{" + fileName + "}*";
			}
			if (!fileExtension.equals("")) {
				glob += "*.{" + fileExtension + "}";
			}
			pm = fs.getPathMatcher(glob);
		}

		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			if (pm.matches(file.getFileName())) {
				String fileName = file.getParent() + "\\" + file.getFileName();
				try {
					Files.delete(file);
					screenSystem(msg_deleted + fileName + MSG_END);
					deleted += 1;
				} catch (IOException e) {
					screenFeedback(error_msg + ": " + e.getMessage() + MSG_END);
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}
}