package g53sqm.chat.server;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class ServerTest extends TestCase{
    private static int port = 9000;
    private Socket socket = null;
    private ObjectOutputStream objOutputStream;
    private ObjectInputStream objInputStream;
    private PrintWriter writer;
    private BufferedReader received;

    @BeforeClass
    public void setUp() throws Exception {
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
    public void tearDown() throws Exception {
    }


    @Test
    public void testConnection(){
        try{
            setUp();
            writer.println("This is a request");
            writer.flush();
            String message = received.readLine();
            assertEquals("OK Welcome to the chat server, there are currently 1 user(s) online", message);
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    @Test
    public void testgetUserList() {
    }

    @Test
    public void testdoesUserExist() {
       try{
           writer.println("IDEN Fara");
           writer.flush();
           received.readLine();
           writer.println("LIST");
           writer.flush();
           String message = received.readLine();
           assertTrue(message.contains("Fara"));
           assertFalse(message.contains("Ferdous"));
       }
       catch(Exception e){
           e.printStackTrace();
        }



    }

    @Test
    public void testbroadcastMessage() {
    }

    @Test
    public void testsendPrivateMessage() {
    }

    @Test
    public void testremoveDeadUsers() {
    }

    @Test
    public void testgetNumberOfUsers() {
    }
}