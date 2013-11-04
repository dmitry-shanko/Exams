package by.epam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for DTO of Exam entity fields.
 * @author Dmitry Shanko
 *
 */
public class ExamWriteBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private List<Integer> idfaculties;
	private String content;

	public ExamWriteBean()
	{
		this.setName("");
		this.setIdfaculties(new ArrayList<Integer>());
		this.setContent("");
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
	 * @return the idfaculties
	 */
	public List<Integer> getIdfaculties() 
	{
		return idfaculties;
	}
	/**
	 * @param idfaculties the idfaculties to set
	 */
	public void setIdfaculties(List<Integer> idfaculties) 
	{
		if (null != idfaculties)
		{
			this.idfaculties = idfaculties;
		}
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * Overridden Object.toString()
	 * @return name.concat(" . ").concat(content);
	 */
	@Override
	public String toString() 
	{
		return name.concat(" . ").concat(content);
	}
	/**
	 * Overridden Object.hashCode()<p>
	 *	final int hash = 123;<p>
	 *	int result = 452;<p>
	 *	result = hash * result + ((null != name) ? name.hashCode() : 1);<p>
	 *	result = hash * result + ((null != content) ? content.hashCode() : 1);<p>
	 *	result = hash * result + ((null != idfaculties) ? idfaculties.hashCode() : 1);<p>
	 *	@return result;
	 */
	@Override
	public int hashCode() {
		final int hash = 123;
		int result = 452;
		result = hash * result + ((null != name) ? name.hashCode() : 1);
		result = hash * result + ((null != content) ? content.hashCode() : 1);
		result = hash * result + ((null != idfaculties) ? idfaculties.hashCode() : 1);
		return result;
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return ((null != obj)
	 *			&& (obj instanceof ExamWriteBean)
	 *			&& (((ExamWriteBean)obj).getContent().equals(this.getContent()))
	 *			&& (((ExamWriteBean)obj).getName().equals(this.getName()))
	 *			&& (((ExamWriteBean)obj).getIdfaculties().equals(this.getIdfaculties())));
	 */
	@Override
	public boolean equals(Object obj) {
		return ((null != obj)
				&& (obj instanceof ExamWriteBean)
				&& (((ExamWriteBean)obj).getContent().equals(this.getContent()))
				&& (((ExamWriteBean)obj).getName().equals(this.getName()))
				&& (((ExamWriteBean)obj).getIdfaculties().equals(this.getIdfaculties())));
	}
}
