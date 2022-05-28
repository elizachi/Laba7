package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class FilterByMinutesCommand implements Command{

    // TODO
    private final PostgreSqlDao postgresqlDAO = new PostgreSqlDao();
    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        ArrayList<String> rows = postgresqlDAO.getSQL("minutesOfWaiting = ", humanBeing.getMinutesOfWaiting());
        if (rows.size() != 0) {

            return rows.stream().
                    reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
        } else {
            return "Таких значений поля минуты ожидания не нашлось.";
        }
    }
}
