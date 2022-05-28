package ru.itmo.server.collection.commands;

public interface Command {
    Object execute(Object arguments);
}
