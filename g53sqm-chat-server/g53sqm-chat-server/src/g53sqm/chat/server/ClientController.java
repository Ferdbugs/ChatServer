package g53sqm.chat.server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.Socket;

public class ClientController {

    @FXML TextArea ChatBox;
    @FXML TextField ChatField;
    @FXML Button Send;
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
                        ChatBox.appendText("\n" + "Server: " + Message );
                    }
                });
            }
        });
    }

    @FXML
    public void SendMsg(){
        String Message = ChatField.getText();
        ChatBox.appendText("\n" + "You: " + Message);
        ChatField.setText("");
        NewClient.SendMessage(Message);
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
