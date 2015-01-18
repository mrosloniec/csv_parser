package main.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader {

	public String readLine(String format, Object... args) throws IOException {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		System.out.print(String.format(format, args));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();
	}

	public char[] readPassword(String format, Object... args)
			throws IOException {
		if (System.console() != null) {
			return System.console().readPassword(format, args);
		}
		return this.readLine(format, args).toCharArray();
	}

}
