package edu.pti.students.itp262.bap.session.beans;

import java.sql.Date;

import edu.pti.students.itp262.bap.data.CourseType;

/**
 * @author bem9
 *
 */
public class CourseListing
{
	private String classId;

	private CourseType type;
	
	private Date startDate;
	
	public CourseListing()
	{
		this.classId = "";
		this.type = null;
		this.startDate = new Date(0);
	}

	/**
	 * @return the classId
	 */
	public String getClassId()
	{
		return this.classId;
	}

	/**
	 * @return the type
	 */
	public CourseType getType()
	{
		return this.type;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate()
	{
		return this.startDate;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(String classId)
	{
		this.classId = classId;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(CourseType type)
	{
		this.type = type;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.classId == null) ? 0 : this.classId.hashCode());
		result = prime * result + ((this.startDate == null) ? 0 : this.startDate.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CourseListing other = (CourseListing) obj;
		if (this.classId == null)
		{
			if (other.classId != null) return false;
		}
		else if (!this.classId.equals(other.classId)) return false;
		if (this.startDate == null)
		{
			if (other.startDate != null) return false;
		}
		else if (!this.startDate.equals(other.startDate)) return false;
		if (this.type != other.type) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "CourseListing [classId=" + this.classId + ", type=" + this.type + ", startDate=" + this.startDate + "]";
	}
}
