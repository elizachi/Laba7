package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;
import java.util.List;

public class FilterGreaterThanSpeedCommand implements Command{
    @Override
    public Response execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        List<?> minutes = ArrayDequeDAO.getInstance().filterByMinutes(humanBeing.getMinutesOfWaiting());

        if(minutes.size() == 0) return new Response(Response.Status.WARNING,
                "filter_greater_than_impact_speed: Не нашлось элементов больших impactSpeed = "
                        +humanBeing.getImpactSpeed());
        return new Response(Response.Status.OK, "filter_greater_than_impact_speed: "+ minutes);
    }
}
