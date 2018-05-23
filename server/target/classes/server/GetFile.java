package server;

import java.net.*;
import java.io.*;

class GetFile{
    String fileName;
    int filePort;

    GetFile(int fP){
        filePort = fP;
    }

    public void getFile(){
        try{
            ServerSocket ssFile = new ServerSocket(filePort);
            Socket socketFile = ssFile.accept();

            InputStream inStream = socketFile.getInputStream();
            DataInputStream dataIn = new DataInputStream(inStream);

            int buffer = 0;
            String fileName1 = dataIn.readUTF();

            File file = new File(fileName1);
            FileOutputStream fIn = new FileOutputStream(file);

            System.out.println("SYSTEM: File: " + fileName1 + " created");

            while(true){
                buffer = dataIn.read();
                if(buffer == -1) break;
                fIn.write(buffer);
            }
            System.out.println("File received");
        } catch (Exception exc){ exc.printStackTrace(); }
    }
}
