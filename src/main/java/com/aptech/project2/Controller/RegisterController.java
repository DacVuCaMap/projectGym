package com.aptech.project2.Controller;

import com.aptech.project2.DAO.UserDao;
import com.aptech.project2.Model.User;
import com.aptech.project2.Validation.Validate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

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
    @FXML
    private Pane pane;
    @FXML
    private TextField otpCode;
    @FXML
    private Button completeBtn;
    @FXML
    private Label weSend;
    @FXML
    private Label otpNof;
    private String otp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setVisible(false);
        btnConfirm.setOnAction(e->{
            //validation
            nameNof.setText("");
            passNof.setText("");
            repassNof.setText("");
            boolean flag = true;
            if (!Validate.isEmail(nameField.getText())){
                flag = false;
                nameNof.setText("Invalid email");
            }
            if (new UserDao().findByUserName(nameField.getText())!=null){
                flag = false;
                nameNof.setText("email already exists");
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
                pane.setVisible(true);
                weSend.setText("We send to email "+nameField.getText());
                this.otp = buildOTP();
                sendMail(nameField.getText(),otp);
            }

        });
        completeBtn.setOnAction(e->{

            if (otpCode.getText().equals(this.otp)){
                String name = nameField.getText();
                String password = passField.getText();
                String salt = BCrypt.gensalt();
                String hashPass = BCrypt.hashpw(password,salt);
                User user = new User(0,name,hashPass);
                new UserDao().insertUser(user);
                Stage stage = (Stage) completeBtn.getScene().getWindow();
                stage.close();
            }
            else {
                otpNof.setText("* Invalid OTP");
            }
            //insert

        });
    }
    public void backLogin(MouseEvent e){
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    public void resendEmail(MouseEvent e){
        this.otp = buildOTP();
        sendMail(nameField.getText(),otp);
    }
    public static String sendMail(String recipient,String otp){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myAccountEmail = "gymsystem000@gmail.com";
        String password = "zubajtjeqfckwcde";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }

        });
        Message message = prepareMessage(session,myAccountEmail,recipient,otp);
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return otp;
    }
    private static Message prepareMessage(Session session,String myAccountEmail,String recipient,String otp){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject("Verification code");
            message.setText(otp+"  this is your otp code to active account");
            return message;
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    public static String buildOTP(){
        Random random = new Random();
        String otp="G-";
        for (int i=0;i<5;i++){
            otp+=random.nextInt(9);
        }
        return otp;
    }
}
