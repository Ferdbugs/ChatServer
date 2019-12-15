package g53sqm.chat.server;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.Element;
import javax.swing.text.html.ListView;
import java.io.*;

public class ChatGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
