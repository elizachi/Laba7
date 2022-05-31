package ru.itmo.server.JDBC;

import ru.itmo.server.ServerLauncher;

import java.sql.*;

public class JdbcManager {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String password = "Piqh-178_Laks";
    private static Connection connect;

    /**
     * Подключается к базе данных
     * @return
     */
    public static Connection connectToDataBase() {
        ServerLauncher.log.info("Соединение с базой данных...");
        try {
            Class.forName(JDBC_DRIVER);
            connect = DriverManager
                    .getConnection(url, user, password);
            ServerLauncher.log.info("Соединение с базой данных успешно установлено");
        } catch (ClassNotFoundException | SQLException e) {
            ServerLauncher.log.fatal("Файл с драйвером не обнаружен");
        } catch (Exception e) {
            ServerLauncher.log.fatal("Непредвиденная ошибка");
        }
        createTypes();
        createTable();
        return connect;
    }

    /**
     * Создаёт новую таблицу (новый файл с коллекцией), если она ещё не существует
     */
    private static void createCollectionTable() {
        try {
            ServerLauncher.log.info("Проверка таблицы коллекции...");
            Statement stmt = connect.createStatement();
            // Создание типов данных для таблицы
            // Создает новую таблицу, если она ещё не существует
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS HUMAN_BEING_COLLECTION(" +
                    "id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
                    "creationDate DATE," +
                    "name TEXT NOT NULL," +
                    "soundtrackName TEXT NOT NULL," +
                    "minutesOfWaiting BIGINT NOT NULL," +
                    "impactSpeed INTEGER NOT NULL," +
                    "realHero BOOLEAN NOT NULL," +
                    "hasToothpick BOOLEAN," +
                    "coordinates Coordinates," +
                    "mood Mood," +
                    "car Car);");
            ServerLauncher.log.info("Таблица успешно подключена");
        } catch (SQLException e) {
            ServerLauncher.log.error("Возникла проблема с подключением таблицы :(");
        }
    }

    private static void createTypes() {
        Statement stmt = null;
        ServerLauncher.log.info("Создание типов данных таблицы...");
        try {
            stmt = connect.createStatement();
        } catch (SQLException e) {
            ServerLauncher.log.error("Возникла проблема с подключением типов данных таблицы :(");
        } try {
            stmt.executeUpdate(
                    "CREATE TYPE Coordinates AS(" +
                            "x INTEGER," +
                            "y FLOAT);"
            );
            ServerLauncher.log.info("Тип данных Coordinates успешно создан");
        } catch (SQLException e) {
            ServerLauncher.log.info("Тип данных Coordinates уже создан");
        } try {
            stmt.executeUpdate("CREATE TYPE Mood AS ENUM ('SADNESS', 'LONGING', 'GLOOM', 'RAGE')");
            ServerLauncher.log.info("Тип данных Mood успешно создан");
        } catch (SQLException e) {
            ServerLauncher.log.info("Тип данных Mood уже создан");
        } try {
            stmt.executeUpdate(
                    "CREATE TYPE Car AS (" +
                            "name text," +
                            "isCool boolean );"
            );
            ServerLauncher.log.info("Тип данных Car успешно создан");
        } catch (SQLException e) {
            ServerLauncher.log.info("Тип данных Car уже создан");
        }
    }
    public static void createUserTable(){
        try {
            ServerLauncher.log.info("Проверка таблицы пользователей...");
            Statement stmt = connect.createStatement();
            // Создает новую таблицу, если она ещё не существует
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERS (" +
                    "login TEXT NOT NULL PRIMARY KEY," +
                    "password TEXT NOT NULL);");
            ServerLauncher.log.info("Таблица успешно подключена");
        } catch (SQLException e) {
            ServerLauncher.log.error("Возникла проблема с подключением таблицы :(");
        }
    }
}
