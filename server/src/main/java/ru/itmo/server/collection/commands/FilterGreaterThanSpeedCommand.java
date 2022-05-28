package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class FilterGreaterThanSpeedCommand implements Command{
    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        return ArrayDequeDAO.getInstance().filterGreaterThanSpeed(humanBeing.getImpactSpeed()).toString();
    }
}
