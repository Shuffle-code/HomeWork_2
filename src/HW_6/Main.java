package HW_6;

import HW_6.Client.Client;
import HW_6.Server.Server;

public class Main {
    public static void main(String[] args) {
        new Thread(Server::new).start();
        new Thread(() -> new Client()).start();

    }
}
