package user;

import api.User.IUserContext;
import application.model.User;

import java.util.ArrayList;

public class UserTestContext implements IUserContext {

    private ArrayList<User> users;

    UserTestContext() {
        this.users = new ArrayList<>();
        users.add(new User(1, "test", "test"));
    }

    @Override
    public ArrayList<User> getAll() {
        return this.users;
    }

    @Override
    public boolean create(User user) {
        boolean result = true;

        for (User userInList : this.users) {
            if (userInList.getName().equals(user.getName())) {
                result = false;
            }
        }

        return result;

    }

    @Override
    public User getByCredentials(String name, String password) {
        return null;
    }
}
