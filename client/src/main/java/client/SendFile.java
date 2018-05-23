package client;

import java.net.*;
import java.io.*;

class SendFile{
    String fileName;
    int serverFilePort;
    InetAddress ipAddress;

    SendFile(int sFP, InetAddress ip){
        serverFilePort = sFP;
        ipAddress = ip;
    }

    public void sendFile(String fileName){
        try(
            Socket socketFile = new Socket(ipAddress, serverFilePort);

        ){
            int buffer;
            File file = new File(fileName);
            DataOutputStream dataOut = new DataOutputStream(socketFile.getOutputStream());
            FileInputStream fOut = new FileInputStream(file);
            dataOut.writeUTF(fileName);
            while(true){
                buffer = fOut.read();
                if(buffer == -1) {
                    System.out.println("END TRANSMISSION");;
                    break;
                }
                dataOut.write(buffer);
            }
            System.out.println("File sent");
        } catch (Exception exc){ exc.printStackTrace(); }
    }
}
