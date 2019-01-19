package nl.zoethout.grot.samples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum Locators {
	UH("home"), UT("test"), UI("login"), UO("logout"), CM("module"), CQ(
			"question"), CR("review"), CC("correction"), CF("final"), AA(
			"admin-modules-assign"), AE("admin-modules-edit"), AN(
			"admin-modules-new"), AM("admin-modules"), AQ("admin-questions"), AI(
			"import"), AH("admin"), AP("input"), AB("text"), AS("search");

	private static final Map<Locators, ArrayList<Locators>> locations = new HashMap<Locators, ArrayList<Locators>>();

	private Locators locator;
//	private AttributeProvider attr;

	private String strClass = getClass().getName();

	static {
		ArrayList<Locators> writable;

		writable = getWritable(new Locators[] { AH, AB, CR, CF, CM });
		locations.put(UH, writable);

		writable = getWritable(new Locators[] { UH, CM, CF });
		locations.put(UO, writable);

		writable = getWritable(new Locators[] { UH, AH, AB });
		locations.put(CM, writable);

		writable = getWritable(new Locators[] { CM });
		locations.put(CQ, writable);

		writable = getWritable(new Locators[] { CQ, CC });
		locations.put(CR, writable);

		writable = getWritable(new Locators[] { CR });
		locations.put(CC, writable);

		writable = getWritable(new Locators[] { CR });
		locations.put(CF, writable);

		writable = getWritable(new Locators[] { UH, AN, AE, AA, AQ, AB, CM });
		locations.put(AH, writable);

		writable = getWritable(new Locators[] { AH, AE, AA, AQ });
		locations.put(AN, writable);

		writable = getWritable(new Locators[] { AH, AN, AA, AQ });
		locations.put(AE, writable);

		writable = getWritable(new Locators[] { AH, AN, AE, AQ });
		locations.put(AA, writable);

		writable = getWritable(new Locators[] { AH, AN, AE, AA });
		locations.put(AQ, writable);

		writable = getWritable(new Locators[] { AB });
		locations.put(AP, writable);

		writable = getWritable(new Locators[] { AB });
		locations.put(AS, writable);

		writable = getWritable(new Locators[] { UH, AP, AB, AH, CM });
		locations.put(AB, writable);
	}

	private static ArrayList<Locators> getWritable(Locators[] allowables) {
		ArrayList<Locators> result = new ArrayList<Locators>();
		for (Locators locator : allowables) {
			result.add(locator);
		}
		return result;
	}

	private final String value;

	Locators(String locator) {
		this.value = locator;
	}

	public String value() {
		return value;
	}

	public boolean check(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		// this.attr = new GenialSAProvider(req);
//		this.attr = AttributeProviderImpl.getProvider(req);
//		locator = attr.getSALocator();
		locator = null;
		String strLocator = getValueNotNull(locator);

		boolean result = false;
		if (locator == null) {
			locator = Locators.UH;
			if (req.getMethod().equals("GET")) {
//				attr.setSALocator(locator);
			}
			// Terug naar home
			result = false;
			res.sendRedirect(strLocator);
		} else {
			// Controleer locatie
			if (locator.equals(this)) {
				// Locatie is gelijk aan locator, doorgaan
				result = true;
			} else if (isAllowed()) {
				// Locator mag worden overschreven, doorgaan
//				attr.setSALocator(this);
				result = true;
			} else {
				// Locatie is ongelijk locator, terug naar locator
				res.sendRedirect(strLocator);
				result = false;
			}
		}
		return result;
	}

	private boolean isAllowed() {
		ArrayList<Locators> allowable = locations.get(this);
		boolean result = false;
		if (allowable != null && allowable.contains(locator)) {
			result = true;
		}
		return result;
	}

	public String getValueNotNull(Locators locator) {
		String result = "";
		if (locator != null) {
			result = locator.value;
		}
		return result;
	}

	protected void codeInfo(String strMethod, String strMessage) {
		System.out.println("TEST: (" + strClass + "-" + strMethod + ") "
				+ strMessage);
	}

	protected void codeInfo(String strClass, String strMethod, String strMessage) {
		System.out.println("TEST: (" + strClass + "-" + strMethod + ") "
				+ strMessage);
	}

	protected void codeInfo() {
		System.out.println("========================");
	}
}
