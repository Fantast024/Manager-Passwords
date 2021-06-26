package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBConnect {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    static final String USER = "root";
    static final String PASS = "myroot";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String COMMENTS="comments";
    public static final String DATE="date";

    public static Connection dbConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void updateDb(String sqlStmt) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection=dbConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public static ObservableList<Story> getDataFromDb(String query) throws SQLException {
        ObservableList<Story> users = FXCollections.observableArrayList();
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = dbConnection();
            Statement statement1 = dbConnection.createStatement();
            ResultSet rs = statement1.executeQuery(query);
            while (rs.next()) {
                Story user = new Story("1","1","1","1");
                user.setUsername(rs.getString(USERNAME));
                user.setPassword(rs.getString(PASSWORD));
                user.setComment(rs.getString(COMMENTS));
                user.setDate(rs.getString(DATE));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
    public static void createDb() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dbConnection();
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE mysql";
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void createTable() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dbConnection();
            stmt = conn.createStatement();
            String sql = """
                    CREATE TABLE `mysql`.`passwords` (
                      `username` VARCHAR(45) NULL,
                      `password` VARCHAR(45) NULL,
                      `comments` VARCHAR(45) NULL,
                      `date` VARCHAR(45) NULL);""";
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void clearTable(){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dbConnection();
            stmt = conn.createStatement();
            String sql = """
                    TRUNCATE TABLE `mysql`.`passwords`;""";
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void sortTable(String str){
        Connection conn = null;
        Statement stmt = null;
        try {
            if(str == "personal use") {
                conn = dbConnection();
                stmt = conn.createStatement();
                String sql = """
                        SELECT * FROM `mysql`.`passwords` WHERE `comments` = `personal use`;""";
                stmt.executeUpdate(sql);
            }else if(str == "public use"){
                conn = dbConnection();
                stmt = conn.createStatement();
                String sql = """
                        SELECT * FROM `mysql`.`passwords` WHERE `comments` = `public use`;""";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
