/**
 * 
 */
package com.pagss.lms.exceptions;

/**
 * @author John
 *
 */
public class SessionExpiredException extends LmsException {

	private static final long serialVersionUID = -925270679283369156L;
	
	public SessionExpiredException() {
		super();
	}
	
	public SessionExpiredException (String message) {
		super(message);
	}
	
	public SessionExpiredException (String message, Throwable cause) {
		super(message, cause);
	}
	
	public SessionExpiredException (Throwable cause) {
		super(cause);
	}
	
}
