package by.epam.db.dao;

import java.util.List;

import by.epam.db.exception.DaoException;
import by.epam.entity.User;

public interface UserDao extends GeneralDao<User>
{
	
	User getUserByEmail(String email, String password) throws DaoException;
	List<User> getUsersByIdfaculty(int idfaculty) throws DaoException;

}
