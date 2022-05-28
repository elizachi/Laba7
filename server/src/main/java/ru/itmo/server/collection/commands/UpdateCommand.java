package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

public class UpdateCommand implements Command{
    private final DAO postgresqlDAO = new PostgreSqlDao();
    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;

        if(postgresqlDAO.update(humanBeing)) {
            ArrayDequeDAO.getInstance().update(humanBeing);
            return "Элемент с id = "+humanBeing.getId()+" успешно обновлён";
        } else {
            return "Элемента с id = "+humanBeing.getId()+" не нашлось";
        }
    }
}
