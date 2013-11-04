package by.epam.db.dao.mysql;
/**
 * Contains SQL Statements for ExamDao
 * @author Dmitry Shanko
 *
 */
public enum MySQLExamDaoStatement 
{
	collectIdExamsForLecturer("SELECT idexam FROM examslist WHERE idlecturers=?;"),
	collectIdExamsForMainadmin("SELECT idexam FROM exams"),
//	collectExamsByIdNotChecked("SELECT facultyexams.idfaculty, facultyexams.idexam, mark, status, idstudents, studentcontent FROM studentsexams, facultyexams where status='0' AND NOT studentcontent='NULL' AND facultyexams.idexam=? AND studentsexams.idfacultyexams=facultyexams.idfacultyexams"),
	collectExamsByIdNotChecked("SELECT facultyexams.idfaculty, facultyexams.idexam, mark, status, idstudents, studentcontent, exams.content FROM studentsexams, facultyexams, exams WHERE status='0' AND NOT studentcontent='NULL' AND facultyexams.idexam=? AND studentsexams.idfacultyexams=facultyexams.idfacultyexams AND exams.idexam=facultyexams.idexam;"),
	updateExam("UPDATE studentsexams SET status=?, mark=? WHERE idfacultyexams=(SELECT idfacultyexams from facultyexams WHERE idexam=? AND idfaculty=?) AND idstudents=?"),
//	collectExamsForStudent("SELECT * FROM studentsexams, facultyexams where idstudents=? AND studentsexams.idfacultyexams=facultyexams.idfacultyexams"),
	collectExamsForStudent("SELECT * FROM studentsexams, facultyexams, exams where idstudents=? AND studentsexams.idfacultyexams=facultyexams.idfacultyexams AND exams.idexam=facultyexams.idexam"),
//	collectExamsForStudentNotPassed("SELECT * FROM studentsexams, facultyexams WHERE idstudents=? AND studentcontent is null AND studentsexams.idfacultyexams=facultyexams.idfacultyexams"),
	collectExamsForStudentNotPassed("SELECT * FROM studentsexams, facultyexams, exams WHERE idstudents=? AND studentcontent is null AND studentsexams.idfacultyexams=facultyexams.idfacultyexams AND exams.idexam=facultyexams.idexam"),
//	collectAllExamsForStudent("SELECT * FROM studentsexams, facultyexams WHERE idstudents=? AND studentsexams.idfacultyexams=facultyexams.idfacultyexams"),
	collectAllExamsForStudent("SELECT * FROM studentsexams, facultyexams, exams WHERE idstudents=? AND studentsexams.idfacultyexams=facultyexams.idfacultyexams AND exams.idexam=facultyexams.idexam"),
//	collectAllExamsForMainadmin("SELECT facultyexams.idfaculty, facultyexams.idexam, mark, status, idstudents, studentcontent FROM studentsexams, facultyexams WHERE studentsexams.idfacultyexams=facultyexams.idfacultyexams"),
	collectAllExamsForMainadmin("SELECT facultyexams.idfaculty, facultyexams.idexam, mark, status, idstudents, studentcontent, exams.content FROM studentsexams, facultyexams, exams WHERE studentsexams.idfacultyexams=facultyexams.idfacultyexams AND exams.idexam=facultyexams.idexam"),
	createNewExam("INSERT INTO exams(exam, content) VALUES(?,?)"),
	createNewFacultyExam("INSERT INTO facultyexams(idfaculty, idexam) VALUES(?,?)"),
	getMaxIdexam("SELECT max(exams.idexam) FROM exams"),
	writeExamsForStudent("INSERT INTO studentsexams(idstudents, mark, status, idfacultyexams) VALUES(?, '0', '0', ?)"),
	collectExamsByFaculty("SELECT idexam, idfacultyexams FROM facultyexams WHERE idfaculty=?"),
	getIdfacultyexamsByIdstudentsIdexamIdfaculty("SELECT studentsexams.idfacultyexams FROM studentsexams, facultyexams WHERE studentsexams.idstudents=? AND facultyexams.idexam=? AND facultyexams.idfaculty=? AND studentsexams.idfacultyexams=facultyexams.idfacultyexams;"),
	writeExamAsPassed("UPDATE studentsexams SET studentcontent=? WHERE idstudents=? AND mark='0' AND status='0' AND studentcontent is null AND idfacultyexams=?;");
	private String statement;
	private MySQLExamDaoStatement (String s)
	{
		this.statement = s;
	}
	
	public String getStatement()
	{
		return statement;
	}

}
