package tp.pr5.cityLoader.cityLoaderExceptions;

import java.io.IOException;

/**
 * Exception thrown by the map loader when the file does not adhere to the file format
 * @author Juan Manuel Carrera García 
 *
 */
@SuppressWarnings("serial")
public class WrongCityFormatException extends IOException {
	/**
	 * Constructor without parameters (no message is given)
	 */
	public WrongCityFormatException() {}
	
	/**
	 * The exception thrown is created with a problem message.
	 * @param arg0 User-friendly string that explains the error.
	 */
	public WrongCityFormatException(String msg) {
		super (msg);
	}

	/**
	 * Constructor to create the exception with a nested cause and an error message.
	 * @param arg0 User-friendly string that explains the error.
	 * @param arg1 Nested exception.
	 */
 	public WrongCityFormatException(String msg, Throwable arg) {
 		super (msg, arg);
 	} 
 
 	/**
 	 * Constructor to create the exception with a nested cause.
 	 * @param arg0 Nested exception.
 	 */
	public WrongCityFormatException(Throwable arg) {
		super (arg);
	} 
}
