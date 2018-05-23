package server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.net.InetAddress;

public class InetInfo {
    public static void inetInfo()
            throws Exception{
        InetAddress localhost = InetAddress.getLocalHost();

        System.out.println("System IP: " + (localhost.getHostAddress()).trim());

        String systemipaddress = "";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc =
            new BufferedReader(new InputStreamReader(url_name.openStream()));

            systemipaddress = sc.readLine().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Public IP: " + systemipaddress +"\n");
    }
}
