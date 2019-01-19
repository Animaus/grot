package nl.zoethout.grot.domain;

import java.util.Vector;

public class Administrator extends UserPreferred {
	public Vector<Event> aanmaken;
	public Vector<Organization> onderhouden;
	
	public Administrator() {
		super();
	}

}