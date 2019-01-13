package api.category;

import api.game.GameSqlContext;
import application.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryContext implements ICategoryContext {

    private String dbPassword = "wachtwoord";
    private static final Logger LOGGER = Logger.getLogger(CategoryContext.class.getName());

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Trivia.Category";

            // Execute query
            ResultSet result = statement.executeQuery(query);

            // Add items to list
            while (result.next()) {
                categories.add(new Category(
                        result.getInt(1),
                        result.getString(2)
                ));
            }

            // Close connections
            result.close();
            statement.close();
            connection.close();


        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }

        return categories;
    }
}
