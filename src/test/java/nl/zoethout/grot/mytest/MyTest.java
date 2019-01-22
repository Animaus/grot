package nl.zoethout.grot.mytest;

public interface MyTest {

	default public void devInfo(String strClass, String strMethod, String strMessage) {
		System.out.println("|\t(" + strClass + "-" + strMethod + ") " + strMessage);
	}

}
