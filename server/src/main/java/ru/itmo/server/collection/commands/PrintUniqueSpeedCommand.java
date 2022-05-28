package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.ArrayDequeDAO;

import java.util.ArrayList;
import java.util.List;

public class PrintUniqueSpeedCommand implements Command{
    @Override
    public Object execute(Object arguments) {
        arguments = "";
        List<Integer> uniqueFieldsSpeed = new ArrayList<>();
        if (ArrayDequeDAO.getInstance().getAll().size() != 0) {
            for(HumanBeing human: ArrayDequeDAO.getInstance().getAll()) {
                Integer speed = human.getImpactSpeed();
                if(!uniqueFieldsSpeed.contains(speed)) {
                    uniqueFieldsSpeed.add(speed);
                } else {
                    uniqueFieldsSpeed.remove(speed);
                }
            }
            for(Integer element: uniqueFieldsSpeed) {
                arguments += element.toString() + "\n";
            }
            return arguments;
        } else {
            return "Коллекция пустая.";
        }
    }
}
