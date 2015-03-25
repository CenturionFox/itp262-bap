package edu.pti.students.itp262.bap.session.beans;

/**
 * @author bem9
 *
 */
public class Certificate
{
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	public Certificate()
	{
		this.id = "";
		this.name = "";
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
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
		final int prime = 23;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
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
		Certificate other = (Certificate) obj;
		if (this.id == null)
		{
			if (other.id != null) return false;
		}
		else if (!this.id.equals(other.id)) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Certificate [id=" + this.id + ", name=" + this.name + "]";
	}

	
}
