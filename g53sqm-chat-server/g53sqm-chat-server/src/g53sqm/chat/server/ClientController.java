package g53sqm.chat.server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.jws.soap.SOAPBinding;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {

    @FXML TextArea ChatBox;
    @FXML TextField ChatField, Username;
    @FXML Button Send;
    @FXML ListView Userlist;
    Client NewClient;
    Server NewServer;

    public ClientController(){
        NewClient = new Client();
    }

    @FXML
    public void initialize() {
        NewClient.addListener(new Client.OnMessageReceivedListener() {
            @Override
            public void OnMessageReceived(String Message) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ChatBox.appendText("Server : " + Message + "\n" );
                    }
                });
            }
        });
    }

    @FXML
    public void SendMsg(){
        String Message = ChatField.getText();
        ChatBox.appendText( "You : " + Message + "\n");
        ChatField.setText("");
        NewClient.SendMessage(Message);
    }

    @FXML
    public void Connect(){
        String username = Username.getText();
        String login = "IDEN ";
        String login_username = login + username;
        NewClient.writer.println(login_username);
        NewClient.writer.flush();
    }

    @FXML
    public void DisplayUserList(){
        NewServer.getUserList();

    }

    @FXML
    public void UpdateChat(String Message){
        ChatBox.setText(Message);
    }

    @FXML
    public void SetText(){
        ChatField.setText("Works!");
    }

}
