package com.aptech.project2.Controller;

import com.aptech.project2.Model.FxmlLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMenuCoach;

    @FXML
    private Button btnMenuDashBroad;

    @FXML
    private Button btnMenuMember;

    @FXML
    private Button btnMenuPayment;

    @FXML
    private SplitPane girdMenu;

    @FXML
    private Label txtAdminName;
    @FXML
    private Circle circleIMG;

    private Alert alert;

    @FXML
    public void Logout(ActionEvent event){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error Message");
        alert.setContentText("Are you sure want to logout?");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get()==ButtonType.OK){
            btnLogout.getScene().getWindow().hide();
            Parent root = FxmlLoader.getInstance().getViewPane("/com/aptech/project2/Login/Login.fxml");
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
    }
   public void ActionExit (ActionEvent event){
       Platform.exit();
       System.exit(0);
   }
    public void displayUserName(){

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        circleIMG.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/user.png"))));
        displayUserName();

        displayDashBroad();
        btnMenuDashBroad.setOnAction(event -> {
            displayDashBroad();
        });
        btnMenuMember.setOnAction(event -> {
            displayMember();
        });
        btnMenuPayment.setOnAction(event -> {
            displayPayment();
        });
        btnMenuCoach.setOnAction(event -> {
            displayCoach();
        });

    }


    public void displayDashBroad(){
        Parent root = FxmlLoader.getInstance().getViewPane("/com/aptech/project2/Menu/DashBroad.fxml");
        Scene scene = new Scene(root);
        girdMenu.getItems().set(1, scene.getRoot());
    }
    public void displayCoach(){
        Parent root = FxmlLoader.getInstance().getViewPane("/com/aptech/project2/Coaches/Index.fxml");
        Scene scene = new Scene(root);
        girdMenu.getItems().set(1, scene.getRoot());
    }

    public void displayMember(){
        Parent root = FxmlLoader.getInstance().getViewPane("/com/aptech/project2/Member/Index.fxml");
        Scene scene = new Scene(root);
        girdMenu.getItems().set(1, scene.getRoot());
    }

    public void displayPayment(){
        Parent root = FxmlLoader.getInstance().getViewPane("/com/aptech/project2/Payment/Payment.fxml");
        Scene scene = new Scene(root);
        girdMenu.getItems().set(1, scene.getRoot());
    }



}