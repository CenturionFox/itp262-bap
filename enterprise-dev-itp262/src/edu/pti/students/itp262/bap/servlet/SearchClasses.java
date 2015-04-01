package edu.pti.students.itp262.bap.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.pti.students.itp262.bap.data.CourseType;
import edu.pti.students.itp262.bap.data.SQLFunctions;
import edu.pti.students.itp262.bap.session.beans.CourseListing;

/**
 * @author bem9
 *
 */
public class SearchClasses extends HttpServlet
{

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.doPost(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		session.setAttribute("searchResults", null);
		//TODO Search error reporting value.
		
		CourseType type = CourseType.getCourseForType(req.getParameter("classType"));
		boolean includeGenEd = req.getParameter("includeGenEd") != null && type != CourseType.GEN_ED && type != null;
		
		StringBuilder query = new StringBuilder("SELECT * FROM classes WHERE");
		
		if(type != null) query.append(" type = '").append(type.toString()).append("'");
		if(includeGenEd) query.append(" OR type = '").append(CourseType.GEN_ED.toString()).append("'");
		
		query.append(";");
		
		List<CourseListing> courses = new ArrayList<CourseListing>();
		
		try
		{
			ResultSet result = SQLFunctions.executeQuery(query.toString());
			
			while(result.next())
			{
//				CourseListing courseListing = new CourseListing();
//				
//				courseListing.setClassId(result.getString("classid"));
//				courseListing.setStartDate(startDate);
				
				String dateQuery = "SELECT DISTINCT startDate FROM scheduledClasses INNER JOIN classes ON classes.classId = scheduledClasses.classId AND scheduledClasses.startDate > '" + new java.sql.Date(new java.util.Date().getTime()) + "';";
				
				ResultSet result2 = SQLFunctions.executeQuery(dateQuery);
				
				while(result2.next())
				{
					CourseListing courseListing = new CourseListing();
					
					courseListing.setClassId(result.getString("classid"));
					courseListing.setStartDate(result2.getDate("startDate"));
					courseListing.setType(CourseType.getCourseForType(result.getString("type")));
					
					courses.add(courseListing);
				}
			}
		}
		catch(Exception e)
		{
			resp.sendError(256898);
			session.setAttribute("searchResults", null);
			return;
		}
		
		session.setAttribute("searchResults", courses);
		
		resp.sendRedirect(req.getRequestURI());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
