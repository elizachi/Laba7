package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class ArrayDequeDAO implements DAO{
    private static volatile ArrayDequeDAO instance;
    private Deque<HumanBeing> humanCollection = new ConcurrentLinkedDeque<>();
    private static int availableId = 0;
    private LocalDateTime initDate;

    public ArrayDequeDAO() {
        initDate = LocalDateTime.now();
    }
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

    /**
     * override DAO methods
     */

    @Override
    public int add(HumanBeing human) {
        humanCollection.add(human);
        return human.getId();
    }

    public String toString() {
        return "размер "+humanCollection.size();
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

    @Override
    public ArrayList<Integer> getAllSQL() {
        return null;
    }

    public void clear() {
        humanCollection.clear();
    }

    public void removeHead() {
        humanCollection.removeFirst();
    }

    public void removeLast() {
        humanCollection.removeLast();
    }
//
//    @Override
//    public List<?> filterGreaterThanSpeed(int speed){
//        return humanCollection.stream().filter(humanBeing -> humanBeing.getImpactSpeed() > speed).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<?> filterByMinutes(Long minutesOfWaiting) {
//        return humanCollection.stream().filter(humanBeing -> Objects.equals(humanBeing.getMinutesOfWaiting(), minutesOfWaiting)).collect(Collectors.toList());
//    }
//
//    @Override
//    public void removeGreater(HumanBeing humanBeing) {
//        humanCollection.removeIf(humanBeing1 -> humanBeing1.compareTo(humanBeing) > 0);
//    }
//
//    @Override
//    public HumanBeing get(int id) {
//        return humanCollection.stream().filter(humanBeing -> humanBeing.getId() == id).findFirst().orElse(null);
//    }
//
//    @Override
//    public HumanBeing getHead(){
//        return humanCollection.stream().findFirst().orElse(null);
//    }
//
//    @Override
//    public Collection<HumanBeing> getAll() {
//        return humanCollection;
//    }
//
//    @Override
//    public int size() {
//        return humanCollection.size();
//    }
//
//    @Override
//    public void setAvailableId() {
//        int id;
//        if (humanCollection.isEmpty()) {
//            id = 0;
//        } else {
//            id = getMaxId();
//        }
//        availableId = id + 1;
//    }
//
//    public int getMaxId(){
//        ArrayDeque<HumanBeing> cloneCollection = humanCollection.clone();
//        Integer[] ids = new Integer[cloneCollection.size()];
//        int i = 0;
//        int max;
//        while (!(cloneCollection.isEmpty())) {
//            ids[i] = cloneCollection.poll().getId();
//            i++;
//        }
//        max = Collections.max(Arrays.asList(ids));
//        return max;
//    }
//
//    @Override
//    public void save() {
//        fileManager.writeCollection(humanCollection);
//    }
//
//    @Override
//    public String showCollection(){
//        if (humanCollection.isEmpty()) return null;
//        return humanCollection.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
//    }
//
//    @Override
//    public void sort() {
//        HumanBeing[] humanBeingArray = humanCollection.toArray(new HumanBeing[0]);
//        Arrays.sort(humanBeingArray, new ru.itmo.server.utility.HumanComparator());
//        humanCollection.clear();
//        humanCollection.addAll(Arrays.asList(humanBeingArray));
//    }
}
