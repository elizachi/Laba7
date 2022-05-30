package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.client.to_server.ServerAPI;
import ru.itmo.client.to_server.ServerAPIImpl;
import ru.itmo.common.User;
import ru.itmo.common.commands.CommandType;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;

import java.io.IOException;
import java.util.Arrays;

/**
 * Класс, содержащий основную логику работы клиента
 */
public class Client {
    private final MessageManager msg = new MessageManager();
    private final AskInput ask = new AskInput();
    private final String serverHost;
    private final int serverPort;
    private final int connectionAttempts = 20;
    private final int connectionTimeout = 2000;

    public Client(String serverHost, int serverPort){

        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        ServerAPI serverAPI = new ServerAPIImpl(serverHost, serverPort);
        boolean run = true;

        while (!serverAPI.connectToServer()) {
            if(serverAPI.getAttempts() > connectionAttempts){
                System.err.println("Превышено количество попыток подключиться");
                return;
            }
            try {
                Thread.sleep(connectionTimeout);
            } catch (InterruptedException e) {
                System.err.println("Произошла ошибка при попытке ожидания подключения!");
                System.err.println("Повторное подключение будет произведено немедленно.");
            }
        }

        //вот тут происходит авторизация
        User user = userProcessing(serverAPI);

        while(run) {
            try {
                CommandType commandType = ask.askCommand(ReaderManager.getHandler());
                HumanBeing human = ask.askInputManager(commandType, ReaderManager.getHandler());

                Response response = serverAPI.executeCommand(commandType, human, user);
                if(response.status == Response.Status.OK) {
                    if(response.getArgumentAs(String.class) != null) {
                        System.out.println(response.getArgumentAs(String.class));
                    }
                } else if(response.status == Response.Status.SERVER_EXIT) {
                    System.out.println("Клиент завершает свою работу.");
//                    serverAPI.closeConnection();
                    run = false;
                } else if(response.status == Response.Status.ERROR) {
                    System.err.println("В процессе выполнения данной команды произошла ошибка.");
                } else if(response.status == Response.Status.WARNING) {
                    System.out.println("Внимание! "+response.getArgumentAs(String.class));
                }
            } catch (NullPointerException e) {
                ReaderManager.returnOnPreviousReader();
                ask.removeLastElement();
            } catch (RuntimeException e) {
                e.printStackTrace();
                System.err.println("Непредвиденная ошибка.");
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                if (e.getType() == TypeOfError.CONNECTION_BROKEN) {

                    System.out.println("Попытка переподключиться..");
                    while (!serverAPI.connectToServer()){
                        if(serverAPI.getAttempts() > connectionAttempts){
                            System.err.println("Превышено количество попыток подключиться.");
                            return;
                        }
                        try {
                            Thread.sleep(connectionTimeout);
                        } catch (InterruptedException exception) {
                            System.err.println("Произошла ошибка при попытке ожидания подключения.");
                        }
                    }

                }
                if (e.getType() == TypeOfError.CONNECTED_REFUSE) {
                    run = false;
                }
                if(e.getType() == TypeOfError.NOT_STARTED) {
                    run = false;
                }
            }

        }
    }

    /**
     * Регистрация клиента
     * @return авторизованный клиент
     */
    private User userProcessing(ServerAPI serverAPI){
        User user = null;
        boolean flag = true;
        boolean isAuthorized = ask.askAuthorization(ReaderManager.getHandler());
        if (isAuthorized) {
            String checkedLogin = checkLogin(serverAPI);
            //если логин не найден
            if (checkedLogin == null) {
                //вопрос, создать новый аккаунт или попробовать снова
                user = tryAuthorize(checkedLogin);
            } else {
                //если логин найден
                //проверка пароля
                //TODO доделать
            }
        } else {
            user = newLogin(serverAPI);
        }
        return user;
    }

    private User tryAuthorize(String name){
        User user;
        if (ask.askNewAccount(ReaderManager.getHandler())) {
            user = newLogin();
        } else {
            try {
                user = ask.repeatAuthorization(ReaderManager.getHandler(), name);
            } catch (IOException e) {
                msg.printErrorMessageIO();
                user = null;
            }
        }
        return user;
    }

    /**
     * Метод для проверки наличия логина в базе существующих логинов пользователей
     * @return пароль пользователя (если логин уже есть) или null (если такого логина среди созданных нет)
     * @throws WrongArgumentException
     */
    private String checkLogin(ServerAPI serverAPI) {
        HumanBeing human = null;
        try {
            human = ask.askInputManager(CommandType.CHECK_USER, ReaderManager.getHandler());
            User user = new User(human.getName(), null);
            Response response = serverAPI.executeCommand(CommandType.CHECK_USER, human, user);
            if (response.status == Response.Status.OK) {
                return response.getArgumentAs(String.class);
            }
        } catch (WrongArgumentException e) {
            msg.printErrorMessage(e);
        }
        return null;
    }

    private User newLogin(ServerAPI serverAPI){
        if (checkLogin(serverAPI) == null) {
            String password = ask.askPassword(ReaderManager.getHandler());
        } else {
            System.err.println("Такой логин уже существует. Придумайте другой.");
            newLogin(serverAPI);
            return  null;
        }

        return new User(name, password);
    }
}
