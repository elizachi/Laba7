package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

import java.util.ArrayList;

public interface DAO {
    int add(HumanBeing humanBeing);
    boolean update(HumanBeing humanBeing);
    boolean delete(int index);
    ArrayList<Integer> getAllSQL();
}
