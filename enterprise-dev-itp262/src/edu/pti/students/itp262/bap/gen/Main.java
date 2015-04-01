/**
 * 
 */
package edu.pti.students.itp262.bap.gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.attributestudios.api.util.crypto.HashUtils;
import com.attributestudios.api.util.crypto.HashUtils.HashingType;

import edu.pti.students.itp262.bap.data.CourseType;
import edu.pti.students.itp262.bap.data.SQLFunctions;

/**
 * Generates names and inserts them into
 * 	the student database.
 * Names are randomly generated from a file list.
 * 
 * @author Bridger Maskrey
 *
 */

// #define DEBUG

/**
 * Dirty, dirty program quickly written to generate random student
 * 	data and insert it into the database.
 * 
 * @author Bridger Maskrey (maskreybe@live.com)
 *
 */
public class Main
{
	private static final int PHONE_PREFIX = 1000000000;
	private static final String DIVIDER = "----------------------------------------------------------------";
	private static final int TIMES = 500;
	private static final int[] ORDINAL_MAX = new int[]{135, 194, 120, 459};
	private static final int[][] COURSE_MAXES = new int[][] {
		new int[]{45, 35, 20, 35},
		new int[]{42, 42, 80, 30},
		new int[]{30, 25, 25, 40},
		new int[]{150, 109, 75, 125}
	};

	/**
	 * Output print writer.
	 */
	private static final PrintWriter out = new PrintWriter(System.out, true);
	
	private static String[] firstNames;
	private static String[] lastNamePrefix;
	private static String[] lastNameSuffix;
	
	private static String[] roadNames;
	private static String[] roadSuffix;
	
	private static String[] townNames;
	private static String[] townSuffix;
	
	private static String[] states;
	
	private static String mInitials;
	
	private static String passwordValidChars;
	
	private static int studentCount;
	
	private static int[][] counts = new int[][] {
		new int[4], new int[4], new int[4], new int[4]
	};
	
	private static int[] ordinalCounts = new int[4];
	
	/**
	 * Program main function.
	 * Controls all the program's functionality. 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException
	{
		out.println("== NAME GENERATOR ==");
		out.println("Loading names from config...");
		
		LoadNames();
		
		Random random = new Random(System.currentTimeMillis());
		
//		if(args != null && args.length > 0)
//		{
//			try
//			{
//				times = Integer.parseInt(args[0]);
//			}
//			catch(NumberFormatException nfe)
//			{
//				out.println("Error parsing argument 1: not an int.");
//			}
//		}
		
		out.println("Inserting " + TIMES + " names into database.");
		out.println(DIVIDER);
		
		out.println("Attempt create debug table...");
		createDebugTable();
		out.println(DIVIDER);
		
		for(int i = 0; i < TIMES; i++)
		{
			buildAndWriteStudent(random);
			Thread.sleep(10);
		}
		
		out.println(DIVIDER);
		for(CourseType ct : CourseType.values())
		{
			out.print(ct.toString() + ": " + ordinalCounts[ct.ordinal()] + " (");
			for(int i = 0; i < 4; i++)
			{
				out.print(counts[ct.ordinal()][i] + ", ");
			}
			out.println(")");
		}
	}

	private static void buildAndWriteStudent(Random random)
	{
		StringBuilder query = new StringBuilder(50);
		
		query.delete(0, query.length());
		
		query.append("INSERT INTO students VALUES ('");
		
		//region Name Section
		String fName = firstNames[random.nextInt(firstNames.length)];
		String mInit = new String(new char[]{mInitials.charAt(random.nextInt(mInitials.length()))});
		
		String lName = firstNames[random.nextInt(firstNames.length)];
		String suffix = lastNameSuffix[random.nextInt(lastNameSuffix.length)];
		String prefix = lastNamePrefix[random.nextInt(lastNamePrefix.length)];
		
		lName += random.nextInt(lastNameSuffix.length + 1) != 0 && (lName + suffix).length() < 20  ? suffix : "";
		
		if(random.nextInt(3) == 0 && (prefix + lName).length() < 20)
		{
			lName = prefix + lName;
		}
		
		fName = fName.replace("'", "''");
		lName = lName.replace("'", "''");
		
		String[] lSplit = lName.split(" ");
		
		
		String id = fName.charAt(0) + mInit + lSplit[lSplit.length - 1].charAt(0);
		
		
		id = id.toLowerCase();
		
		String idQuery = "SELECT COUNT(studentid) as count FROM Students WHERE studentid LIKE '" + id + "%';";
		out.println(idQuery);
		try
		{
			ResultSet result = SQLFunctions.executeQuery(idQuery);
			if (result.next()) 
			{
				id += (result.getInt("count") + 1);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//endregion

		//region Password Section
		String pass = "";
		
		for(int j = 0; j < random.nextInt(10) + 5; j++)
		{
			char rand = passwordValidChars.charAt(random.nextInt(passwordValidChars.length()));
			pass += rand;
		}
					
		String passUnhash = pass;
		
		try
		{
			pass = HashUtils.hashAs(pass, HashingType.SHA256);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			pass = "74234e98afe7498fb5daf1f36ac2d78acc339464f950703b8c019892f982b90b";
		}
		
		long phoneNum = 0;
		
		for(int j = 0; j < 10; j++)
		{
			phoneNum += (j == 10 ? 1 : 0) + random.nextInt(j == 10 ? 9 : 10) * (Math.pow(10, j));
		}
		
		if(phoneNum < PHONE_PREFIX)
		{
			phoneNum += PHONE_PREFIX;
		}
		
		query.append(id + "','" + fName + "','" + mInit + "','" + lName + "'," + phoneNum + ",'" + pass +"');");
					
		out.println("Inserting user " + id + " (" + fName.replace("''", "'") + " " + lName.replace("''", "'") + ", " + passUnhash + ") into database.");
		
		out.println(query.toString());
		try
		{
			SQLFunctions.executeSQL(query.toString());
		}
		catch (Exception e)
		{
			;
		}
		
		query.delete(0, query.length());
		//endregion

		//region Student Address
		int streetAddress = random.nextInt(1000);
		int zip = random.nextInt(90000) + 10000;
		
		String street = streetAddress + " " + roadNames[random.nextInt(roadNames.length)] + " " + roadSuffix[random.nextInt(roadSuffix.length)];
		String city = townNames[random.nextInt(townNames.length)] + townSuffix[random.nextInt(townSuffix.length)];
		String state = states[random.nextInt(states.length)];
		
		String SQLCommand = "INSERT INTO StudentAddress VALUES ('" + id + "','" + street + "','" + (random.nextBoolean() ? "Apt. Number " + random.nextInt(601) : "") + "','" + city + "','" + state + "'," + zip + ");";
		out.println(SQLCommand);
		try
		{
			out.println("INSERT INTO Debug_Passwords VALUES ( '" + id + "', '" + passUnhash + "');");
			SQLFunctions.executeSQL("INSERT INTO Debug_Passwords VALUES ( '" + id + "', '" + passUnhash + "');");
			
			SQLFunctions.executeSQL(SQLCommand);
		}
		catch(Exception e)
		{
			;
		}
		// endregion
		
		List<CourseType> currentStudentCourses = new ArrayList<CourseType>();
		
		if(ordinalCounts[CourseType.GEN_ED.ordinal()] < ORDINAL_MAX[CourseType.GEN_ED.ordinal()])
		{
			currentStudentCourses.add(CourseType.GEN_ED);
			ordinalCounts[CourseType.GEN_ED.ordinal()]++;
		}
		
		if(studentCount >= 51)
		{
			if(studentCount < ORDINAL_MAX[CourseType.PROGRAMMING.ordinal()] + 51)
			{
				currentStudentCourses.add(CourseType.PROGRAMMING);
				ordinalCounts[CourseType.PROGRAMMING.ordinal()]++;
			}
			else if(studentCount < ORDINAL_MAX[CourseType.PROGRAMMING.ordinal()] + 51 + ORDINAL_MAX[CourseType.MULTIMEDIA.ordinal()])
			{
				currentStudentCourses.add(CourseType.MULTIMEDIA);
				ordinalCounts[CourseType.MULTIMEDIA.ordinal()]++;
			}
			else
			{
				currentStudentCourses.add(CourseType.CAD);
				ordinalCounts[CourseType.CAD.ordinal()]++;
			}
		}
		
		
		for (CourseType actual : currentStudentCourses)
		{
			// DO SPECIALIZED
			String classQuery = "SELECT classes.classId as classid FROM classes INNER JOIN scheduledClasses " +
				"ON scheduledClasses.classId = classes.classId " +
				"AND type = '" + actual.toString() + "';";
			out.println(classQuery);
			if(!signUpStudent(random, fName, lName, id, new Date(System.currentTimeMillis()), classQuery, actual)) return;
		}
		
		out.println("User " + fName + " " + mInit + " " + lName + " registered and signed up");
		out.println(DIVIDER);
		
		studentCount++;
	}

	private static boolean signUpStudent(Random random, String fName, String lName, String id, java.sql.Date date, String classQuery, CourseType courseType)
	{
		String classId;
		try
		{
			ResultSet result = SQLFunctions.executeQuery(classQuery);
			
			List<String> classIds = new ArrayList<String>();
			
			while(result.next())
			{
				classIds.add(result.getString("classid"));
			}
			
			classId = classIds.get(random.nextInt(classIds.size()));
			
//			switch(courseType)
//			{
//			case GEN_ED:
//				for(int i = 0; i < genEdMax.length; i++)
//				{
//					if(genEdCount[i] < genEdMax[i])
//					{
//						genEdCount[i]++;
//						classId = classIds.get(i);
//						break;
//					}
//				}
//				break;
//				
//			default:
//				
//			}
			
			for(int i = 0; i < COURSE_MAXES[courseType.ordinal()].length; i++)
			{
				if(counts[courseType.ordinal()][i] < COURSE_MAXES[courseType.ordinal()][i])
				{
					counts[courseType.ordinal()][i]++;
					classId = classIds.get(i);
					break;
				}
			}
			
			String insertClass = "INSERT INTO studentClasses VALUES('" + id + "', '" + classId + "', '" + date.toString() +"');";
			out.println(insertClass);
			SQLFunctions.executeSQL(insertClass);
		}
		catch (Exception e)
		{
			out.println("class insertion for " + fName + " " + lName + " failed");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static void createDebugTable()
	{
		try
		{
			SQLFunctions.executeSQL("DROP TABLE Debug_Passwords CASCADE;");
			SQLFunctions.executeSQL("CREATE TABLE Debug_Passwords ( id varchar(5), password text, foreign key (id) references Students(studentid) );");
		}
		catch(Exception e) 
		{
			;
		}
	}
	
	private static void LoadNames()
	{
		Properties config = new Properties();
		try
		{
			config.load(Main.class.getClassLoader().getResourceAsStream("config/names.cfg"));
		}
		catch (IOException e)
		{
			RuntimeException e1 = new RuntimeException("Name load from config failed.", e);
			throw e1;
		}
		
		String firstNames = config.getProperty("firstNames");
	    String lastNameSuffix = config.getProperty("lastNameSuffix", "");
        String lastNamePrefix = config.getProperty("lastNamePrefix", "");
        String roadNames = config.getProperty("roadNames", "");
        String roadSuffix = config.getProperty("roadSuffix", "");        String townNames = config.getProperty("townNames", "");        String townSuffix = config.getProperty("townSuffix", "");        String states = config.getProperty("states", "");        Main.mInitials = config.getProperty("mInitials", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");        Main.passwordValidChars = config.getProperty("passwordValidChars", "PASSWORD");
        
		if(firstNames == null)
		{
			RuntimeException e = new RuntimeException("Name load from config failed.");
			e.setStackTrace(Thread.currentThread().getStackTrace());
			throw e;
		}
        
		Main.firstNames = firstNames.split("/");
		Main.lastNameSuffix = lastNameSuffix.split("/");
		Main.lastNamePrefix = lastNamePrefix.split("/");
		Main.roadNames = roadNames.split("/");
		Main.roadSuffix = roadSuffix.split("/");
		Main.townNames = townNames.split("/");
		Main.townSuffix = townSuffix.split("/");
		Main.states = states.split("/");
	}
}