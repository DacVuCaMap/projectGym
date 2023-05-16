package com.aptech.project2;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;


import java.io.IOException;
public class IndexApplication extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/aptech/project2/Login/Login.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Sale Shop Management Systems");
        stage.setScene(scene);
        stage.show();
        System.out.println(BCrypt.hashpw("12345", BCrypt.gensalt()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}