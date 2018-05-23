package server;

import java.net.*;
import java.io.*;

class SendData implements Runnable{
    Thread thrd;
    Socket socket;
    int filePort;

    SendData (String name, Socket s, int fP){
        thrd = new Thread(this, name);
        thrd.start();
        socket = s;
        filePort = fP;
    }

    public void run() {
        try{
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            char ch;
            int spaceIndex;

            SendFile sendfile = new SendFile(filePort);

            while (true) {
                line = keyboard.readLine();
                ch = line.charAt(0);
                spaceIndex = line.indexOf(' ');
                boolean isExit = false;
                if(ch == '@'){
                    switch (line.substring(0, spaceIndex)){
                        case "@sendFile":
                            System.out.println("Start sending file");
                            out.writeUTF(line);
                            sendfile.sendFile(line.substring(spaceIndex + 1));
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
                out.writeUTF(line);
                out.flush();
                System.out.println();
                if (isExit) break;
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
