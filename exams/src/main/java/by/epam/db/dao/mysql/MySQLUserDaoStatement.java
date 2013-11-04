package by.epam.db.dao.mysql;
/**
 * Contains SQL Statements for UserDao
 * @author Dmitry Shanko
 *
 */
public enum MySQLUserDaoStatement 
{
	getUserByEmail("SELECT * FROM users WHERE email = ? AND password = ?;"),
	getUsersByIdfaculty("SELECT DISTINCT users.idusers, users.idroles, users.name, users.surname FROM users, (SELECT roles.idroles FROM roles WHERE roles.role='student') as role, (SELECT studentsexams.idstudents FROM studentsexams, (SELECT facultyexams.idfacultyexams FROM facultyexams WHERE idfaculty=?) as facultyexams WHERE studentsexams.idfacultyexams=facultyexams.idfacultyexams) as studentsexams WHERE users.idroles=role.idroles AND users.idusers=studentsexams.idstudents;");
	private String statement;
	private MySQLUserDaoStatement (String s)
	{
		this.statement = s;
	}
	
	public String getStatement()
	{
		return statement;
	}

}
