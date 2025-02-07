package com.pagss.lms.configurations;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/***
 * 
 * @author Gerald
 *
 */
@WebListener
public class SessionConfig implements HttpSessionListener {
	
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(60*60);
		System.out.println("=====Session Started======");
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("=====Session Expired======");
	}
}
