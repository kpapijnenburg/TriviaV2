package user;

import api.User.UserRepository;
import api.exceptions.IncorrectCredentialsException;
import api.exceptions.NonUniqueUsernameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import application.model.User;

public class UserTests {
    private User user;
    private UserRepository userRepository = new UserRepository(new user.UserTestContext());

    @Before
    public void TestInitialize() {
        // This user is present in the test context.
        this.user = new User(1, "test", "test");
    }


    @Test
    public void Login_ShouldLoginUser_WhenUsingStandardUser() {
        Exception e = null;

        try {
            userRepository.login(user.getName(), user.getPassword());
        } catch (Exception ex) {
            e = ex;
        }

        Assert.assertNull(e);

    }

    @Test(expected = IncorrectCredentialsException.class)
    public void Login_ShouldThrowException_WhenUsingNotRegisterUser() throws IncorrectCredentialsException {
        User user = new User(1, "Wrong", "Credentials");
        userRepository.login(user.getName(), user.getPassword());
    }

    @Test
    public void Register_ShouldRegisterUser_WhenNotUsingExistingUser() {
        User user = new User("new", "user");
        Exception e = null;

        try {
            userRepository.register(user);
        } catch (NonUniqueUsernameException ex){
            e = new Exception();
        }

        Assert.assertNull(e);
    }

    @Test(expected = NonUniqueUsernameException.class)
    public void Register_ShouldNotRegisterUser_WhenUsingStandardUser() throws NonUniqueUsernameException {
        userRepository.register(this.user);
    }
}
