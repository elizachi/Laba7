package ru.itmo.server.collection.commands;

import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.dao.ArrayDequeDAO;

import java.awt.geom.RectangularShape;

public class ShowCommand implements Command{
    @Override
    public Response execute(Object arguments) {
        String result = ArrayDequeDAO.getInstance().showCollection();
        if(result != null) return new Response(Response.Status.OK, "show: "+result);
        return new Response(Response.Status.WARNING, "show: Коллекция пуста");
    }
}
