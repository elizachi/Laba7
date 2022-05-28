package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class ClearCommand implements Command{
    private final DAO postgresqlDAO = new PostgreSqlDao();
    @Override
    public Object execute(Object arguments) {
        ArrayList<Integer> indexes = postgresqlDAO.getAllSQL();
        if (indexes.size() != 0) {
            for(int i : indexes) {
                if(!postgresqlDAO.delete(i)) {
                    return "В процессе очистки коллекции произошла ошибка";
                }
            }
            ArrayDequeDAO.getInstance().clear();
            return "Коллекция успешно очищена";
        } else {
            return "Коллекция пустая";
        }
    }
}
