package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class RemoveGreaterCommand implements Command{

    @Override
    public Object execute(Object arguments) {
        HumanBeing human = ArrayDequeDAO.getInstance().removeLast();
        if(human != null) return human.toString();
        return "Коллекция пуста";
    }
}
