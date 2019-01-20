package nl.zoethout.grot.mytest;

public interface MyTest {

	default public void testInfo(String strClass, String strMethod, String strMessage) {
		System.out.println("*** (" + strClass + "-" + strMethod + ") " + strMessage);
	}

}
