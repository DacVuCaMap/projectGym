package com.aptech.project2.Controller;

import com.aptech.project2.DAO.UserDao;
import com.aptech.project2.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane FormLogin;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnLogin;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private AnchorPane sideForm;

    @FXML
    private Label txtMesLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;



    private Alert alert;
    @FXML
    public void setBtnCancel(ActionEvent event){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setBtnLogin(ActionEvent event) throws IOException {
        if (txtUsername.getText().isBlank() == true
                || txtPassword.getText().isBlank() == true) {
            txtMesLogin.setText("Please enter Username and Password.");
        } else {
            String userName = txtUsername.getText();
            String password = txtPassword.getText();
            User user = UserDao.getInstance().findByUserName(userName);
            if (user == null) {
                txtMesLogin.setText("Incorrect account or password");
            } else {
                if (!BCrypt.checkpw(password, user.getPassword())) {
                    txtMesLogin.setText("Incorrect account or password.");
                } else {
                    UserDao.userSession = user;
                    btnLogin.getScene().getWindow().hide();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/aptech/project2/Menu/Menu.fxml"));
                    SplitPane pane = loader.load();
                    Scene home = new Scene(pane);
                    Stage stage = new Stage();
                    stage.setScene(home);
                    stage.setTitle("Gym Management Systems");
                    stage.show();
                }
            }
        }
    }

    }





