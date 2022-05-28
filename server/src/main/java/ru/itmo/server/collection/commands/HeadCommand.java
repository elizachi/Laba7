package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class HeadCommand implements Command{
    // TODO

    private final PostgreSqlDao postgresqlDAO = new PostgreSqlDao();
    @Override
    public Object execute(Object arguments) {
        ArrayList<Integer> indexes = postgresqlDAO.getAllSQL();
        if (indexes.size() != 0) {
            return postgresqlDAO.getSQL("id = ", indexes.get(0)).stream().
                    reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
        } else {
            return "Коллекция пустая.";
        }
    }
}
