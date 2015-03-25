package edu.pti.students.itp262.bap.session.beans;

/**
 * @author bem9
 *
 */
public class CourseListing
{
	/**
	 * 
	 */
	private String classId;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	public CourseListing()
	{
		this.classId = "";
		this.type = "";
		this.name = "";
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
	public String getType()
	{
		return this.type;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
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
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 43;
		int result = 1;
		result = prime * result + ((this.classId == null) ? 0 : this.classId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		CourseListing other = (CourseListing) obj;
		if (this.classId == null)
		{
			if (other.classId != null) { return false; }
		}
		else if (!this.classId.equals(other.classId)) { return false; }
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "CourseListing [classId=" + this.classId + "]";
	}
	
	
}
