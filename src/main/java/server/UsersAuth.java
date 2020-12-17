package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersAuth implements Authentication{
    private Connection connection;
    private Statement stmt;
    private ResultSet rs;

    public UsersAuth() {
        try {
            connection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNickname(String login, String password) {
        try {
            rs = stmt.executeQuery(String.format
                    ("SELECT nickname FROM users WHERE login = '%s' AND password = '%s'", login, password));
            String name = rs.getString("nickname");
            System.out.println(name);
            return name;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean register(String login, String password, String nickname) {

        try {
            stmt.executeUpdate(String.format
                    ("INSERT INTO users VALUES ('%s', '%s', '%s')", login, password, nickname));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;


//        for (User user:users ) {
//            if(user.login.equals(login) || user.nickname.equals(nickname))
//                return false;
//        }
//        users.add(new User(login, password, nickname));
//        return true;
    }

    private void connection() throws SQLException, ClassNotFoundException {
        // подключиться к JDBC после импорта библиотеки
        Class.forName("org.sqlite.JDBC");
        // указываем на местоположение DB
        connection = DriverManager.getConnection
                ("jdbc:sqlite:src/main/resources/mainDB.db");
        stmt = connection.createStatement();
    }

    private void disconnect() throws SQLException {
        connection.close();
    }
}
