package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO extends DBContext {

    public boolean validateUser(String email, String password) {
        try {
            String query = "SELECT * FROM Users WHERE email = ? AND password = ? AND status = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        try {
            String query = "SELECT * FROM Users WHERE email = ? AND password = ? AND status = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getInt("UserID"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("role"),
                                resultSet.getBoolean("status"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(User newUser) {
        try {
            String query = "INSERT INTO Users (name, email, password, role, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newUser.getName());
                preparedStatement.setString(2, newUser.getEmail());
                preparedStatement.setString(3, newUser.getPassword());
                preparedStatement.setString(4, newUser.getRole());
                preparedStatement.setBoolean(5, true);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailAlreadyInUse(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    public User getUserByID(int id) {
        try {
            String query = "SELECT * FROM Users WHERE UserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getInt("UserID"),
                                resultSet.getString("Name"),
                                resultSet.getString("Email"),
                                resultSet.getString("Password"),
                                resultSet.getString("Role"),
                                resultSet.getObject("Status") != null ? resultSet.getBoolean("Status") : null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM Users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getInt("UserID"),
                            resultSet.getString("Name"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getString("Role"),
                            resultSet.getObject("Status") != null ? resultSet.getBoolean("Status") : null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUser(User user) {
        try {
            String query = "UPDATE Users SET Name = ?, Email = ?, Role = ?, Status = ? WHERE UserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getRole());
                if (user.getStatus() != null) {
                    preparedStatement.setBoolean(4, user.getStatus());
                } else {
                    preparedStatement.setNull(4, java.sql.Types.BOOLEAN);
                }
                preparedStatement.setInt(5, user.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> searchUsers(String searchTerm) {
        List<User> results = new ArrayList<>();
        try {
            String query = "SELECT * FROM Users WHERE name LIKE ? OR email LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                String likePattern = "%" + searchTerm + "%";
                preparedStatement.setString(1, likePattern);
                preparedStatement.setString(2, likePattern);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(new User(
                                resultSet.getInt("UserID"),
                                resultSet.getString("Name"),
                                resultSet.getString("Email"),
                                resultSet.getString("Password"),
                                resultSet.getString("Role"),
                                resultSet.getObject("Status") != null ? resultSet.getBoolean("Status") : null
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public boolean updatePassword(int userId, String newPassword) {
        String query = "UPDATE Users SET Password = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser2(User user) {
        String query = "UPDATE Users SET Name = ?, Email = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new UserDAO().findAll().forEach(System.out::println);
    }
}
