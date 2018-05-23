package server;

import java.net.*;
import java.io.*;

class GetData implements Runnable{
    Thread thrd;
    Socket socket;
    int filePort;

    GetData (String name, Socket s, int fP){
        thrd = new Thread(this, name);
        thrd.start();
        socket = s;
        filePort = fP;
    }

    public void run(){
        try(
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ){
            String line = null;
            char ch;
            int spaceIndex;
            GetFile getFile = new GetFile(filePort);

            while (true) {
                line = in.readUTF();
                ch = line.charAt(0);
                spaceIndex = line.indexOf(' ');
                if(spaceIndex < 0) spaceIndex = line.length();
                if(ch == '@'){
                    switch(line.substring(0, spaceIndex)){
                        case "@sendFile":
                            System.out.println("Getting file");
                            getFile.getFile();
                            break;
                        case "@ipInfo":
                            InetInfo.inetInfo();
                            break;
                        case "@help":
                            System.out.println(" @exit – exit program");
                            System.out.println(" @help – Print all commands");
                            System.out.println(" @ipInfo – Show ip");
                            System.out.println(" @sendFile filename – Send file");
                            break;
                        case "@exit":
                            System.out.println("EXIT PROGRAM");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Unknown command, use @help");
                    }
                    continue;
                }
                System.out.println(line);
                System.out.flush();
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
