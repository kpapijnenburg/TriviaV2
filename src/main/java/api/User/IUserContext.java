package api.User;

import api.exceptions.IncorrectCredentialsException;
import application.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserContext {
    ArrayList<User> getAll() throws SQLException;
    boolean create(User user) throws SQLException;
    User getByCredentials(String name, String password) throws IncorrectCredentialsException;
}
