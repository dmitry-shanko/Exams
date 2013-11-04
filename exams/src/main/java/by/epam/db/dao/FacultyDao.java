package by.epam.db.dao;

import java.util.List;

import by.epam.db.exception.DaoException;
import by.epam.entity.Faculty;

public interface FacultyDao extends GeneralDao<Faculty>
{
	List<Faculty> collectFaculties() throws DaoException;
	List<Integer> collectIdFaculties() throws DaoException;

}
