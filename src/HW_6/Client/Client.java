package HW_6.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private Socket socket;
    public Client() {
        start();
        communicate();
        System.out.println("Closing the connection ...");
        System.out.println("Shutting down ...");
        System.out.println("STATUS OK");

    }

    private void start(){

        try {
            Thread.sleep(3000);
            socket = new Socket("localhost", 8899);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void communicate(){
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scn = new Scanner(System.in);

            AtomicBoolean isShutDown = new AtomicBoolean(true);

            new Thread(() -> {
                try {
                    while (true){
                        String inboundMessage = in.readUTF();
                        if(inboundMessage.equals("-end")){
                            isShutDown.set(false);
                            break;
                        }
                            System.out.println("Server said: " + inboundMessage);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }}).start();

            while (true){
                System.out.println("Please input message...");
                String outMessage = scn.nextLine();
                if(!isShutDown.get()){
                    System.out.println("Client closing...");
                    System.out.println("STATUS OK");
                    break;
                }
                out.writeUTF(outMessage);
                System.out.println("Client say..." + outMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
