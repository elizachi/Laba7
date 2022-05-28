package ru.itmo.server.collection.commands;

public class ExitCommand implements Command{

    @Override
    public Object execute(Object arguments) {
        return "exit";
    }
}
