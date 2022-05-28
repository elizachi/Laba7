package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;
import ru.itmo.server.JDBC.JdbcManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PostgreSqlDao implements DAO{

    private final Connection connection;
    private ArrayDeque<HumanBeing> humanCollection = new ArrayDeque<>();
    private final String COLLECTION_NAME = "HUMAN_BEING_COLLECTION";
    private final String ADD_COMMAND = "INSERT INTO "+ COLLECTION_NAME;
    private final String UPDATE_COMMAND = "UPDATE " + COLLECTION_NAME +" SET ";
    private final String DELETE_COMMAND = "DELETE FROM " + COLLECTION_NAME;
    private final String GET_COMMAND = "SELECT * FROM " + COLLECTION_NAME + " WHERE ";
    private final String GET_ID = "SELECT ID FROM " + COLLECTION_NAME;

    public PostgreSqlDao() {
        this.connection = JdbcManager.connectToDataBase();
    }

    /**
     * override DAO methods
     */
    // TODO разобраться с айдишками
    // TODO разобраться с отправкой ответа на запрос
    @Override
    public void addSQL(HumanBeing humanBeing) {
        String sql = ADD_COMMAND + "(creationDate, name, soundtrackName, minutesOfWaiting," +
                " impactSpeed, realHero, hasToothpick, coordinates, mood, car) VALUES (" +
                "TO_DATE('"+humanBeing.getCreationDate().toString() + "', 'YYYY/MM/DD'), '" +
                humanBeing.getName() + "', '" +
                humanBeing.getSoundtrackName() + "', '" +
                humanBeing.getMinutesOfWaiting() + "', '" +
                humanBeing.getImpactSpeed() + "', '" +
                humanBeing.isRealHero() + "', " +
                convertToSQL(humanBeing.isHasToothpick()) + ", '(" +
                humanBeing.getCoordinates().getX() +", " + humanBeing.getCoordinates().getY()+ ")', " +
                convertToSQL(humanBeing.getMood()) + ", '(" +
                convertToSQL(humanBeing.getCar().getCarName()) + ", " + humanBeing.getCar().getCarCool() + ")')";
        sendToDataBaseUpdate(sql);

    }
    @Override
    public void updateSQL(HumanBeing humanBeing) {
        String sql = null;
        if(getSQL("id = ", humanBeing.getId()) != null) {
            sql = UPDATE_COMMAND + "name = '" + humanBeing.getName() + "' ," +
                    "soundtrackName = '" + humanBeing.getSoundtrackName() + "' ," +
                    "minutesOfWaiting = " + humanBeing.getMinutesOfWaiting() + " ," +
                    "impactSpeed = " + humanBeing.getImpactSpeed() + " ," +
                    "realHero = '" + humanBeing.isRealHero() + "' ," +
                    "hasToothpick = " + convertToSQL(humanBeing.isHasToothpick()) + ", " +
                    "coordinates = '(" + humanBeing.getCoordinates().getX() + ", " +
                    humanBeing.getCoordinates().getY() + ")', " +
                    "mood = " + convertToSQL(humanBeing.getMood()) + ", " +
                    "car = '(" + humanBeing.getCar().getCarName() + ", " +
                    humanBeing.getCar().getCarCool() + ")' WHERE " +
                    "id = " + humanBeing.getId();
        }
        sendToDataBaseUpdate(sql);
    }

    @Override
    public void deleteSQL(int index) {
        String sql = null;
        if(getSQL("id = ", index) != null) {
            sql = DELETE_COMMAND + " WHERE " +
                    "id = " + index;
        }
        sendToDataBaseUpdate(sql);
    }

    @Override
    public ArrayList<String> getSQL(String column, Object obj) {
        String sql = GET_COMMAND + column + obj.toString();
        ResultSet result = sendToDataBaseQuery(sql);
        ArrayList<String> rows = new ArrayList<>();
        String row = null;
        try {
            while (result.next()) {
                row = "id = " + result.getInt("id") +"\n"+
                        "creationDate = " + result.getDate("creationDate") +"\n"+
                        "name = " + result.getString("name") +"\n"+
                        "soundtrackName = " + result.getString("soundtrackName") +"\n"+
                        "minutesOfWaiting = " + result.getInt("minutesOfWaiting") +"\n"+
                        "impactSpeed = " + result.getInt("impactSpeed") +"\n"+
                        "realHero = " + result.getBoolean("realHero") +"\n"+
                        "hasToothpick = " + result.getString("hasToothpick") +"\n"+
                        "coordinates = " + result.getObject("coordinates") +"\n"+
                        "mood = " + result.getObject("mood") +"\n"+
                        "car = " + result.getObject("car") +"\n";
                rows.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Случилась еще одна хуета");
        }
        return rows;
    }

    @Override
    public ArrayList<Integer> getAllSQL() {
        ArrayList<Integer> indexes = new ArrayList<>();
        String sql = GET_ID;
        ResultSet result = sendToDataBaseQuery(sql);
        try {
            while(result.next()) {
                int index = result.getInt("id");
                indexes.add(index);
            }
        } catch (SQLException e) {
            System.out.println("Бляяяяя проблема появилась :(");
        }
        return indexes;
    }
    private ResultSet sendToDataBaseQuery(String sql) {
        ResultSet result = null;
        try {
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(sql);
        } catch(SQLException e) {
            System.out.println("Случилась хуета");
        }
        return result;
    }

    private Statement sendToDataBaseUpdate(String sql) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch(SQLException e) {
            System.out.println("Случилась хуета");
        }
        return stmt;
    }
    private String convertToSQL(Boolean boolValue) {
        return (boolValue == null) ? null : "'"+boolValue+"'";
    }
    private String convertToSQL(Mood mood) {
        return (mood == null) ? null : "'"+mood.name()+"'";
    }
    private String convertToSQL(String strValue) {
        return (strValue == null) ? null : "'"+strValue+"'";
    }

    @Override
    public int size() {
        return humanCollection.size();
    }

}
