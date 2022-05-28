package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.PostgreSqlDao;

import java.util.ArrayList;

public class PrintUniqueSpeedCommand implements Command{
    // TODO
    private final PostgreSqlDao postgresqlDAO = new PostgreSqlDao();
    @Override
    public Object execute(Object arguments) {
        ArrayList<Integer> indexes = postgresqlDAO.getAllSQL();
        ArrayList<Integer> uniqueImpactSpeed = new ArrayList<>();
        ArrayList<String> humans = new ArrayList<>();
        if (indexes.size() != 0) {
            for(int i : indexes) {
                String human = postgresqlDAO.getSQL("id = ", i).get(0);
                int index_start = human.indexOf("impactSpeed = ") + 14;
                int index_end = human.indexOf("\n", index_start);
                int value = Integer.parseInt(human.substring(index_start, index_end));
                if(uniqueImpactSpeed.contains(value)) {
                    uniqueImpactSpeed.remove(new Integer(value));
                } else {
                    uniqueImpactSpeed.add(value);
                }
            }
            for(Integer speed: uniqueImpactSpeed) {
                humans.add(postgresqlDAO.getSQL("impactSpeed = ", speed).get(0));
            }
        } else {
            return "Коллекция пустая.";
        }
        return humans.stream().
                reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }
}
