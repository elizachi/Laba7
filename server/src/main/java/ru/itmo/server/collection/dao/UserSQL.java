package ru.itmo.server.collection.dao;

import org.postgresql.util.PSQLException;
import ru.itmo.common.User;
import ru.itmo.server.JDBC.JdbcManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserSQL {
    Connection connection = JdbcManager.connectToDataBase();

    public Boolean add(User user) {
        String sql = "INSERT INTO USERS (login, password) VALUES ('" +
                user.getUsername() + "', '" +
                user.getPassword() + "')";
        return sendToDataBaseUpdate(sql);
    }

    private Boolean sendToDataBaseUpdate(String sql) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
