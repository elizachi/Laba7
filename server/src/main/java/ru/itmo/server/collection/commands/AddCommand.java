package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

public class AddCommand implements Command{
    private final DAO postgresqlDAO = new PostgreSqlDao();
    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        int id = postgresqlDAO.add(humanBeing);

        if(id != -1) {
            humanBeing.setId(id);
            return "add: Элемент успешно добавлен в коллекцию, его id = "
                    + ArrayDequeDAO.getInstance().add(humanBeing);
        }
        return "Возникли проблемы с добавлением элемента. Пожалуйста, проверьте, подключена ли база данных.";
    }

}
