package api.category;

import application.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryContext implements ICategoryContext {
    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
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
            e.printStackTrace();
        }

        return categories;
    }
}
