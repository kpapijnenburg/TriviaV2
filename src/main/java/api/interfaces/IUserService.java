package api.interfaces;

import api.exceptions.IncorrectCredentialsException;
import api.exceptions.NonUniqueUsernameException;
import application.model.User;

import java.io.IOException;

public interface IUserService {
    /**
     * Returns the user with the specified username and password if available.
     */
    User login(String name, String password) throws IOException, IncorrectCredentialsException;
    /**
     * Registers a new user if the name is not in use yet.
     */
    void register(String name, String password) throws IOException, NonUniqueUsernameException;
}
