package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

public interface DAO {
    int add(HumanBeing humanBeing);
    boolean update(HumanBeing humanBeing);
    boolean delete(int index);
    HumanBeing get(int id);
}
