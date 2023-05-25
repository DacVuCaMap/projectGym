package com.aptech.project2.Controller;

import com.aptech.project2.DAO.UserDao;
import com.aptech.project2.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCancle;

    @FXML
    private Button btnConfirm;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameNof;

    @FXML
    private PasswordField passField;

    @FXML
    private Label passNof;

    @FXML
    private PasswordField repassField;

    @FXML
    private Label repassNof;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirm.setOnAction(e->{
            //validation
            nameNof.setText("");
            passNof.setText("");
            repassNof.setText("");
            boolean flag = true;
            if (nameField.getText().length()<3){
                flag = false;
                nameNof.setText("Invalid name");
            }
            if (new UserDao().findByUserName(nameField.getText())!=null){
                flag = false;
                nameNof.setText("Name already exists");
            }
            if (passField.getText().length()<5){
                flag=false;
                passNof.setText("Invalid password");
            }
            if (!repassField.getText().equals(passField.getText())){
                flag = false;
                repassNof.setText("Different password");
            }
            //insert
            if (flag){
                String name = nameField.getText();
                String password = passField.getText();
                String salt = BCrypt.gensalt();
                String hashPass = BCrypt.hashpw(password,salt);
                User user = new User(0,name,hashPass);
                new UserDao().insertUser(user);
                Stage stage = (Stage) btnConfirm.getScene().getWindow();
                stage.close();
            }

        });
        btnCancle.setOnAction(e->{
            Stage stage = (Stage) btnCancle.getScene().getWindow();
            stage.close();
        });
    }
}
