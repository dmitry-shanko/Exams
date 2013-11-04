package by.epam.entity;

import java.io.Serializable;

import by.epam.resources.LocalizationRef;

/**
 * Class for Exam entity.
 * @author Dmitry Shanko
 *
 */
public class Exam implements Serializable, LocalizationRef
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idexam;
	private String examName;
	private int idstudent;
	private int idfaculty;
	private int mark;
	private boolean status;
	private String content;
	private String studentcontent;
	/**
	 * Public default constructor.<p>
	 * this.setIdexam(0);<p>
	 * this.setMark(0);<p>
	 * this.setStatus(false);<p>
	 */
	public Exam()
	{
		this.setIdexam(0);
		this.setMark(0);
		this.setStatus(false);
		this.setContent("");
		this.setIdstudent(0);
		this.setExamName("");
		this.setIdfaculty(0);
	}
	/**
	 * Getter for idexam field.
	 * @return the idexam
	 */
	public int getIdexam() 
	{
		return idexam;
	}
	/**
	 * Setter for idexam field if param > 0
	 * @param idexam the idexam to set
	 */
	public void setIdexam(int idexam) 
	{
		if (idexam > 0)
		{
			this.idexam = idexam;
		}
		this.setExamName("");
	}
	/**
	 * Getter for idstudent field.
	 * @return the idstudent
	 */
	public int getIdstudent() 
	{
		return idstudent;
	}
	/**
	 * Setter for idstudent field if param > 0
	 * @param idstudent the idstudent to set
	 */
	public void setIdstudent(int idstudent) 
	{
		if (idstudent > 0)
		{
			this.idstudent = idstudent;
		}
	}
	/**
	 * Getter for mark field.
	 * @return the mark
	 */
	public int getMark() 
	{
		return mark;
	}
	/**
	 * Setter for mark field if param is >= 0
	 * @param mark the mark to set
	 */
	public void setMark(int mark) 
	{
		if (mark >= 0)
		{
			this.mark = mark;
		}
	}
	/**
	 * Getter for status field.
	 * @return the status
	 */
	public boolean isStatus() 
	{
		return status;
	}
	/**
	 * Setter for status field
	 * @param status the status to set
	 */
	public void setStatus(boolean status) 
	{
		this.status = status;
	}
	/**
	 * Getter for content field.
	 * @return the content
	 */
	public String getContent() 
	{
		return content;
	}
	/**
	 * Setter for content field.
	 * @param content the content to set
	 */
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * @return the examName
	 */
	public String getExamName() 
	{
		return examName;
	}
	/**
	 * @param examName the examName to set
	 */
	public void setExamName(String examName) 
	{
		if (null != examName)
		{
			this.examName = examName;
		}
	}
	/**
	 * @return the idfaculty
	 */
	public int getIdfaculty() {
		return idfaculty;
	}
	/**
	 * @param idfaculty the idfaculty to set
	 */
	public void setIdfaculty(int idfaculty) {
		this.idfaculty = idfaculty;
	}
	/**
	 * @return the studentcontent
	 */
	public String getStudentcontent() {
		return studentcontent;
	}
	/**
	 * @param studentcontent the studentcontent to set
	 */
	public void setStudentcontent(String studentcontent) {
		this.studentcontent = studentcontent;
	}
	/**
	 * Overridden Object.toString()<p>
	 * @return (examName + ". " + "mark is " + mark + ". Status is " + status + ".\n");
	 */
	@Override
	public String toString() 
	{		
		return (examName + ". " + "mark is " + mark + ". Status is " + status + ".\n");
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return ((null != obj) && (obj instanceof Exam) <p>
	 *		&& (((Exam)obj).getIdexam() == this.idexam) <p>
	 *		&& (((Exam)obj).getMark() == this.mark) <p>
	 *		&& (((Exam)obj).isStatus() == this.status)<p>
	 *		&& (((Exam)obj).getContent() == this.content)<p>
	 *		&& (((Exam)obj).getIdstudent() == this.idstudent)<p>
	 *		&& (((Exam)obj).getStudentcontent() == this.studentcontent));
	 */
	@Override
	public boolean equals(Object obj) 
	{
		return ((null != obj) && (obj instanceof Exam) 
				&& (((Exam)obj).getIdexam() == this.idexam) 
				&& (((Exam)obj).getMark() == this.mark) 
				&& (((Exam)obj).isStatus() == this.status)
				&& (((Exam)obj).getContent() == this.content)
				&& (((Exam)obj).getIdstudent() == this.idstudent)
				&& (((Exam)obj).getStudentcontent() == this.studentcontent));
	}
	/**
	 * Overridden Object.hashCode()<p>
	 * final int hash = 45;<p>
	 * int result = 44;<p>
	 * result = hash * result + idexam;<p>
	 * result = hash * result + ((null != content) ? content.hashCode() : 1);<p>
	 * result = hash * result + mark;<p>
	 * result = hash * result + ((status) ? 1 : 0)<p>
	 * result = hash * result + idstudent;<p>
	 * result = hash * result +  ((null != studentcontent) ? studentcontent.hashCode() : 1);
	 * @return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 45;
		int result = 44;
		result = hash * result + idexam;
		result = hash * result + ((null != content) ? content.hashCode() : 1);
		result = hash * result + mark;
		result = hash * result + ((status) ? 1 : 0);
		result = hash * result + idstudent;
		result = hash * result +  ((null != studentcontent) ? studentcontent.hashCode() : 1);
		return result;
	}

}
