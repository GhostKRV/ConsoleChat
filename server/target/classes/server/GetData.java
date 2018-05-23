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
        try{
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            char ch;
            int spaceIndex;

            GetFile getFile = new GetFile(filePort);

            while (true) {
                line = in.readUTF();
                ch = line.charAt(0);
                spaceIndex = line.indexOf(' ');
                boolean isExit = false;
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
                            isExit = true;
                            break;
                        default:
                            System.out.println("Unknown command, use @help");
                    }
                    continue;
                }
                System.out.println(line);
                System.out.flush();
                if (isExit) break;
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
