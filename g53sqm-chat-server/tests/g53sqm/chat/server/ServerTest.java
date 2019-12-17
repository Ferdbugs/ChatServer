package g53sqm.chat.server;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest extends TestCase {
    private static int port = 9000;
    private Socket socket = null;
    private ObjectOutputStream objOutputStream;
    private ObjectInputStream objInputStream;

    @Test
    public void testClientCall() throws Exception {

        getSocket();

        objOutputStream.writeObject("This is a request from " + Thread.currentThread().getName());
        objOutputStream.reset();
        objOutputStream.writeObject(Boolean.TRUE);
        objOutputStream.flush();
        objOutputStream.reset();

        Object obj = objInputStream.readObject();
        objInputStream.readObject();

        assertEquals("This is response.", obj);

    }

//    @Test
//    public void testFailure(){
//        fail("This is a check to make sure failures are reported");
//    }

    @Test
    public void getSocket() throws IOException{
        if(socket == null){
            try{
                socket = new Socket("localhost", port);
                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                BufferedInputStream in = new BufferedInputStream(socket.getInputStream());

                objOutputStream = new ObjectOutputStream(out);
                objInputStream = new ObjectInputStream(in);

            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else {
            objOutputStream.reset();
            objOutputStream.writeByte(1);
            objOutputStream.flush();
            objOutputStream.reset();
            objInputStream.readByte();
        }
    }

    @Test
    public void getUserList() {
    }

    @Test
    public void doesUserExist() {
    }

    @Test
    public void broadcastMessage() {
    }

    @Test
    public void sendPrivateMessage() {
    }

    @Test
    public void removeDeadUsers() {
    }

    @Test
    public void getNumberOfUsers() {
    }

    @Test
    public void testFinalize() {
    }
}