/**
 * 
 */
package com.pagss.lms.exceptions;

public class LmsException extends Exception {

	private static final long serialVersionUID = 7459293151900260630L;

	public LmsException() {
		super();
	}
	
	public LmsException (String message) {
		super(message);
	}
	
	public LmsException (String message, Throwable cause) {
		super(message, cause);
	}
	
	public LmsException (Throwable cause) {
		super(cause);
	}
}
