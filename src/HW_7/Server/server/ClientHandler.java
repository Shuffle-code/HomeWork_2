package HW_7.Server.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class ClientHandler {
    /* Создали свойства класса, отвечающего за поведение "клиента" */
    private Server server;
    private Socket socket;
    private DataInputStream in; /*входящие данные*/
    private DataOutputStream out;/*изходящие данные*/
    private String name;

    public ClientHandler(Server server, Socket socket) { /*Конструктор, с входящими значениями server и socket*/

        try { /*Исключение для потоков*/
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> { /*Новый поток*/
                try {
                    doAuthentication();
                    listenMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection(socket);
                }
            })
                    .start();
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during client establishing...", e);
        }
    }

    private void closeConnection(Socket socket) {/*Закрытие соединеий, in, out, socket через close*/
        server.unsubscribe(this);
        server.broadcastMessage(String.format("User[%s] is out.", name));

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    private void doAuthentication() throws IOException /*Избаление от обработки исключений*/{
        while (true) { /*Бесконечный цикл, до return*/
            String maybeCredentials = in.readUTF(); /* Создали экземпляр класса, с помощью входящего потока*/
            /** sample: -auth login1 password1 */
            if (maybeCredentials.startsWith("-auth")) {/*Если: запрос на аунтификацию ..., ключевое слово "-auth"*/
                String[] credentials = maybeCredentials.split("\\s");/* Получение массива из строки по пробелу*/

                Optional<AuthService.Entry> maybeUser = server.getAuthService()/* Создали перменную класса после обработки методом findUserByLoginAndPassword*/
                        .findUserByLoginAndPassword(credentials[1], credentials[2]);

                if (maybeUser.isPresent()) {/* Если значение присутствует, возвращает истину, в противном случае - ложь.*/
                    AuthService.Entry user = maybeUser.get();
                    if (server.isNotUserOccupied(user.getName())) {/* Проверка пользователя, на: уже зарегистрирован */
                        name = user.getName();/*Присвоили имя после всех проверок*/
                        sendMessage("AUTH OK.");
                        sendMessage("Welcome.");
                        server.broadcastMessage(String.format("User[%s] entered chat.", name));
                        server.subscribe(this);/*добавление самого себя: this, после рассылки всем инфы о твоем подключении*/
                        return;
                    } else {
                        sendMessage("Current user is already logged in");
                    }
                } else {
                    sendMessage("Invalid credentials.");
                }
            } else {
                sendMessage("Invalid auth operation");
            }
        }
    }

    public void sendMessage(String outboundMessage) {/*Отправка сообщений, на вход принимает сообщение которое надо отправить*/
        try {
            out.writeUTF(outboundMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenMessages() throws IOException {/*Слушатель сообщений*/
        while (true) {
            String inboundMessage = in.readUTF();
            if (inboundMessage.equals("-exit")) {/*Закрывает программу при получении "-exit"*/
                break;
            }
            server.broadcastMessage(inboundMessage);/*Рассылает всем прочитанное сообщение */
        }
    }

}
