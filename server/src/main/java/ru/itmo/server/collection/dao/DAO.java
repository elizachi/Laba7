package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

import java.util.ArrayList;

public interface DAO {
    void addSQL(HumanBeing humanBeing);
    void updateSQL(HumanBeing humanBeing);
    void deleteSQL(int index);
    ArrayList<String> getSQL(String column, Object value);
    ArrayList<Integer> getAllSQL();
    int size();
}
