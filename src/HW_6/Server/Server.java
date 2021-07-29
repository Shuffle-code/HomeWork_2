package HW_6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private ServerSocket socket;
    private Socket client;

    public Server() {
        start();
        communicate();
        System.out.println("Closing the connection ...");
        System.out.println("Shutting down ...");
        System.out.println("STATUS OK");
        System.out.println("Please press ENTER");


    }
    private void start(){
        try {
            socket = new ServerSocket(8899);
            System.out.println("Socket created ...");
            System.out.println("Waiting for a connection ...");
            client = socket.accept();
            System.out.println("Client connection: " + client);
            System.out.println("Status OK ...");

        } catch (Exception e) {
            System.out.println("Status NOK ...");
            e.printStackTrace();
        }
    }

    private void communicate(){
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            Scanner scn = new Scanner(System.in);
            AtomicBoolean isShutDown = new AtomicBoolean(true);

            new Thread(() -> {
                while (true) {
                    try {
                        String inboundMessage = in.readUTF();
                        if (inboundMessage.equals("-exit")) {
                            out.writeUTF("ECHO-Server: Good bay! Please press ENTER");
//                            System.out.println("ECHO-Server: Good bay!");
                            out.writeUTF("-end");
                            isShutDown.set(false);
                            break;
                        }
                        System.out.println("Server accepted message: " + inboundMessage);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();


                while (true) {
                    System.out.println("Please input message Server...");
                    String outMessage = scn.nextLine();
                    if(!isShutDown.get()) {
                        System.out.println("Client closing...");
                        System.out.println("STATUS OK");
                        break;
                    }
                    try {
                        out.writeUTF(outMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Server say: " + outMessage);
                }
//            client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
