package reflexivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Reflex {

	public static final String MISS_CLASS_NAME = "You must specify the class name as first argument in execution command line.";
	public static final String CLASS_NAME_INVALID = "Failed to load the class of name '%s'. Cheks if it is present in the classpath.";
	public static final String MISS_OUTPUT = "You must specify the output file name as second argument in execution command line.";
	public static final String OUTPUT_UNUSABLE = "The supplied file name '%s' is unuable. The message is `%s`";

	/** For execution */
	public static void main(String[] args) {
		String className = null;
		String output = null;
		PrintStream print = null;
		String error;
		try {
			className = args[0];
			output = args[1];
			Class clazz = Class.forName(className);
			File file = new File(output);
			print = new PrintStream(file);
		} catch (ClassNotFoundException e) {
			if (className == null) {
				error = MISS_CLASS_NAME;
			} else {
				error = String.format(CLASS_NAME_INVALID, new Object[] { className });
			}
		} catch (FileNotFoundException | SecurityException e) {

		} catch (Exception e) {
			if (className == null) {
				error = MISS_CLASS_NAME;
			} else if (output == null) {
				error = MISS_OUTPUT;
			}
			System.err.println(error);
		} finally {
			if (print != null) {
				print.close();
			}
		}
	}

}
