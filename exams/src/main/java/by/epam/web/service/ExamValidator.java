package by.epam.web.service;

import java.util.List;

import by.epam.entity.ExamWriteBean;
import by.epam.web.service.exception.ServiceException;
/**
 * Abstract class with public static and private static methods.<p>
 * Is an example of server validating of creating new Exam.<p>
 * @author Dmitry Shanko
 *
 */
public abstract class ExamValidator 
{
	/**
	 * Validates ExamWriteBean by its fields if new Exam that will be created in database is valid or do not have some important fields or values.
	 * @param exam ExamWriteBean to validate
	 * @return true if all fields in ExamWriteBean are valid.
	 * @throws ServiceException
	 */
	public static boolean validateExam(ExamWriteBean exam) throws ServiceException
	{
		if (null != exam)
		{
			return ((ExamValidator.validateName(exam.getName())) 
					&& (ExamValidator.validateContent(exam.getContent())) 
					&& (ExamValidator.validateIdFaculties(exam.getIdfaculties())));
		}
		return false;
	}
	/**
	 * Validate name field of ExamWriteBean.
	 * @param name name to validate
	 * @return ((null != name) && (name.length() > 3) && (name.length() < 15));
	 */
	private static boolean validateName(String name)
	{
		return ((null != name) && (name.length() > 3) && (name.length() < 25));
	}
	/**
	 * Validate content field of ExamWriteBean.
	 * @param content content to validate
	 * @return ((null != content) && (content.length() > 1) && (content.length() < Integer.MAX_VALUE));
	 */
	private static boolean validateContent(String content)
	{
		return ((null != content) && (content.length() > 1) && (content.length() < Integer.MAX_VALUE));
	}
	/**
	 * Validate idfaculties field of ExamWriteBean only as they are not null and contains in list
	 * @param idfacultues list of idfaculties to validate
	 * @return ((null != idfacultues) && (idfacultues.size() > 0) && ExamValidator.validateFaculty(idfacultues));
	 * @throws ServiceException
	 */
	private static boolean validateIdFaculties(List<Integer> idfacultues) throws ServiceException
	{
		return ((null != idfacultues) && (idfacultues.size() > 0) && ExamValidator.validateFaculty(idfacultues));
	}
	/**
	 * Validate idfaculties field of ExamWriteBean by comparing them with existing idfaculties from database.<p>
	 * Uses FacultyService.getInstance().collectPossibleIdFacultiesForNewExam() to collect all possible idfaculties values from database.
	 * @param idfacultues list of idfaculties to validate
	 * @return true if all idfaculties exist in database. Or false if at least one idfaculty is not valid.
	 * @throws ServiceException
	 */
	private static boolean validateFaculty(List<Integer> idfacultues) throws ServiceException
	{
		for (Integer i : idfacultues)
		{
			boolean flag = false;
			for (Integer id : FacultyService.getInstance().collectPossibleIdFacultiesForNewExam())
			{
				if (id == i)
				{
					flag = true;
				}
			}
			if (flag == false)
			{
				return false;
			}
			else
			{
				flag = true;
			}
		}
		return true;
	}
}
