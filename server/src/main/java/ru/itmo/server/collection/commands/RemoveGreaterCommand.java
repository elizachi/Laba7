package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class RemoveGreaterCommand implements Command{
// TODO
    private final PostgreSqlDao postgresqlDAO = new PostgreSqlDao();

    @Override
    public Object execute(Object arguments) {
        ArrayList<Integer> indexes = postgresqlDAO.getAllSQL();

        if (indexes.size() != 0) {
            Object result = postgresqlDAO.getSQL("id = ", indexes.get(indexes.size() - 1)).stream().
                    reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
            postgresqlDAO.delete(indexes.get(indexes.size() - 1));
            ArrayDequeDAO.getInstance().removeLast();
            return result;
        } else {
            return "Коллекция пустая.";
        }
    }
}
