package HW_7.Server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private final List<ClientHandler> loggedUser;/* Свойство - список клиентов, и для проверки уже подключенных клиентов */
    private final AuthService authService;

    public Server() { /* Конструктор */
        authService = new AuthService();
        loggedUser = new ArrayList<>(); /*Создание списка клиентов*/

        try {/*Прием пользователей*/
            serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept(); /*Связь между сервером и клиентом*/
                new ClientHandler(this, socket);/*Создали клиента, с сылкой на самого себя и socket*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AuthService getAuthService() {
        return authService;
    } /*Геттер для общения с ClientHandler */



    public void subscribe(ClientHandler user) {/*Добавление в список*/
        loggedUser.add(user);
    }

    public void unsubscribe(ClientHandler user) {
        loggedUser.remove(user);
    } /*Удаление из списока*/

    public boolean isNotUserOccupied(String name) {/*Метод обратный isUserOccupied*/
        return !isUserOccupied(name);
    }

    public boolean isUserOccupied(String name) {/*Метод возвращает true, если находит совпадения входящего name и user*/
        /**
         for(ClineHandler user : loggedUser) {
         if (user.getName().equals(user)) {
         return true;
         }
         }
         return false;
         */


//         loggedUser.stream()
//         .filter(u -> u.getName().equals(u))
//         .findFirst()
//         .isPresent();


        return loggedUser.stream()
                .anyMatch(u -> u.getName().equals(name));/*Возвращает, соответствуют ли какие-либо элементы этого потока указанному предикату*/



    }

    public void broadcastMessage(String outboundMessage) {/*Отправка сообщений всем пользователям, sendMessage всем клиентам*/
        /**
         for (ClientHandler user: loggedUser) {
         user.sendMessage(outboundMessage);
         }
         */

        /**
         loggedUser.forEach(new Consumer<ClientHandler>() {
        @Override
        public void accept(ClientHandler clientHandler) {
        clientHandler.sendMessage(outboundMessage);
        }
        });
         */

        loggedUser.forEach(clientHandler -> clientHandler.sendMessage(outboundMessage));
    }
}
