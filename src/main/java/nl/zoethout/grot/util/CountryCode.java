package nl.zoethout.grot.util;

import java.util.HashMap;
import java.util.Map;

public enum CountryCode {
	AF("Afghanistan"),
	AX("Åland Eilanden"),
	AL("Albanië"),
	DZ("Algerije"),
	AS("Amerikaans Samoa"),
	AD("Andorra"),
	AO("Angola"),
	AI("Anguilla"),
	AQ("Antarctica"),
	AG("Antigua en Barbuda"),
	AR("Argentinië"),
	AM("Armenië"),
	AW("Aruba"),
	AU("Australië"),
	AT("Oostenrijk"),
	AZ("Azerbaijan"),
	BS("Bahamas"),
	BH("Bahrein"),
	BD("Bangladesh"),
	BB("Barbados"),
	BY("Belarus"),
	BE("België"),
	BZ("Belize"),
	BJ("Benin"),
	BM("Bermuda"),
	BT("Bhutan"),
	BO("Bolivië"),
	BQ("Bonaire, Sint Eustatius en Saba"),
	BA("Bosnië-Herzegovina"),
	BW("Botswana"),
	BV("Bouvet Eiland"),
	BR("Brazilië"),
	IO("Brits-Indisch Oceaangebied"),
	BN("Brunei Darussalam"),
	BG("Bulgarije"),
	BF("Burkina Faso"),
	BI("Burundi"),
	KH("Cambodja"),
	CM("Cameroen"),
	CA("Canada"),
	CV("Kaap Verdië"),
	KY("Kaaiman Eilanden"),
	CF("Centraal Afrikaanse Republiek"),
	TD("Tsjaad"),
	CL("Chili"),
	CN("China"),
	CX("Kerstmis Eiland"),
	CC("Cocos (Keeling Eilanden)"),
	CO("Colombia"),
	KM("Comoros"),
	CG("Congo"),
	CK("Cook Eilanden"),
	CR("Costa Rica"),
	CI("Cote d\'Ivoire (Ivory Coast)"),
	HR("Croatië (Hrvatska)"),
	CU("Cuba"),
	CW("Curaçao"),
	CY("Cyprus"),
	CZ("Tjechische Republiek"),
	DK("Denemarken"),
	DJ("Djibouti"),
	DM("Dominica"),
	DO("Dominicaanse Republiek"),
	TP("Oost Timor"),
	EC("Ecuador"),
	EG("Egypte"),
	SV("El Salvador"),
	GQ("Equatoriaal Guinea"),
	ER("Eritrea"),
	EE("Estland"),
	ET("Ethiopië"),
	FK("Falkland Eilanden (Malvinas)"),
	FO("Faroe Eilanden"),
	FJ("Fiji"),
	FI("Finland"),
	FR("Frankrijk"),
	GF("Frans Guyana"),
	PF("Frans Polynesië"),
	TF("Frans Zuidelijk gebied"),
	GA("Gabon"),
	GM("Gambia"),
	GE("Georgië"),
	DE("Duitsland"),
	GH("Ghana"),
	GI("Gibraltar"),
	GR("Griekenland"),
	GL("Groenland"),
	GD("Grenada"),
	GP("Guadeloupe"),
	GU("Guam"),
	GT("Guatemala"),
	GG("Guernsey"),
	GN("Guinea"),
	GW("Guinea-Bissau"),
	GY("Guyana"),
	HT("Haïti"),
	HM("Heard en McDonald Eilanden"),
	HN("Honduras"),
	HK("Hong Kong"),
	HU("Hungary"),
	IS("Iceland"),
	IN("India"),
	ID("Indonesië"),
	IR("Iran"),
	IQ("Irak"),
	IE("Ierland"),
	IM("Eiland van Man"),
	IL("Israël"),
	IT("Italië"),
	JM("Jamaica"),
	JP("Japan"),
	JE("Jersey"),
	JO("Jordanië"),
	KZ("Kazachstan"),
	KE("Kenia"),
	KI("Kiribati"),
	KP("Korea (Noord) (Volksrepubliek)"),
	KR("Korea (Zuid) (Republiek)"),
	KW("Kuweit"),
	KG("Kirgizistan (Kirgizische Republiek)"),
	LA("Laos"),
	LV("Letland"),
	LB("Libanon"),
	LS("Lesotho"),
	LR("Liberia"),
	LY("Libya"),
	LI("Liechtenstein"),
	LT("Litouwen"),
	LU("Luxemburg"),
	MO("Macao"),
	MK("Macedonië"),
	MG("Madagascar"),
	MW("Malawi"),
	MY("Maleisië"),
	MV("Malediven"),
	ML("Mali"),
	MT("Malta"),
	MH("Marshall Eilanden"),
	MQ("Martinique"),
	MR("Mauritanië"),
	MU("Mauritius"),
	YT("Mayotte"),
	MX("Mexico"),
	FM("Micronesië"),
	MD("Moldovië"),
	MC("Monaco"),
	MN("Mongolië"),
	ME("Montenegro"),
	MS("Montserrat"),
	MA("Marokko"),
	MZ("Mozambique"),
	MM("Myanmar"),
	NA("Namibië"),
	NR("Nauru"),
	NP("Nepal"),
	NL("Nederland"),
	NC("Nieuw Caledonië"),
	NZ("Nieuw Zeeland"),
	NI("Nicaragua"),
	NE("Niger"),
	NG("Nigeria"),
	NU("Niue"),
	NF("Norfolk Eiland"),
	MP("Noordelijke Marianen Eilanden"),
	NO("Norwegen"),
	OM("Oman"),
	PK("Pakistan"),
	PW("Palau"),
	PS("Palestine"),
	PA("Panama"),
	PG("Papua Nieuw Guinea"),
	PY("Paraguay"),
	PE("Peru"),
	PH("Filippijnen"),
	PN("Pitcairn"),
	PL("Polen"),
	PT("Portugal"),
	PR("Puerto Rico"),
	QA("Qatar"),
	RE("Reunion"),
	RO("Roemenië"),
	RU("Russische Federatie"),
	RW("Rwanda"),
	SH("Sint Helena, Ascension en Tristan"),
	KN("Sint Kitts en Nevis"),
	LC("Sint Lucia"),
	PM("Sint Pierre en Miquelon"),
	VC("Sint Vincent en The Grenadines"),
	WS("Samoa"),
	SM("San Marino"),
	ST("Sao Tome en Principe"),
	SA("Saudi Arabië"),
	SN("Senegal"),
	RS("Servië"),
	SC("Seychelles"),
	SL("Sierra Leone"),
	SG("Singapore"),
	SX("Sint Maarten (Nederlands deel)"),
	SK("Slovakije (Slovaakse Republiek)"),
	SI("Slovenië"),
	SB("Solomon Eilanden"),
	SO("Somalië"),
	ZA("Zuid-Afrika"),
	GS("Zuid Georgië en Sandwich Eilanden"),
	SU("Sovjet Unie (voormalig)"),
	ES("Spanje"),
	LK("Sri Lanka"),
	SD("Soedan"),
	SR("Suriname"),
	SJ("Svalbard en Jan Mayen Eilanden"),
	SZ("Swaziland"),
	SE("Zweden"),
	CH("Zwitserland"),
	SY("Syrië"),
	TW("Taiwan"),
	TJ("Tajikistan"),
	TZ("Tanzania"),
	TH("Thailand"),
	TL("Timor-Leste"),
	TG("Togo"),
	TK("Tokelau"),
	TO("Tonga"),
	TT("Trinidad en Tobago"),
	TN("Tunesië"),
	TR("Turkey"),
	TM("Turkmenistan"),
	TC("Turks en Caicos Eilanden"),
	TV("Tuvalu"),
	UG("Oeganda"),
	UA("Oekraïne"),
	AE("Verenigde Arabische Emiraten"),
	GB("Verenigd Koninkrijk (Groot-Britannië)"),
	UM("Verenigde Staten Kleine Buiteneilanden"),
	US("Verenigde Staten"),
	UY("Uruguay"),
	UZ("Uzbekistan"),
	VU("Vanuatu"),
	VA("Vaticaanstad"),
	VE("Venezuela"),
	VN("Viet Nam"),
	VG("Maagden Eilanden (Brits)"),
	VI("Maagden Eilanden (Amerikaans)"),
	WF("Wallis en Futuna Eilanden"),
	EH("Westelijke Sahara"),
	YE("Yemen"),
	ZM("Zambia"),
	ZW("Zimbabwe");

	private static final Map<String, CountryCode> nameMap = new HashMap<String, CountryCode>();
	private static final Map<String, String> codeMap = new HashMap<String, String>();
	
    static {
        for (final CountryCode cc : values()) {
            nameMap.put(cc.getName(), cc);
            codeMap.put(cc.toString(), cc.getName());
        }
    }
    
    /**
     * Country name
     */
    private final String name;
    
    private CountryCode(final String name) {
		this.name = name;
	}

    /**
     * Get the country name.
     *
     * @return The country name.
     */
    public String getName() {
		return name;
	}
    
    /**
     * Get the country name.
     * 
     * @param code
     * @return The country name.
     */
    public static String getName(String code) {
    	return codeMap.get(code);
	}

    /**
     * Get the country code.
     * 
     * @param name
     * @return The country code.
     */
    public static String getCode(String name) {
    	return nameMap.get(name).toString();
    }

	public static void main(String[] args) {
		System.out.println(CountryCode.getCode("Zimbabwe"));
		System.out.println(CountryCode.getName("AD"));
		for (CountryCode s:CountryCode.values()) {
			System.out.println(s.getName());
		}
	}

}
