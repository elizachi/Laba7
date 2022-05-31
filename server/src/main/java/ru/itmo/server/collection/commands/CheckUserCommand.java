package ru.itmo.server.collection.commands;

import ru.itmo.common.User;
import ru.itmo.common.responses.Response;
import ru.itmo.server.utility.HandleUsers;

public class CheckUserCommand implements Command{
    private final HandleUsers handleUsers;

    public CheckUserCommand(HandleUsers handleUsers){
        this.handleUsers = handleUsers;
    }

    @Override
    public Response execute(Object arguments) {
        User user = (User) arguments;
        String login = user.getUsername();
        String password = User.getHash(user.getPassword());

        if (handleUsers.getLogin(login) == null) {
            User user1 = new User(null, password);
            return new Response(Response.Status.ERROR, user1, user);
        } else if (handleUsers.getPassword(login) == null) {
            User user1 = new User(login, null);
            return new Response(Response.Status.WRONG_PASSWORD, user1, user);
        } else if (handleUsers.getLogin(login).equals(login) && handleUsers.getPassword(login).equals(password)) {
            User user1 = new User(login, password);
            return new Response(Response.Status.OK, user1, user);
        }

        return new Response(Response.Status.WARNING, "Что-то пошло не так.", user);
    }
}
