package manager;

import db.DBConnectionProvider;
import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CategoryManager categoryManager = new CategoryManager();


    public void add(Product product) {
        String sql = "INSERT INTO product(name,description,price,quantity,category_id) VALUES(?,?,?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(String.format(sql), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getCategory().getId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteById(int productId) {
        String sql = "DELETE FROM product WHERE id = " + productId;
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Product getById(int id) {
        String sql = "SELECT * FROM product WHERE id = " + id;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(sql));
            if (resultSet.next()) {
                return getProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void edit(Product product) {
        String sql = "UPDATE product Set name = '%s',description = '%s',price = %d,quantity = %d WHERE id = %d";
      //  String sql = "UPDATE product SET name = ?,description = ?,prise = ?,quantity = ?,category_id Where id = ?";
           // try(PreparedStatement ps = connection.prepareStatement(sql)) {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(sql,product.getName(), product.getDescription(), product.getPrice(),product.getQuantity(),product.getId()));
       // ps.setString(1, product.getName());
        //ps.setString(2, product.getDescription());
        //ps.setInt(3, product.getPrice());
        //ps.setInt(4, product.getQuantity());
       // ps.setInt(5, product.getCategory().getId());
      //  ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printSum() {
        String sql = "SELECT SUM(id) FROM product";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int sumProduct = resultSet.getInt(1);
                System.out.println(sumProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printMaxPrice() {
        String sql = "SELECT MAX(Price) AS SmallestPrice  FROM product;";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                int maxPrice = resultSet.getInt("SmallestPrice");
                System.out.println(maxPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printMinPrice() {
        String sql = "SELECT MIN(Price) AS SmallestPrice FROM product;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int minPrice = resultSet.getInt("SmallestPrice");
                System.out.println(minPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printAvgPrice() {
        String sql = "SELECT AVG(Price) FROM product;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int avgPrice = resultSet.getInt(1);
                System.out.println(avgPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                products.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getInt("price"));
        product.setQuantity(resultSet.getInt("quantity"));
        int categoryId = resultSet.getInt("category_id");
        product.setCategory(categoryManager.getById(categoryId));
        return product;
    }

}
