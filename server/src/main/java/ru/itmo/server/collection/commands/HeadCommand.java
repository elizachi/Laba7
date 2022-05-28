package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

import java.util.ArrayList;

public class HeadCommand implements Command{
    private final DAO arrayDequeDAO;

    public HeadCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        ArrayList<Integer> indexes = arrayDequeDAO.getAllSQL();
        if (indexes.size() != 0) {
            return arrayDequeDAO.getSQL("id = ", indexes.get(0)).stream().
                    reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
        } else {
            return "Коллекция пустая.";
        }
    }
}
