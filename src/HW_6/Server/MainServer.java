package HW_6.Server;

public class MainServer {
    public static void main(String[] args) {
        new Thread(Server::new).start();
    }
}
