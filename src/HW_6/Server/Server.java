package HW_6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

            while (true){
                String inboundMessage= in.readUTF();
                if (inboundMessage.equals("-exit")){
                    out.writeUTF("ECHO-Server: Good bay!");
//                    System.out.println("ECHO-Server: Good bay!");
                    out.writeUTF("-end");
                    break;
                }
                out.writeUTF("ECHO: " + inboundMessage);

                System.out.println("Message: " + inboundMessage);
            }
//            client.getOutputStream();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
