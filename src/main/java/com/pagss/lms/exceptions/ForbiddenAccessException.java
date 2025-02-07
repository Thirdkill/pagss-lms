/**
 * 
 */
package com.pagss.lms.exceptions;

/**
 * @author John
 *
 */
public class ForbiddenAccessException extends LmsException {

	private static final long serialVersionUID = -6867166126566558119L;
	
	public ForbiddenAccessException() {
		super();
	}
	
	public ForbiddenAccessException (String message) {
		super(message);
	}

	public ForbiddenAccessException (String message, Throwable cause) {
		super(message, cause);
	}
	
	public ForbiddenAccessException (Throwable cause) {
		super(cause);
	}
}
