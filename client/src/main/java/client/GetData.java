package client;

import java.net.*;
import java.io.*;

class GetData implements Runnable{
    Thread thrd;
    Socket socket;
    int serverFilePort;
    InetAddress adress;

    GetData (String name, Socket s, int sFP, InetAddress ip){
        thrd = new Thread(this, name);
        thrd.start();
        socket = s;
        serverFilePort = sFP;
        adress = ip;
    }

    public void run() {
        try(
            //InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ){
            String line = null;
            char ch;
            int spaceIndex;
            boolean isExit = false;

            GetFile getfile = new GetFile(serverFilePort, adress);

            while (true) {
                line = in.readUTF();
                ch = line.charAt(0);
                spaceIndex = line.indexOf(' ');
                if(spaceIndex < 0) spaceIndex = line.length();
                if(ch == '@'){
                    switch(line.substring(0, spaceIndex)){
                        case "@sendFile":
                            System.out.println("Getting file");
                            getfile.getFile();
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
                    if (isExit) {
                        System.exit(0);
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
