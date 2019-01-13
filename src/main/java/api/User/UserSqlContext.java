package api.User;


import api.exceptions.IncorrectCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import application.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class UserSqlContext implements IUserContext {

    private static final Logger LOGGER = Logger.getLogger(UserSqlContext.class.getName());


    String dbPassword = "wachtwoord";

    @Override
    public ArrayList<User> getAll() throws SQLException {

        Connection connection = null;

        ArrayList<User> users = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Trivia.[User]";

            // Execute query
            ResultSet result = statement.executeQuery(query);

            // Add items to list
            while (result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("password")
                ));
            }

            // Close connections
            result.close();
            statement.close();

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        } finally {
            connection.close();
        }

        return users;
    }

    @Override
    public boolean create(User user) throws SQLException {

        boolean result = false;
        Connection connection = null;

        String encryptedPassword = new BCryptPasswordEncoder(11).encode(user.getPassword());

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Trivia.[User]" +
                            " VALUES (?, ?)"
            );

            statement.setString(1, user.getName());
            statement.setString(2, encryptedPassword);

            result = statement.executeUpdate() > 0;


        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }
        finally {
            connection.close();
        }

        return result;
    }

    @Override
    public User getByCredentials(String name, String password) throws IncorrectCredentialsException {
        User user = null;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Trivia.[User] WHERE [Name] = ?"
            );

            statement.setString(1, name);
            // Execute query
            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                String pw = result.getString("password");
                if (passwordEncoder.matches(password, pw)) {

                    user = new User(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("password")
                    );
                }

            }

            // Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }

        if (user != null) {
            return user;
        } else {
            throw new IncorrectCredentialsException("Incorrect username or password.");
        }
    }


}
