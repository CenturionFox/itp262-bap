package edu.pti.students.itp262.bap.servlet;

import javax.servlet.http.HttpSession;

/**
 * @author bem9
 *
 */
public class Common
{
	/**
	 * @param userSession
	 */
	public static void clearAllWarningMessages(HttpSession userSession)
	{
		setOperationFailMessage(userSession, "");
	}
	
	/**
	 * @param userSession
	 * @param message
	 */
	public static void setOperationFailMessage(HttpSession userSession, String message)
	{
		userSession.setAttribute("logInError", message);
	};
}
