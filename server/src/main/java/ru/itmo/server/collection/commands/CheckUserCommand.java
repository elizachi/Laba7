package ru.itmo.server.collection.commands;

import ru.itmo.common.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.utility.HandleUsers;

public class CheckUserCommand implements Command{
    private final HandleUsers handleUsers;

    public CheckUserCommand(HandleUsers handleUsers){
        this.handleUsers = handleUsers;
    }

    @Override
    public Response execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        String usersLogin = handleUsers.getLogin(humanBeing.getName());
        if (usersLogin != null) {
            User user = new User(usersLogin, handleUsers.getPassword(usersLogin));
            return new Response(Response.Status.OK, user);
        } else {
            return new Response(Response.Status.ERROR, handleUsers.getLogins());
        }
    }
}
