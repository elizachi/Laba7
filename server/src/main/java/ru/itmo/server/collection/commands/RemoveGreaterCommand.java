package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class RemoveGreaterCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveGreaterCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        return "Команда в разработке";
    }
}
