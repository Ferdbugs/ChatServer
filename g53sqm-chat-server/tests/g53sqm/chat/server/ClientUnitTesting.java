package g53sqm.chat.server;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClientUnitTesting {
    static int port = 9000;
    static Client TestClient;
    static Client TestClient2;
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

    public void SetupClient() {
        try {
            TestClient = new Client();
            TestClient2 = new Client();
            TestClient.received.readLine();
            TestClient2.received.readLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLogin(){
        try{
            SetupClient();
            TestClient.login("Ferd");
            String message = TestClient.received.readLine();
            assertEquals("OK Welcome to the chat server Ferd",message);
            TestClient.Quit();
            TestClient2.Quit();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    @Test
    public void testStatWithoutLogin(){
        try{
            SetupClient();
            TestClient.generateStat();
            String message = TestClient.received.readLine();
            assertEquals("OK There are currently 2 user(s) on the server. You have not logged in yet",message);
            TestClient.Quit();
            TestClient2.Quit();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testStatLogin(){
        try{
            SetupClient();
            TestClient.login("Koko");
            TestClient.received.readLine();
            TestClient.generateStat();
            String message = TestClient.received.readLine();
            assertEquals("OK There are currently 2 user(s) on the server. You are logged in and have sent 0 message(s)",message);
            TestClient.Quit();
            TestClient.received.readLine();
            Thread.sleep(100);
            TestClient2.Quit();
            TestClient2.received.readLine();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testQuit(){
        try{
            SetupClient();
            TestClient.Quit();
            String message = TestClient.received.readLine();
            assertEquals("OK goodbye",message);
            TestClient2.Quit();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testLoggedInQuit(){
        try{
            SetupClient();
            TestClient.login("Ferd");
            TestClient.received.readLine();
            TestClient.Quit();
            String message = TestClient.received.readLine();
            assertEquals("OK thank you for sending 0 message(s) with the chat service, goodbye. ",message);
            TestClient2.Quit();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testSendMessageBroadcast(){
        try{
            SetupClient();
            TestClient.login("Ferd");
            TestClient.received.readLine();
            TestClient.hailFlag=1;
            TestClient.SendMessage("Hello!",null);
            String message = TestClient.received.readLine();
            assertEquals("Broadcast from Ferd: Hello!",message);
            TestClient.Quit();
            TestClient2.Quit();
            TestClient2.received.readLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testNotLoggedInSendMessageBroadcast(){
        try{
            SetupClient();
            TestClient.hailFlag=1;
            TestClient.SendMessage("Hello!",null);
            String message = TestClient.received.readLine();
            assertEquals("BAD You have not logged in yet",message);
            TestClient.Quit();
            TestClient2.Quit();
            TestClient2.received.readLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testSendPrivateMessage(){
        try{
            SetupClient();
            TestClient2.login("Konan");
            TestClient.login("Ferd");
            TestClient.received.readLine();
            TestClient.p_messageFlag=1;
            TestClient.SendMessage("Hello!","Konan");
            String message = TestClient.received.readLine();
            assertEquals("OK your message has been sent",message);
            TestClient.Quit();
            TestClient2.Quit();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testNotLoggedInSendPrivateMessage(){
        try{
            SetupClient();
            TestClient.p_messageFlag=1;
            TestClient.SendMessage("Hello!","Konan");
            String message = TestClient.received.readLine();
            assertEquals("BAD You have not logged in yet",message);
            TestClient.Quit();
            TestClient2.Quit();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



}
