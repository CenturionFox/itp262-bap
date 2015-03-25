package edu.pti.students.itp262.bap.data;

public enum ClassType
{
	PROGRAMMING("Programming"),
	MULTIMEDIA("Multimedia"),
	CAD("CAD"),
	GEN_ED("General Education");

	private String classType;
	
	private ClassType(String classType)
	{
		this.classType = classType;
	}
	
	/**
	 * @return the classType
	 */
	@Deprecated
	public String getClassType()
	{
		return this.classType;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return this.classType;
	}
}
