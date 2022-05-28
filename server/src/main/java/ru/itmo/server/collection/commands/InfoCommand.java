package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

public class InfoCommand implements Command{

    private final DAO postgresqlDAO = new PostgreSqlDao();

    @Override
    public Object execute(Object arguments) {
        int size = ArrayDequeDAO.getInstance().getAll().size();
        return ("info: Таблица HUMAN_BEING_COLLECTION,\n" + "Количество элементов: " + size + "\n");
    }
}
