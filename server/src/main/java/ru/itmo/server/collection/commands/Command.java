package ru.itmo.server.collection.commands;

import ru.itmo.common.responses.Response;

public interface Command {
    Response execute(Object arguments);
}
