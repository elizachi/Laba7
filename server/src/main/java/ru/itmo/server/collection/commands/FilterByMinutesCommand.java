package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;

public class FilterByMinutesCommand implements Command{

    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        return ArrayDequeDAO.getInstance().filterByMinutes(humanBeing.getMinutesOfWaiting()).toString();
    }
}
