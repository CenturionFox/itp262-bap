/**
 * 
 */
package edu.pti.students.itp262.bap.gen;

import java.io.IOException;
import java.io.PrintWriter;
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
	private static final String DIVIDER = "----------------------------------------------------------------";

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
	
	/**
	 * Program main function.
	 * Controls all the program's functionality. 
	 * @param args
	 */
	public static void main(String[] args)
	{
		out.println("== NAME GENERATOR ==");
		out.println("Loading names from config...");
		
		LoadNames();
		
		Random random = new Random(System.currentTimeMillis());
		
		int times = 50;
		
		if(args != null && args.length > 0)
		{
			try
			{
				times = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException nfe)
			{
				out.println("Error parsing argument 1: not an int.");
			}
		}
		
		out.println("Inserting " + times + " names into database.");
		out.println(DIVIDER);
		
		out.println("Attempt create debug table...");
		createDebugTable();
		out.println(DIVIDER);
		
		for(int i = 0; i < times; i++)
		{
			buildAndWriteStudent(random);
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
		catch (IOException e)
		{
			e.printStackTrace();
			pass = "null";
		}
		
		long phoneNum = 0;
		
		for(int j = 0; j < 10; j++)
		{
			phoneNum += (j == 10 ? 1 : 0) + random.nextInt(j == 10 ? 9 : 10) * (Math.pow(10, j));
		}
		
		if(phoneNum < 1000000000)
		{
			phoneNum += 1000000000;
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
		
		//region Class Insertion 
		CourseType gened = CourseType.GEN_ED;
		CourseType actual = new CourseType[]{CourseType.PROGRAMMING, CourseType.MULTIMEDIA, CourseType.CAD}[random.nextInt(3)];
		
		java.sql.Date date = java.sql.Date.valueOf("1970-01-01"); //UNIX EPOCH
		try
		{
			out.println("SELECT DISTINCT startDate FROM scheduledClasses;");
			ResultSet result = SQLFunctions.executeQuery("SELECT DISTINCT startDate FROM scheduledClasses;");
			if(result.next())
			{
				date = result.getDate(1);
			}
			else
			{
				throw new Exception("NO RESULTS RETURNED");
			}
		}
		catch (Exception e)
		{
			out.println("class reg for " + fName + " " + lName + " failed; start date query failure");
			e.printStackTrace();
			return;
		}
		
		// DO GEN ED SIGNUP
		String classQuery = "SELECT classes.classId as classid FROM classes INNER JOIN scheduledClasses " +
			"ON scheduledClasses.classId = classes.classId " +
			"AND scheduledClasses.startDate = '" + date.toString() + "' " +
			"AND type = '" + gened.toString() + "';";
		out.println(classQuery);
		if(!signUpStudent(random, fName, lName, id, date, classQuery)) return;
		
		// DO SPECIALIZED
		classQuery = "SELECT classes.classId as classid FROM classes INNER JOIN scheduledClasses " +
			"ON scheduledClasses.classId = classes.classId " +
			"AND scheduledClasses.startDate = '" + date.toString() + "' " +
			"AND type = '" + actual.toString() + "';";
		out.println(classQuery);
		if(!signUpStudent(random, fName, lName, id, date, classQuery)) return;
		
		out.println("User " + fName + " " + mInit + " " + lName + " registered and signed up");
		out.println(DIVIDER);
	}

	private static boolean signUpStudent(Random random, String fName, String lName, String id, java.sql.Date date, String classQuery)
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