package g53sqm.chat.server;

import java.io.*;
import java.net.Socket;

public class Client2 {
    static Socket socket = null;
    static String input = "";
    static String output = "";

    public static void main(String[] arghs){

        String receive, send;
        BufferedReader Read;

        int Port = 9000;
        try {
            socket = new Socket("localhost", Port);
            Read = new BufferedReader(new InputStreamReader(System.in));

            OutputStream OutputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(OutputStream,true);

            InputStream inputStream = socket.getInputStream();
            BufferedReader received = new BufferedReader(new InputStreamReader(inputStream));

            while (true){
                if((receive = received.readLine()) != null) //receive from server
                {
                    System.out.println(receive); // displaying at DOS prompt
                }
                send = Read.readLine();
                writer.println(send);
                writer.flush();
            }

        }
        catch (IOException e){
            System.err.println("Connection error!");
            e.printStackTrace();
        }

    }
}
