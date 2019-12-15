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
    @FXML TextArea UserList;
    Client NewClient;

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
                            if (Message.contains("USERS$@4412")) {
                                UserList.setText(Message.substring(11).replaceAll(" ","\n"));
                            }
                            else {
                                if(!Message.equals("")) {
                                    ChatBox.appendText("Server : " + Message + "\n");
                                }
                            }
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
        String User = Username.getText();
        NewClient.login(User);
    }


}
