package g53sqm.chat.server;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnectionTestRegistered {
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

            writer = new PrintWriter(new OutputStreamWriter(OutputStream), true);
            received = new BufferedReader(new InputStreamReader(inputStream));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testQuit(){
        try{
            setupClient();
            received.readLine();
            writer.println("IDEN Fara");
            received.readLine();
            writer.println("QUIT");
            String message = received.readLine();
            assertEquals("OK thank you for sending 0 message(s) with the chat service, goodbye. ",message);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    public void testConnection(){
        try{
            setupClient();
            String message = received.readLine();
            assertEquals("OK Welcome to the chat server, there are currently 1 user(s) online", message);
            writer.println("QUIT");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(3)
    public void testgetUserList() {
        try {
            setupClient();
            received.readLine();
            writer.println("IDEN Ferd");
            received.readLine();
            writer.println("LIST");
            String message = received.readLine();
            assertEquals("USERS$@4412 Ferd ",message);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testdoesUserExist() {
       try{
           setupClient();
           received.readLine();
           writer.println("IDEN Poka");
           received.readLine();
           writer.println("LIST");
           String message = received.readLine();
           assertTrue(message.contains("Poka"));
           assertFalse(message.contains("Ferdous"));
           writer.println("QUIT");
       }
       catch(Exception e){
           e.printStackTrace();
        }
    }

    @Test
    public void testBroadcastMessage() {
        try {
            setupClient();
            received.readLine();
            writer.println("IDEN Lola");
            received.readLine();
            writer.println("HAIL Hello Guys!");
            String message = received.readLine();
            assertEquals("Broadcast from Lola: Hello Guys!", message);
            writer.println("QUIT");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(4)
    public void testSendPrivateMessage() {
        try{
            setupClient();
            received.readLine();
            writer.println("IDEN Cola");
            received.readLine();
            writer.println("MESG Ferd Hello!");
            String message = received.readLine();
            assertEquals("OK your message has been sent", message);
            writer.println("QUIT");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMessageBadlyFormatted(){
        try{
            setupClient();
            received.readLine();
            writer.println("IDEN Cola");
            received.readLine();
            writer.println("MESGFerdHello!");
            String message = received.readLine();
            assertEquals("BAD Your message is badly formatted", message);
            writer.println("QUIT");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAlreadyLoggedIn(){
        try{
            setupClient();
            received.readLine();
            writer.println("IDEN Ford");
            received.readLine();
            writer.println("IDEN Ford");
            String message = received.readLine();
            assertEquals("BAD you are already registered with username Ford", message);
            writer.println("QUIT");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    public void testGetNumberOfUsers() {
        try{
            setupClient();
            received.readLine();
            writer.println("IDEN Watt");
            received.readLine();
            writer.println("STAT");
            String message = received.readLine();
            assertEquals("OK There are currently 1 user(s) on the server. You are logged in and have sent 0 message(s)",message);
            writer.println("QUIT");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}