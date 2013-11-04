package by.epam.db.dao.mysql.constant;
/**
 * Contains column names of Exam table in database.
 * @author Dmitry Shanko
 *
 */
public enum ExamConstants 
{
	IDEXAM("idexam"), 
	FACULTYEXAMS_IDEXAM("facultyexams.idexam"),
	IDSTUDENTS("idstudents"),
	STUDENTCONTENT("studentcontent"),
	MARK("mark"),
	STATUS("status"),
	FACULTYEXAMS_IDFACULTY("facultyexams.idfaculty"),
	EXAMS_CONTENT("exams.content"),
	IDFACULTYEXAMS("idfacultyexams");
	private String content;
	private ExamConstants(String content) 
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}
}
