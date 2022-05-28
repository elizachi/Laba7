package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayDequeDAO implements DAO{
    private static volatile ArrayDequeDAO instance;
    private Deque<HumanBeing> humanCollection;

    public static ArrayDequeDAO getInstance() {
        if(instance == null) {
            synchronized (ArrayDequeDAO.class) {
                if(instance == null) {
                    instance = new ArrayDequeDAO();
                }
            }
        }
        return instance;
    }
    public void setCollection(Deque<HumanBeing> humans) {
        humanCollection = humans;
    }

    /**
     * override DAO methods
     */

    @Override
    public int add(HumanBeing human) {
        humanCollection.add(human);
        return human.getId();
    }

    @Override
    public boolean update(HumanBeing humanBeing) {
        HumanBeing existedHuman = get(humanBeing.getId());
        if(existedHuman != null) {
            existedHuman.setName(humanBeing.getName());
            existedHuman.setSoundtrackName(humanBeing.getSoundtrackName());
            existedHuman.setMinutesOfWaiting(humanBeing.getMinutesOfWaiting());
            existedHuman.setImpactSpeed(humanBeing.getImpactSpeed());
            existedHuman.setRealHero(humanBeing.isRealHero());
            existedHuman.setHasToothpick(humanBeing.isHasToothpick());
            existedHuman.setCoordinates(humanBeing.getCoordinates());
            existedHuman.setMood(humanBeing.getMood());
            existedHuman.setCar(humanBeing.getCar());
            return true;
        }
        return false;
    }

    public HumanBeing get(int id) {
        return humanCollection.stream().filter(humanBeing -> humanBeing.getId() == id).findFirst().orElse(null);
    }
    @Override
    public boolean delete(int index) {
        HumanBeing existedHuman = get(index);
        if(existedHuman != null) {
            humanCollection.remove(existedHuman);
            return true;
        }
        return false;
    }

    public Deque<HumanBeing> getAll() {
        return humanCollection;
    }

    public String showCollection(){
        if (humanCollection.isEmpty()) return null;
        return humanCollection.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    public List<?> filterByMinutes(Long minutesOfWaiting) {
        return humanCollection.stream().filter(humanBeing -> Objects.equals(
                humanBeing.getMinutesOfWaiting(), minutesOfWaiting)).collect(Collectors.toList());
    }

    public void clear() {
        humanCollection.clear();
    }

    public HumanBeing removeHead() {
        HumanBeing firstHuman = getHead();
        humanCollection.removeFirst();
        return firstHuman;
    }

    public HumanBeing removeLast() {
        HumanBeing lastHuman = getLast();
        humanCollection.removeLast();
        return lastHuman;
    }

    public List<?> filterGreaterThanSpeed(int speed){
        return humanCollection.stream().filter(
                humanBeing -> humanBeing.getImpactSpeed() > speed).collect(Collectors.toList());
    }

    public HumanBeing getHead(){
        return humanCollection.stream().findFirst().orElse(null);
    }

    private HumanBeing getLast() {
        if(humanCollection.size() != 0) return humanCollection.getLast();
        return null;
    }

}
