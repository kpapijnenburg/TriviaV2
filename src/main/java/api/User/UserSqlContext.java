package api.User;


import api.exceptions.IncorrectCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import application.model.User;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserSqlContext implements IUserContext {
    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
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
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean create(User user) {

        boolean result = false;

        String encryptedPassword = new BCryptPasswordEncoder(11).encode(user.getPassword());

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Trivia.[User]" +
                            " VALUES (?, ?)"
            );

            statement.setString(1, user.getName());
            statement.setString(2, encryptedPassword);

            result = statement.executeUpdate() > 0;


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
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
            e.printStackTrace();
        }

        if (user != null) {
            return user;
        } else {
            throw new IncorrectCredentialsException("Incorrect username or password.");
        }
    }


}
