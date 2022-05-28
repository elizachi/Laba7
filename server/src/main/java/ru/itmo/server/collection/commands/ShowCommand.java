package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

import java.util.ArrayList;

public class ShowCommand implements Command{
    private final DAO arrayDequeDAO;

    public ShowCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        ArrayList<Integer> indexes = arrayDequeDAO.getAllSQL();
        ArrayList<String> humans = new ArrayList<>();
        if (indexes.size() != 0) {
            for(int i : indexes) {
                humans.add(arrayDequeDAO.getSQL("id = ", i).get(0));
            }
            return humans.stream().
                    reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
        } else {
            return "Коллекция пустая.";
        }
    }
}
