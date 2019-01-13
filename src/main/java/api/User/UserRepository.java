package api.User;


import api.exceptions.IncorrectCredentialsException;
import api.exceptions.NonUniqueUsernameException;
import application.model.User;

import java.util.ArrayList;

public class UserRepository {

    private IUserContext context;

    public UserRepository(IUserContext context) {
        this.context = context;
    }

    public User login(String name, String password) throws IncorrectCredentialsException {
        return context.getByCredentials(name, password);
    }


    public void register(User user) throws NonUniqueUsernameException {
        if (!context.create(user)){
            throw new NonUniqueUsernameException("Username is already taken.");
        }
    }

    private ArrayList<User> getAll() {
        return context.getAll();
    }


}
