package ru.itmo.server.collection.commands;

import ru.itmo.common.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.dao.ArrayDequeDAO;

public class RemoveGreaterCommand implements Command{

    @Override
    public Response execute(Object arguments) {
        HumanBeing human = ArrayDequeDAO.getInstance().removeLast();
        if(human != null) return new Response(Response.Status.OK, "remove_greater: "+human,new User("", ""));
        return new Response(Response.Status.WARNING, "remove_greater: Коллекция пуста", new User("", ""));
    }
}
