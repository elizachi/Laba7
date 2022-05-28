package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;

public class HeadCommand implements Command{
    @Override
    public Object execute(Object arguments) {
        HumanBeing human = ArrayDequeDAO.getInstance().getHead();
        if(human != null) return human.toString();
        return "Коллекция пуста";
    }
}
