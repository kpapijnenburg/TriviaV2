package api.User;

import api.exceptions.IncorrectCredentialsException;
import application.model.User;

import java.util.ArrayList;

public interface IUserContext {
    ArrayList<User> getAll();
    boolean create(User user);
    User getByCredentials(String name, String password) throws IncorrectCredentialsException;
}
