/**
 * 
 */
package edu.pti.students.itp262.bap.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.attributestudios.api.util.crypto.HashUtils;
import com.attributestudios.api.util.crypto.HashUtils.HashingType;

import edu.pti.students.itp262.bap.data.SQLFunctions;
import edu.pti.students.itp262.bap.session.beans.Certificate;

/**
 * Handles the logging in and out of the users.
 * 
 * @author Bridger Maskrey
 * @version 1.0.0
 */
public class StudentLoginFunctions extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7910911533258320568L;

	/**
	 * Executes login
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.doPost(req, resp);
	}
	
	/**
	 * Executes login
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		
		HttpSession userSession = req.getSession();
		Common.clearAllWarningMessages(userSession);
		
		Certificate current = (Certificate)userSession.getAttribute("cert");
		
		// If we are logged in, log out. Otherwise, log in.
		if(current == null)
		{
			// Gather page fields
			String id = req.getParameter("userName");
			String passHash = HashUtils.hashAs(req.getParameter("passwordInput"), HashingType.SHA256);
			String name = "";
			
			try
			{
				ResultSet query = SQLFunctions.executeQuery("SELECT password AS hash FROM students WHERE studentid = '" + id + "';");
				
				String hashFromDb;
				
				if(query.next() && (hashFromDb = query.getString("hash")) != null)
				{
					if(!hashFromDb.equals(passHash))
					{
						// Password mismatch.
						Common.setOperationFailMessage(userSession, "The ID and password do not match. Please try again.");
						resp.sendRedirect("/studentaccess/");
						return;
					}
					
					//#debug debug
//@					System.out.println("Login successful.");
				}
				else
				{
					// ID not extant in database.
					Common.setOperationFailMessage(userSession, "The specified ID did not exist. Please try again, or if you are a new student, register first.");
					resp.sendRedirect("/studentaccess/");
					return;
				}
				
				// Password and ID matched, continue
				query = SQLFunctions.executeQuery("SELECT fName || ' ' || lName AS commonName FROM students WHERE studentid = '" + id + "';");
				
				if(query.next())
				{
					name = query.getString("commonName");
					
					//#debug debug
//@					System.out.println("Name populated");
				}
				else
				{
					// Well, that was odd; the user managed to get this far but does not exist.
					Common.setOperationFailMessage(userSession, "Unable to verify user. Please contact an administrator.");
					resp.sendRedirect("/studentaccess/");
					return;
				}
			}
			catch(SQLException e)
			{
				// Query executed improperly
				Common.setOperationFailMessage(userSession, "An error occured while logging in: " + e.getMessage() +". Please try again later.");
				resp.sendRedirect("/studentaccess/");
				return;
			}
			
			current = new Certificate();
			current.setId(id);
			current.setName(name);
			
			userSession.setAttribute("cert", current);
		}
		else
		{
			// Log out
			userSession.setAttribute("cert", null);
		}
		
		resp.sendRedirect("/studentaccess/");
	}
}
