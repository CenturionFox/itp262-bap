package edu.pti.students.itp262.bap.data;

/**
 * Represents the valid class subject values present in the school database.
 * 
 * @author Bridger Maskrey {maskreybe@live.com}
 * @version 1.0.1
 */
public enum CourseType
{
	/**
	 * Represents the database type value for a programming course.
	 * @since 1.0.0
	 */
	PROGRAMMING("Programming"),
	
	/**
	 * Represents the database type value for a multimedia course.
	 * @since 1.0.0
	 */
	MULTIMEDIA("Multimedia"),
	
	/**
	 * Represents the database type value for a CAD course.
	 * @since 1.0.0
	 */
	CAD("CAD"),
	
	/**
	 * Represents the database type value for a Gen Ed course.
	 * @since 1.0.0
	 */
	GEN_ED("General Education");

	/* (Non-Javadoc)
	 * Database type value
	 */
	private String courseType;
	
	/**
	 * Generates a course type enum value with
	 * 	a specified course type.
	 * @param courseType The database type value
	 * 	of the course.
	 */
	private CourseType(String courseType)
	{
		this.courseType = courseType;
	}
	
	/**
	 * Returns the database type value for this course.
	 * @return The database type value for this course.
	 * @since 1.0.0
	 * @deprecated Since 1.0.1. Use {@link CourseType#toString()} instead.
	 */
	public String getClassType()
	{
		return this.courseType;
	}
	
	/**
	 * Returns the database course type value of this enum.
	 */
	@Override
	public String toString()
	{
		return this.courseType;
	}
}
