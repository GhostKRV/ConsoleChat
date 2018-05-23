package client;

import java.net.*;
import java.io.*;

class GetFile{
    String fileName;
    int serverFilePort;
    InetAddress ipAddress;

    GetFile(int sFP, InetAddress ip){
        serverFilePort = sFP;
        ipAddress = ip;
    }

    public void getFile(){
        try(
            Socket socketFile = new Socket(ipAddress, serverFilePort);
            DataInputStream dataIn = new DataInputStream(socketFile.getInputStream());
        ){

            int buffer = 0;

            String fileName1 = dataIn.readUTF();

            File file = new File(fileName1);
            FileOutputStream fIn = new FileOutputStream(file);

            System.out.println("SYSTEM: File: " + fileName1 + " created");

            while(true){
                buffer = dataIn.read();
                if(buffer == -1) {
                    System.out.println("File received");
                    break;
                }

                fIn.write(buffer);
            }

        } catch (FileNotFoundException FileNotFoundExc){
            System.out.println("File not found");
        } catch (Exception exc){ exc.printStackTrace(); }
    }
}
