package manager;

import com.sun.source.tree.TryTree;
import db.DBConnectionProvider;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    public void add(Category category) {
        String sql = "INSERT INTO category(name) VALUES(?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void editCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setString(1, category.getName());
            ps.setInt(2,category.getId());
          ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteById(int categoryId) {
        String sql = "DELETE FROM category WHERE id = " + categoryId;
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * from category";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                categoryList.add(getCategoryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public Category getById(int categoryId) {
        String sql = "SELECT * FROM category WHERE id = " + categoryId;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(sql));
            if (resultSet.next()) {
                return  getCategoryFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
