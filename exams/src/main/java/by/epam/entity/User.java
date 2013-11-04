package by.epam.entity;

import java.io.Serializable;

/**
 * Abstract class for user entity
 * @author Dmitry Shanko
 *
 */
public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String roleStringValue = "role is ";
	private int idroles;
	private int idusers;
	private String name;
	private String surname;
	/**
	 * Public default constructor.<p>
	 * this.idusers = 0;<p>
	 * this.name = "";<p>
	 * this.surname = "";
	 */
	public User()
	{
		this.idusers = 0;
		this.name = "";
		this.surname = "";
	}
	/**
	 * Getter for idusers field.
	 * @return the idusers
	 */
	public int getIdusers() 
	{
		return idusers;
	}
	/**
	 * Setter for idusers field if param > 0.
	 * @param idusers the idusers to set
	 */
	public void setIdusers(int idusers) 
	{
		if (idusers > 0)
		{
			this.idusers = idusers;
		}
	}
	/**
	 * @return the idroles
	 */
	public int getIdroles() 
	{
		return idroles;
	}
	/**
	 * @param idroles the idroles to set
	 */
	public void setIdroles(int idroles) 
	{
		this.idroles = idroles;
	}
	/**
	 * Getter for name field.
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}
	/**
	 * Setter for name field if param is not null.
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
	 * Getter for surname field.
	 * @return the surname
	 */
	public String getSurname() 
	{
		return surname;
	}
	/**
	 * Setter for surname field if param is not null.
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) 
	{
		if (null != surname)
		{
			this.surname = surname;
		}
	}
	/**
	 * Overridden Object.toString()<p>
	 * @return (idusers + ": ").concat(name).concat(" ").concat(surname);
	 */
	@Override
	public String toString() 
	{		
		return (idusers + ": ").concat(name).concat(" ").concat(surname).concat(User.roleStringValue).concat(idroles + "");
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return ((null != obj) && (obj instanceof User) <p>
	 *			&& (((User)obj).getIdusers() == this.idusers) <p>
	 *			&& (((User)obj).getName() == this.name) <p>
	 *			&& (((User)obj).getSurname() == this.surname)<p>
	 *			&& (((User)obj).getIdroles() == this.idroles));
	 */
	@Override
	public boolean equals(Object obj) 
	{
		return ((null != obj) && (obj instanceof User) 
				&& (((User)obj).getIdusers() == this.idusers) 
				&& (((User)obj).getName() == this.name) 
				&& (((User)obj).getSurname() == this.surname)
				&& (((User)obj).getIdroles() == this.idroles));
	}
	/**
	 * Overridden Object.hashCode()<p>
	 * final int hash = 11;<p>
	 * int result = 1; <p>
	 * result = hash * result + idusers;<p>
	 * result = hash * result + idroles;<p>
	 *	result = hash * result + ((null != name) ? name.hashCode() : 1);<p>
	 *	result = hash * result + ((null != surname) ? surname.hashCode() : 1);<p>
	 * @return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 11;
		int result = 1;
		result = hash * result + idusers;
		result = hash * result + idroles;
		result = hash * result + ((null != name) ? name.hashCode() : 1);
		result = hash * result + ((null != surname) ? surname.hashCode() : 1);
		return result;
	}
}
