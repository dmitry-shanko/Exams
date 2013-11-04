package by.epam.db.dao.mysql;
/**
 * Contains SQL Statements for FacultyDao
 * @author Dmitry Shanko
 *
 */
public enum MySQLFacultyDaoStatement 
{
	collectAllFaculties("SELECT * FROM faculties"),
	collectIdFaculties("SELECT idfaculties FROM faculties");
	private String statement;
	private MySQLFacultyDaoStatement (String s)
	{
		this.statement = s;
	}
	
	public String getStatement()
	{
		return statement;
	}

}
