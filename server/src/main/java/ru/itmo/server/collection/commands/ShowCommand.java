package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.ArrayDequeDAO;

public class ShowCommand implements Command{
    @Override
    public Object execute(Object arguments) {
        String result = ArrayDequeDAO.getInstance().showCollection();
        if(result != null) return result;
        return "Коллекция пуста";
    }
}
