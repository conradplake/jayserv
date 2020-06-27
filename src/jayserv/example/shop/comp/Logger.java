package jayserv.example.shop.comp;

public class Logger {

	/**
	 * Stupid demo logger
	 */
	public static void log(String message, Throwable t) {
		System.out.print(message);
		if (t != null)
			t.printStackTrace();
	}
}
