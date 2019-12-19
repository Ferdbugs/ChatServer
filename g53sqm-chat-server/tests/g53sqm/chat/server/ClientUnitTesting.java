package g53sqm.chat.server;

import junit.framework.TestCase;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class ClientUnitTesting extends TestCase {
    private static int port = 9000;
    private Socket socket = null;
    private Client TestClient;
    private  Client TestClient2;

    public void setUp() {
        try {
            TestClient = new Client();
            TestClient2 = new Client();
            TestClient.received.readLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
    }

    public void testLogin(){
        try{
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
    public void testStat(){
        try{
//            TestClient.received.readLine();
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
    public void testQuit(){
        try{
            TestClient.Quit();
            String message = TestClient.received.readLine();
            assertEquals("OK goodbye",message);
            TestClient2.Quit();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void testSendMessageBroadcast(){
        try{
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
    public void testNotLoggedInSendMessageBroadcast(){
        try{
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
    public void testSendPrivateMessage(){
        try{
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
    public void testNotLoggedInSendPrivateMessage(){
        try{
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
    public void testLoggedInQuit(){
        try{
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

}
