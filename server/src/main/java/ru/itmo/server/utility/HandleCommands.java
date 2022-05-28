package ru.itmo.server.utility;

import ru.itmo.common.commands.CommandType;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.ServerLauncher;
import ru.itmo.server.collection.commands.*;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;
import ru.itmo.server.collection.dao.PostgreSqlDao;

public class HandleCommands {
    // TODO POSTGERSQL -> ARRAYDEQUE
    private static final DAO database = new ArrayDequeDAO();

//    public void exit() {
//        try {
//            database.save();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Response handleRequest(Request request) {
        return executeCommand(request.getCommand(), request.getArgumentAs(HumanBeing.class));
    }

    private Response executeCommand(CommandType command, Object commandArgument){
        int commandIndex = command.ordinal();
        commandArgument = commands[commandIndex].execute(commandArgument);
        ServerLauncher.log.info("Запрос успешно обработан");
        return new Response(Response.Status.OK, commandArgument);
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
