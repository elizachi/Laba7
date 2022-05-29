package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class RemoveGreaterCommand implements Command{

    @Override
    public Response execute(Object arguments) {
        HumanBeing human = ArrayDequeDAO.getInstance().removeLast();
        if(human != null) return new Response(Response.Status.OK, "remove_greater: "+human);
        return new Response(Response.Status.WARNING, "remove_greater: Коллекция пуста");
    }
}
