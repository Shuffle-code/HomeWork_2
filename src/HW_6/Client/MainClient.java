package HW_6.Client;
public class MainClient {
    public static void main(String[] args) {
        new Thread(() -> new Client()).start();
    }
}
