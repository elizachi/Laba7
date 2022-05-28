package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

import java.util.ArrayList;

public class ClearCommand implements Command{
    private final DAO arrayDequeDAO;

    public ClearCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        ArrayList<Integer> indexes = arrayDequeDAO.getAllSQL();
        if (indexes.size() != 0) {
            for(int i : indexes) {
                arrayDequeDAO.deleteSQL(i);
            }
            return "Коллекция успешно очищена";
        } else {
            return "Коллекция пустая.";
        }
    }
}
