package server;

import java.net.*;
import java.io.*;

class SendFile{
    String fileName;
    int filePort;

    SendFile(int fP){
        filePort = fP;
    }

    public void sendFile(String fileName){
        try (ServerSocket ssFile = new ServerSocket(filePort);
             Socket socketFile = ssFile.accept();
        ){
            File file = new File(fileName);
            OutputStream outStream = socketFile.getOutputStream();
            DataOutputStream dataOut = new DataOutputStream(outStream);
            FileInputStream fOut = new FileInputStream(file);
            int buffer;
            dataOut.writeUTF(fileName);
            while(true){
                buffer = fOut.read();
                if(buffer == -1) {
                    System.out.println("File sent");
                    break;
                }
                dataOut.write(buffer);
            }
        } catch (Exception exc){ exc.printStackTrace();
        }
    }
}
