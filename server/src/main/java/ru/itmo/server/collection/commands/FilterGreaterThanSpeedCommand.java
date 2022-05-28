package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

import java.util.ArrayList;

public class FilterGreaterThanSpeedCommand implements Command{
    private final DAO arrayDequeDAO;

    public FilterGreaterThanSpeedCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        ArrayList<String> rows = arrayDequeDAO.getSQL("impactSpeed > ", humanBeing.getImpactSpeed());
        if (rows.size() != 0) {
            return rows.stream().
                    reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
        } else {
            return "Таких значений поля скорость не нашлось.";
        }
    }
}
