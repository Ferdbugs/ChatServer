package g53sqm.chat.server;


import java.io.*;
import java.net.Socket;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class ConnectionTestUnregistered {
    static int port = 9000;
    static Socket socket = null;
    static PrintWriter writer;
    static BufferedReader received;
    static Server TestServer;


    @BeforeAll
    public static void SetUpServer() {
        try {
            new Thread(()->{
                TestServer = new Server(port);
                TestServer.acceptConnections();
            }).start();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setupClient(){
        try{
            socket = new Socket("localhost", port);
            OutputStream OutputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            writer = new PrintWriter(new OutputStreamWriter(OutputStream));
            received = new BufferedReader(new InputStreamReader(inputStream));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testBroadcastWithoutLogin(){
        try{
            setupClient();
            received.readLine();
            writer.println("HAIL Hello!");
            writer.flush();
            String message = received.readLine();
            assertEquals("BAD You have not logged in yet",message);
            writer.println("QUIT");
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public  void testPrivateMessageWithoutLogin(){
        try{
            setupClient();
            received.readLine();
            writer.println("MESG Kola Hello!");
            writer.flush();
            String message = received.readLine();
            assertEquals("BAD You have not logged in yet",message);
            writer.println("QUIT");
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMessageBadUser(){
        try{
            setupClient();
            received.readLine();
            writer.println("IDEN Watt");
            writer.flush();
            received.readLine();
            writer.println("MESG Pojk Hi!");
            writer.flush();
            String message = received.readLine();
            assertEquals("BAD the user does not exist",message);
            writer.println("QUIT");
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testMessageStatWithoutLogin(){
        try{
            setupClient();
            received.readLine();
            writer.println("STAT");
            writer.flush();
            String message = received.readLine();
            assertEquals("OK There are currently 1 user(s) on the server. You have not logged in yet",message);
            writer.println("QUIT");
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testUnregisteredQuit(){
        try{
            setupClient();
            received.readLine();
            writer.println("QUIT");
            writer.flush();
            String message = received.readLine();
            assertEquals("OK goodbye",message);
            writer.println("QUIT");
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testListRequestUnregistered(){
        try{
            setupClient();
            received.readLine();
            writer.println("LIST");
            writer.flush();
            String message = received.readLine();
            assertEquals("BAD You have not logged in yet",message);
            writer.println("QUIT");
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testBadCommand(){
        try{
            setupClient();
            received.readLine();
            writer.println("Any Command");
            writer.flush();
            String msg = received.readLine();
            assertEquals("BAD command not recognised",msg);
            writer.println("QUIT");
            writer.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testInvalidCommand(){
        try{
            setupClient();
            received.readLine();
            writer.println("AB");
            writer.flush();
            String msg = received.readLine();
            assertEquals("BAD invalid command to server",msg);
            writer.println("QUIT");
            writer.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}