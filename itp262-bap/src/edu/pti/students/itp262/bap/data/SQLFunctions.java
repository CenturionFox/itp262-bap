package edu.pti.students.itp262.bap.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages SQL execution tasks.
 * 
 * @author Conner Layton, Bridger Maskrey
 * @version 2.0.1
 */
public class SQLFunctions
{
	/**
	 * Runs on class resolution. Initializes the JDBC Driver.
	 */
	static
	{
		try
		{
			// Returns the Class object associated with the class or interface with the given string name, using
			// the given class loader. This method attempts to locate, load, and link the class or interface.
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("PSQL Drivers not loaded.");
		}
	}
	
	/**
	 * Executes the given SQL command.
	 * @param sqlCommand The command to execute.
	 * @return Whether or not command execution was successful.
	 */
	public static void executeSQL(String sqlCommand) throws SQLException, IOException
	{
		Statement statement = null;			// object used to execute DML on our dataBase
		// Establish a connection between this servlet and our data source
		// the DriverManager will attempt to locate a suitable driver from amongst those loaded at initialization
		// and those loaded explicitly using the same classloader as the current applet or application.

		// Open the connection.  The connection is automatically managed with try-with-resource.
		try ( Connection connection = DriverManager.getConnection(	// Opens the connection.
			"jdbc:postgresql://127.0.0.1:5432/postgres",			// URL - a database URL of the form jdbc:subprotocol:subname
			"postgres",												// user - the database user on whose behalf the connection is being made
			"") )													// password - The password for the user.
		{
			// The object used for executing a static SQL statement and returning the results it produces
			statement = connection.createStatement();   			// throws sql exception

			// execute the DML command and returns the ResultSet
			statement.execute(sqlCommand);			// throws sql and timeoutexception (if a max time is set)
		}

		return;
	}

	/**
	 * Executes the given SQL query.
	 * @param sqlQuery The query to execute.
	 * @return a ResultSet containing all results of the query.
	 */
	public static ResultSet executeQuery(String sqlQuery) throws SQLException, IOException
	{
		Statement statement = null;			// object used to execute DML on our dataBase
		ResultSet values = null;			// Holds all values returned by the query.

		// Establish a connection between this servlet and our data source
		// the DriverManager will attempt to locate a suitable driver from amongst those loaded at initialization
		// and those loaded explicitly using the same classloader as the current applet or application.

		// works for console apps.
		try(Connection connection = DriverManager.getConnection(
			"jdbc:postgresql://127.0.0.1:5432/postgres",	// URL - a database URL of the form jdbc:subprotocol:subname
			"postgres",										// user - the database user on whose behalf the connection is being made
			""))											// password - the user's password mine is "numbnutz21"
		{
			// The object used for executing a static SQL statement and returning the results it produces
			statement = connection.createStatement();   			// throws sql exception

			// execute the DML command and returns the ResultSet
			values = statement.executeQuery(sqlQuery);			// throws sql and timeoutexception (if a max time is set)
		}
		
		return values;
	}
}
