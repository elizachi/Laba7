package ru.itmo.server.utility;

import ru.itmo.common.commands.CommandType;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.ServerLauncher;
import ru.itmo.server.collection.commands.*;

public class HandleCommands {

    public Response handleRequest(Request request) {
        return executeCommand(request.getCommand(), request.getArgumentAs(HumanBeing.class));
    }

    private Response executeCommand(CommandType command, Object commandArgument){
        int commandIndex = command.ordinal();
        Response response = commands[commandIndex].execute(commandArgument);
        ServerLauncher.log.info("Запрос успешно обработан");
        return response;
    }

    /**
     * existed commands
     */
    private static final Command[] commands = {
            new AddCommand(),
            new ClearCommand(),
            new ExitCommand(),
            new FilterByMinutesCommand(),
            new FilterGreaterThanSpeedCommand(),
            new HeadCommand(),
            new HelpCommand(),
            new InfoCommand(),
            new PrintUniqueSpeedCommand(),
            new RemoveByIdCommand(),
            new RemoveGreaterCommand(),
            new RemoveHeadCommand(),
            new ShowCommand(),
            new UpdateCommand(),
    };
}
