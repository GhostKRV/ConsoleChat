package server;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] ar) {
        int port = 7121;
        int filePort = 7122;
        int filePort1 = 7120;
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("v.01 Waiting for a client...");
            Socket socket = ss.accept();
            System.out.println("Connect to client");
            System.out.println();

            GetData seTh = new GetData("Server", socket, filePort);
            SendData clTh = new SendData("Client", socket, filePort1);

            seTh.thrd.join();
            clTh.thrd.join();

        } catch(Exception x) { x.printStackTrace(); }
    }
}
