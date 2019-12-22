package g53sqm.chat.server;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    static int port = 9000;
    static Socket socket = null;
    static PrintWriter writer;
    static BufferedReader received;
    static Server TestServer;

    @BeforeAll
    public static void setupServer(){
        try {
            new Thread(()->{
                TestServer = new Server(port);
                TestServer.acceptConnections();
            }).start();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserListEmpty(){
        assertNotNull(TestServer.getUserList());
        assertEquals(0,TestServer.getUserList().size());
    }
    @Test
    public void testUserListNotEmpty(){
        try {
            CreateUser();
            writer.println("IDEN Ferd");
            Thread.sleep(100);
            received.readLine();
            assertNotNull(TestServer.getUserList());
            assertEquals(1,TestServer.getUserList().size());
            writer.println("QUIT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void doesUserExist(){
        try {
            CreateUser();
            writer.println("IDEN Ferd");
            Thread.sleep(100);
            received.readLine();
            assertTrue(TestServer.doesUserExist("Ferd"));
            writer.println("QUIT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testBroadcastMessage(){
        try {
            CreateUser();
            received.readLine();
            TestServer.broadcastMessage("Test Broadcast");
            assertEquals("Test Broadcast",received.readLine());
            writer.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPrivateMessage(){
        try {
            CreateUser();
            received.readLine();
            writer.println("IDEN Ferd");
            Thread.sleep(100);
            received.readLine();
            TestServer.sendPrivateMessage("Hello Ferd!","Ferd");
            assertEquals("Hello Ferd!",received.readLine());
            writer.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getNumberOfUsers(){
        try {
            CreateUser();
            received.readLine();
            writer.println("IDEN Ferd");
            Thread.sleep(100);
            received.readLine();
            assertEquals(1,TestServer.getNumberOfUsers());
            writer.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeDeadUsers(){
        try {
            CreateUser();
            received.readLine();
            writer.println("IDEN Ferd");
            Thread.sleep(100);
            received.readLine();
            assertEquals(1,TestServer.getNumberOfUsers());
            writer.println("QUIT");
            Thread.sleep(100);
            received.readLine();
            assertEquals(0,TestServer.getUserList().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CreateUser() throws Exception{
        socket = new Socket("localhost",9000);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        received = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

}

