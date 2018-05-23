//mvn exec:java

package client;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] ar) {
        int serverPort = 7121;
        int serverFilePort = 7122;
        int serverFilePort1 = 7120;
        String address = "192.168.0.102";

        try(
        BufferedReader ipBuffer = new BufferedReader(new InputStreamReader(System.in));
        ){
            InetInfo.inetInfo();
            System.out.print("Enter server IP: ");
            address = ipBuffer.readLine();

            InetAddress ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, serverPort);
            System.out.println("Connect to server v.01");
            System.out.println();

            SendData seTh = new SendData("Server", socket, serverFilePort, ipAddress);
            GetData clTh = new GetData("Client", socket, serverFilePort1, ipAddress);

            clTh.thrd.join();
            seTh.thrd.join();

        } catch (UnknownHostException UnknownHostExc) {
            System.out.println("Incorrect IP adress");
        } catch (Exception x) { x.printStackTrace(); }
    }
}
