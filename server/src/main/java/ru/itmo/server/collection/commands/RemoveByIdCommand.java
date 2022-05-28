package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

public class RemoveByIdCommand implements Command{
    private final DAO postgresqlDAO = new PostgreSqlDao();
    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        int id = humanBeing.getId();

        if(postgresqlDAO.delete(id)) {
            ArrayDequeDAO.getInstance().delete(id);
            return "Элемент с id = "+id+" успешно удалён";
        } else {
            return "Элемента с id = "+id+" не нашлось";
        }
    }
}
