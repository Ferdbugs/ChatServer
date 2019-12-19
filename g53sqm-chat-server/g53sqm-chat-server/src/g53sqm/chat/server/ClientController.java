package g53sqm.chat.server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class ClientController {

    @FXML TextArea ChatBox;
    @FXML TextField ChatField, Username, UserPMessage;
    @FXML Button Send, ViewStat;
    @FXML TextArea UserList;
    @FXML RadioButton Broadcast, PMessage;
    @FXML ToggleGroup tgMessage;
    Client NewClient;
    public String user;



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
        if(NewClient.p_messageFlag == 1){
            user = UserPMessage.getText();
            NewClient.SendMessage(Message, user);
        } else {
            NewClient.SendMessage(Message, null);
        }

    }

    @FXML
    public void Connect(){
        String User = Username.getText();
        NewClient.login(User);
    }

    @FXML
    public void Hail(){
       NewClient.hailFlag = 1;
       UserPMessage.setText("");
    }

    @FXML
    public void PMessage(){
        NewClient.p_messageFlag = 1;
    }

    @FXML
    public void Stat(){
        NewClient.generateStat();
    }

    @FXML
    public void Disconnect(){
        NewClient.Quit();
    }


}
