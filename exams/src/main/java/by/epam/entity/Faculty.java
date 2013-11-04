package by.epam.entity;

import java.io.Serializable;

import by.epam.resources.LocalizationRef;

public class Faculty implements LocalizationRef, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int maxstudents;

	public Faculty()
	{
		this.setId(0);
		this.setName("");
	}

	/**
	 * @return the id
	 */
	public int getId() 
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) 
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) 
	{
		if (null != name)
		{
			this.name = name;
		}
	}
	/**
	 * @return the maxstudents
	 */
	public int getMaxstudents() {
		return maxstudents;
	}

	/**
	 * @param maxstudents the maxstudents to set
	 */
	public void setMaxstudents(int maxstudents) {
		this.maxstudents = maxstudents;
	}	
	/**
	 * Overridden Object.toString()<p>
	 * @return this.name;
	 */
	@Override
	public String toString() 
	{
		return this.getName();
	}
	/**
	 * Overridden Object.hashCode()<p>
	 *	final int hash = 49;<p>
	 *	int result = 55;<p>
	 *	result = hash * result + id;<p>
	 *	result = hash * result + ((null != name) ? name.hashCode() : 1);<p>
	 *	result = hash * result + maxstudents;<p>
	 *	@return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 49;
		int result = 55;
		result = hash * result + id;
		result = hash * result + ((null != name) ? name.hashCode() : 1);
		result = hash * result + maxstudents;
		return result;
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return ((null != obj)<p>
	 *		&& (obj instanceof Faculty)<p>
	 *		&& (((Faculty)obj).getId() == this.getId())<p>
	 *		&& (((Faculty)obj).getMaxstudents() == this.getMaxstudents())<p>
	 *		&& (((Faculty)obj).getName().equals(this.getName())));<p>
	 */
	@Override
	public boolean equals(Object obj) 
	{
		return ((null != obj)
				&& (obj instanceof Faculty)
				&& (((Faculty)obj).getId() == this.getId())
				&& (((Faculty)obj).getMaxstudents() == this.getMaxstudents())
				&& (((Faculty)obj).getName().equals(this.getName())));
	}
}
