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
        try(
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ){

            String line = null;
            char ch;
            int spaceIndex;

            SendFile sendfile = new SendFile(filePort);

            while (true) {
                line = keyboard.readLine();
                if (line.length() == 0) continue;
                ch = line.charAt(0);
                spaceIndex = line.indexOf(' ');
                if(spaceIndex < 0) spaceIndex = line.length();
                if(ch == '@'){
                    switch (line.substring(0, spaceIndex)){
                        case "@sendFile":
                            if(line.indexOf(' ') == -1){
                                System.out.println("Enter file name");
                                break;
                            }
                            if(new File(line.substring(spaceIndex + 1)).exists() == false){
                                System.out.println("File not found");
                                break;
                            }
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
                            System.out.println("EXIT PROGRAM");
                            out.writeUTF(line);
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Unknown command, use @help");
                    }
                    continue;
                }
                out.writeUTF(line);
                out.flush();
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
