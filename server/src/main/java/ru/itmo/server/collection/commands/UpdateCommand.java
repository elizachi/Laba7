package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

public class UpdateCommand implements Command{
    private final DAO postgresqlDAO = new PostgreSqlDao();
    @Override
    public Response execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;

        if(postgresqlDAO.update(humanBeing)) {
            ArrayDequeDAO.getInstance().update(humanBeing);
            return new Response(Response.Status.OK, "Элемент с id = "+humanBeing.getId()+" успешно обновлён");
        } else {
            return new Response(Response.Status.WARNING, "Элемента с id = "+humanBeing.getId()+" не нашлось");
        }
    }
}
