package g53sqm.chat.server;
import java.awt.*;
import java.io.*;
import java.net.Socket;


public class Client {

    private Socket socket;
    private OnMessageReceivedListener listener;
    public PrintWriter writer;
    private int loginFlag = 0;
    public int hailFlag = 0;
    public int p_messageFlag = 0;


    public Client(){
        int Port=9000;

        try {
            socket = new Socket("localhost",Port);
            OutputStream OutputStream = socket.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(OutputStream));
        }
        catch (IOException e){
            System.err.println("Connection error!");
        }

    }

    public void login(String Username){
        String login = "IDEN ";
        String login_username = login + Username;
        writer.println(login_username);
        writer.flush();
        loginFlag = 1;
    }

    public void generateStat(){
        String stat = "STAT";
        writer.println(stat);
        writer.flush();
    }

    public void Quit(){
        String quit = "QUIT";
        writer.println(quit);
        writer.flush();
    }


    public void addListener(OnMessageReceivedListener listener) {
        this.listener = listener;
        getMessage();
        ListRequest();
    }

    public interface OnMessageReceivedListener {
        void OnMessageReceived(String newMessage);
    }

    private void getMessage(){
        new Thread(
                new Runnable(){
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            BufferedReader received = new BufferedReader(new InputStreamReader(inputStream));
                            while (true){
                                String receive = received.readLine();
                                    if ( receive != null) //receive from server
                                    {
                                        listener.OnMessageReceived(receive);
                                    }

                            }
                        }
                        catch(IOException e){
                            System.err.println("Connection error!");
                        }
                    }
                }
        ).start();
    }

    public void ListRequest(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                if(loginFlag==1) {
                                    //System.out.println("IT WORKS");
                                    writer.println("LIST");
                                    writer.flush();
                                }

                                Thread.sleep(500);

                             }
                             catch (Exception e){
                                    e.printStackTrace();
                                    break;
                            }
                        }

                    }
                }
        ).start();

    }



    public void SendMessage(String Message, String user){
        try {
            if (hailFlag == 1){
                Message = "HAIL " + Message;
                hailFlag = 0;
            } else if (p_messageFlag == 1){
                Message = "MESG " + user + " " + Message;
                p_messageFlag = 0;
            }
            writer.println(Message);
            writer.flush();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
