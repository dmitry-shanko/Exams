package by.epam.db.dao;

import java.util.List;

import by.epam.db.exception.DaoException;
import by.epam.entity.Exam;
import by.epam.entity.ExamWriteBean;
import by.epam.entity.Faculty;
import by.epam.entity.User;


public interface ExamDao extends GeneralDao<Exam>
{
	
	List<Integer> collectIdExamsForLecturer(int id) throws DaoException;	
	List<Integer> collectIdExamsForMainadmin() throws DaoException;
	List<Exam> collectAllExamsForMainadmin() throws DaoException;
	List<Exam> collectExamsByIdNotChecked(int id) throws DaoException;
	List<Exam> collectExamsForStudent(int id) throws DaoException;
	List<Exam> collectExamsForStudentNotPassed(int id) throws DaoException;
	List<Exam> collectAllExamsForStudent(int id) throws DaoException;
	boolean createNewExam(ExamWriteBean exam) throws DaoException;
	boolean writeExamsForStudent(Faculty faculty, User student) throws DaoException;
	boolean writeExamAsPassed(Exam exam) throws DaoException;

	boolean updateExam(Exam exam) throws DaoException;

}
