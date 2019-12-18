package g53sqm.chat.server;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class TestServerTest extends TestCase{
    private static int port = 9000;
    private Socket socket = null;
    private PrintWriter writer;
    private BufferedReader received;

    public void setUp() {
        if(socket == null){
            try{
                socket = new Socket("localhost", port);
                OutputStream OutputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();

                writer = new PrintWriter(new OutputStreamWriter(OutputStream));
                received = new BufferedReader(new InputStreamReader(inputStream));

            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @After
    public void tearDown() {
    }


    public void testConnection(){
        try{
            writer.println("This is a request");
            writer.flush();
            String message = received.readLine();
            assertEquals("OK Welcome to the chat server, there are currently 1 user(s) online", message);
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    public void testgetUserList() {
        try {
            received.readLine();
            writer.println("IDEN Ferd");
            writer.flush();
            received.readLine();
            writer.println("LIST");
            writer.flush();
            String message = received.readLine();
            assertEquals("USERS$@4412 Poka Ferd ",message);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void testdoesUserExist() {
       try{
           writer.println("IDEN Poka");
           writer.flush();
           received.readLine();
           writer.println("LIST");
           writer.flush();
           String message = received.readLine();
           assertTrue(message.contains("Poka"));
           assertFalse(message.contains("Ferdous"));
       }
       catch(Exception e){
           e.printStackTrace();
        }



    }

    public void testbroadcastMessage() {
        try {
            received.readLine();
            writer.println("IDEN Lola");
            writer.flush();
            received.readLine();
            writer.println("HAIL Hello Guys!");
            writer.flush();
            String message = received.readLine();
            assertEquals("Broadcast from Lola: Hello Guys!", message);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void testsendPrivateMessage() {
        try{
            received.readLine();
            writer.println("IDEN Cola");
            writer.flush();
            received.readLine();
            writer.println("MESG Ferd Hello!");
            writer.flush();
            String message = received.readLine();
            assertEquals("OK your message has been sent", message);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void testremoveDeadUsers() {
    }

    public void testAlreadyLoggedIn(){
        try{
            received.readLine();
            writer.println("IDEN Ford");
            writer.flush();
            received.readLine();
            writer.println("IDEN Ford");
            writer.flush();
            String message = received.readLine();
            assertEquals("BAD you are already registered with username Ford", message);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void testgetNumberOfUsers() {
    }
}