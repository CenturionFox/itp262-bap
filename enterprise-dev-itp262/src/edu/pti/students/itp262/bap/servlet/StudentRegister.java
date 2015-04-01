package edu.pti.students.itp262.bap.servlet;

import java.io.IOException;
import java.sql.ResultSet;

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
 * @author bem9
 *
 */
public class StudentRegister extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7053926894777944794L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession userSession = req.getSession();
		Common.clearAllWarningMessages(userSession);
		
		String sqlCommand = "";

		// get the values from the webpage
		String fName = req.getParameter("firstName").replace("'", "''");
		String mInitial = req.getParameter("middleInitial");
		String lName = req.getParameter("lastName").replace("'", "''");

		String phoneNumber = req.getParameter("phoneNumber");
		
		phoneNumber = phoneNumber.replaceAll("[-() ]+", "");

		String password = HashUtils.hashAs(req.getParameter("password"), HashingType.SHA256);
		
		String[] lSplit = lName.split(" ");
		
		String id = fName.charAt(0) + mInitial + lSplit[lSplit.length - 1].charAt(0);

		id = id.toLowerCase();
		
		sqlCommand = "SELECT COUNT(studentid) FROM Students WHERE studentid LIKE '" + id + "%';";

		try
		{
			ResultSet result = SQLFunctions.executeQuery(sqlCommand);
			if (result.next()) id += (result.getInt(1) + 1);
		}
		catch (Exception e)
		{
			registrationFail(resp, userSession, e);
			return;
		}

		// creating the insert command from the values above
		sqlCommand = "INSERT INTO Students VALUES ('" + id + "','" + fName + "','" + mInitial + "','" + lName + "','" + phoneNumber + "','" + password + "');";

		try
		{
			SQLFunctions.executeSQL(sqlCommand);
		}
		catch (Exception e)
		{
			registrationFail(resp, userSession, e);
			return;
		}
		

		String streetAddress1 = req.getParameter("stAddress1");
		String streetAddress2 = req.getParameter("stAddress2");
		if (streetAddress2 == null) streetAddress2 = "";
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");

		// creating the insert command from the values above
		sqlCommand = "INSERT INTO StudentAddress VALUES ('" + id + "','" + streetAddress1 + "','" + streetAddress2 + "','" + city + "','" + state + "'," + zip + ");";

		try
		{
			SQLFunctions.executeSQL(sqlCommand);
		}
		catch (Exception e)
		{
			this.registrationFail(resp, userSession, e);
			return;
		}
		
		Certificate cert = new Certificate();
		
		cert.setId(id);
		cert.setName(fName + " " + lName);
		
		userSession.setAttribute("cert", cert);
		
		resp.sendRedirect("/studentaccess/");
	}

	private void registrationFail(HttpServletResponse resp, HttpSession userSession, Exception e) throws IOException
	{
		e.printStackTrace();
		Common.setOperationFailMessage(userSession, "Registration failed: " + e.getMessage());
		resp.sendRedirect("/studentaccess/registration");
		return;
	}
}
