package reflexivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

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
		String error = null;
		try {
			className = args[0];
			output = args[1];
			Class<?> clazz = Class.forName(className);
			File file = new File(output);
			print = new PrintStream(file);
			print.print(Modifier.toString(clazz.getModifiers()));
			print.print(" ");
			print.print(clazz.getCanonicalName());
			Class<?> sup = clazz.getSuperclass();
			if (sup != null) {
				print.print(" extends ");
				print.print(sup.getCanonicalName());
			}
			Class<?>[] interfaces = clazz.getInterfaces();
			if (interfaces != null && interfaces.length != 0) {
				print.print(" implements ");
				print.print(Arrays.asList(interfaces).stream().map(e -> e.getCanonicalName())
						.collect(Collectors.joining(", ")));
			}
			print.println("{");
			Field[] fields = clazz.getDeclaredFields();
			print.println();
			if(fields != null && fields.length != 0) {
				for(Field f : fields) {
					print.print(Modifier.toString(f.getModifiers()));
					print.print(" ");
					print.print(f.getType().getCanonicalName());
					print.print(" ");
					print.print(f.getName());
					print.println(";");
				}
			}
			Constructor<?>[] constructors = clazz.getDeclaredConstructors();
			if(constructors!=null && constructors.length != 0) {
				print.print(Arrays.asList(constructors).stream().map(c->c.toString()).collect(Collectors.joining(";\n")));
				print.println(";\n");
			}
			Method[] methods = clazz.getDeclaredMethods();
			if(methods!=null && methods.length != 0) {
				print.print(Arrays.asList(methods).stream().map(m->m.toString()).collect(Collectors.joining(";\n")));
				print.println(";");
			}
			print.print("}");
		} catch (ClassNotFoundException e) {
			if (className == null) {
				error = MISS_CLASS_NAME;
			} else {
				error = String.format(CLASS_NAME_INVALID, new Object[] { className });
			}
		} catch (FileNotFoundException | SecurityException e) {
			if (output == null) {
				error = MISS_OUTPUT;
			} else {
				error = String.format(OUTPUT_UNUSABLE, output, e.getMessage());
			}
		} catch (Exception e) {
			if (className == null) {
				error = MISS_CLASS_NAME;
			} else if (output == null) {
				error = MISS_OUTPUT;
			} else {
				error = e.getMessage();
			}
		} finally {
			if (print != null) {
				print.close();
			}
		}
		if (error != null) {
			System.err.println(error);
		}
	}

}
