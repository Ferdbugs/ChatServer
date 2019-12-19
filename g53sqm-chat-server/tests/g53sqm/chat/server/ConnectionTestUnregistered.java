package g53sqm.chat.server;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class ConnectionTestUnregistered extends TestCase{
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

    public void testBroadcastwithoutlogin(){
        try{
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
    public  void testPrivateMessagewithoutlogin(){
        try{
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
    public void testMessageBadUser(){
        try{
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
    public void testMessageStatWithoutLogin(){
        try{
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

    public void testUnregisteredQuit(){
        try{
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
    public void testListrequestunregistered(){
        try{
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
    public void testBadcommand(){
        try{
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
    public void testInvalidcommand(){
        try{
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