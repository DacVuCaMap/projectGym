package com.aptech.project2.Controller;
import com.aptech.project2.DAO.CoachDAO;
import com.aptech.project2.DAO.MemberDAO;
import com.aptech.project2.Generic.IGeneric;
import com.aptech.project2.Model.Coach;
import com.aptech.project2.Model.Member;
import com.aptech.project2.Validation.Validate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberController implements Initializable {

    @FXML
    private DatePicker EndDate;

    @FXML
    private AnchorPane ProductForm;

    @FXML
    private DatePicker StartDate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInSert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Member, String> colunmAdress;

    @FXML
    private TableColumn<Member, String> colunmGender;

    @FXML
    private TableColumn<Member, String> colunmId;

    @FXML
    private TableColumn<Member, String> colunmName;

    @FXML
    private TableColumn<Member, String> colunmSchedule;

    @FXML
    private TableColumn<Member, LocalDate> colunmStartDate;

    @FXML
    private TableColumn<Member, String> colunmStatus;

    @FXML
    private TableColumn<Member, LocalDate> colunmendDate;
    @FXML
    private TableColumn<Member,String> colunmCoach;

    @FXML
    private TableColumn<Member, String> colunmphone;

    @FXML
    private ComboBox<String> comboxGender;

    @FXML
    private ComboBox<String> comboxSchedule;

    @FXML
    private ComboBox<String> coachBox;

    @FXML
    private TableView<Member> tableMember;

    @FXML
    private TextArea txtAdress;

    @FXML
    private TextField txtId;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
    @FXML
    private Button btnPay;
    private Alert alert;

    private IGeneric<Member> memberIGeneric= new MemberDAO();
    public void showComBox(){
        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female", "Others");
        ObservableList<String> schedules = FXCollections.observableArrayList("8.00-11.00", "14.00-17.00", "18.00-21.00");
        ObservableList<String> coaches = FXCollections.observableArrayList(getCoachList());
        comboxGender.setItems(genders);
        comboxSchedule.setItems(schedules);
        coachBox.setItems(coaches);
    }




    public void showTableMember(){
            ObservableList<Member>members =memberIGeneric.getAll();
            members.forEach(System.out::println);
            colunmId.setCellValueFactory(new PropertyValueFactory<Member, String>("id"));
            colunmName.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
            colunmAdress.setCellValueFactory(new PropertyValueFactory<Member, String>("address"));
            colunmGender.setCellValueFactory(new PropertyValueFactory<Member, String>("gender"));
            colunmphone.setCellValueFactory(new PropertyValueFactory<Member, String>("phone"));
            colunmendDate.setCellValueFactory(new PropertyValueFactory<Member, LocalDate>("endDate"));
            colunmStartDate.setCellValueFactory(new PropertyValueFactory<Member, LocalDate>("startDate"));
            colunmSchedule.setCellValueFactory(new PropertyValueFactory<Member, String>("schedule"));
            colunmStatus.setCellValueFactory(new PropertyValueFactory<Member, String>("status"));
            colunmCoach.setCellValueFactory(Member->new SimpleStringProperty(Member.getValue().getCoach()!=null?Member.getValue().getCoach().getName():""));
            tableMember.setItems(members);
        tableMember.setOnMouseClicked(event->{
            Member newSelection = tableMember.getSelectionModel().getSelectedItem();
            if(newSelection!=null){
                txtId.setText(newSelection.getId());
                txtId.setDisable(true);
                txtName.setText(newSelection.getName());
                txtAdress.setText(newSelection.getAddress());
                txtPhone.setText(newSelection.getPhone());
                comboxGender.setValue(newSelection.getGender());
                comboxSchedule.setValue(newSelection.getSchedule());
                StartDate.setValue(newSelection.getStartDate());
                EndDate.setValue(newSelection.getEndDate());
                coachBox.setValue(setCoachBox(newSelection.getCoach()));
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showComBox();
        showTableMember();

    }

    public void setBtnInSert(){
        if(txtId.getText().isBlank()==true
                ||txtName.getText().isBlank()==true
                ||comboxSchedule.getValue()==null
                ||comboxGender.getValue()==null
                ||txtAdress.getText().isBlank()==true
                ||txtPhone.getText().isBlank()==true
                ||StartDate.getValue()==null
                ||EndDate.getValue()==null){
            txtMessage.setText("Please enter full textFiled.");
        }else {
            boolean f = true;
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAdress.getText();
            String phone = txtPhone.getText();
            String gender = comboxGender.getValue();
            String schedule = comboxSchedule.getValue();
            String status = "Unpaid";
            Coach coach=null;
            if(!Validate.checkMemberId(id)){
                f=false;
                txtMessage.setText("Member ID is invalid.(MID-0000)");
            }
            if(!new MemberDAO().findById(id)){
                f = false;
                txtMessage.setText("Member ID is already.");
            }
            if(!Validate.checkPhone(phone)){
                f = false;
                txtMessage.setText("Number Phone is invalid.");
            }
            if (coachBox.getValue()!=null){
                String coachId = coachBox.getValue().substring(0,7);
                coach = new CoachDAO().findById(coachId);
            }
            if(f==true){
                txtMessage.setText("");
                LocalDate startDate = StartDate.getValue();
                LocalDate endDate = EndDate.getValue();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Message");
                alert.setContentText("Are you sure.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK) {
                    Member member = new Member(id,name,address,gender,phone,schedule,startDate,endDate,status,coach);
                    memberIGeneric.insert(member);
                    //clear field
                    setClear();
                    //show table here
                    showTableMember();
                }
            }

        }
    }

    public void setClear(){
        txtId.setText("");
        txtId.setDisable(false);
        txtName.setText("");
        txtAdress.setText("");
        txtPhone.setText("");
        StartDate.setValue(null);
        EndDate.setValue(null);
        comboxGender.setValue("Choose Gender");
        comboxSchedule.setValue("Choose Schedule");
    }

    public void setBtnDelete(){
        Member newSelection = tableMember.getSelectionModel().getSelectedItem();
        // validation button delete
        boolean flag = true;

        if (newSelection==null){
            flag=false;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning message");
            alert.setContentText("Please choose 1 row on the member table to update!");
            alert.showAndWait();
        }
        else {
            if (LocalDate.now().isBefore(EndDate.getValue()) && flag){
                flag=false;
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning message");
                alert.setContentText("Service package has not expired yet!");
                alert.showAndWait();
            }if (new MemberDAO().getMemberById(txtId.getText()).getStatus().equals("Unpaid") && flag){
                flag=false;
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning message");
                alert.setContentText("Member is not pay yet!");
                alert.showAndWait();
            }
            if (flag){
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtAdress.setText(newSelection.getAddress());
                txtPhone.setText(newSelection.getPhone());
                comboxGender.setValue(newSelection.getGender());
                comboxSchedule.setValue(newSelection.getSchedule());
                StartDate.setValue(newSelection.getStartDate());
                EndDate.setValue(newSelection.getEndDate());
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Message");
                alert.setContentText("Are you sure.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get()==ButtonType.OK){
                    memberIGeneric.delete(newSelection);
                    setClear();
                    showTableMember();
                }
            }
        }

    }

    public void setBtnUpdate(){
        System.out.println(coachBox.getValue());
        Member newSelection = tableMember.getSelectionModel().getSelectedItem();
        if(newSelection==null){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning message");

            alert.setContentText("Please choose 1 row on the member table to update!");

            alert.setContentText("Please choose 1 row on the member table to update!");

            alert.showAndWait();
        }else {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAdress.getText();
            String phone = txtPhone.getText();
            String gender = comboxGender.getValue();
            String schedule = comboxSchedule.getValue();
            String status = newSelection.getStatus();
            LocalDate startDate = StartDate.getValue();
            LocalDate endDate = EndDate.getValue();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Message");
            alert.setContentText("Do you want to update this member!");

            Coach coach=null;
            if (coachBox.getValue()!=null){
                String coachId = coachBox.getValue().substring(0,7);
                coach = new CoachDAO().findById(coachId);
            }
            System.out.println(coach + "  in controller");
            Member member = new Member(id,name,address,gender,phone,schedule,startDate,endDate,status,coach);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Message");
            alert.setContentText("Do you want to update this member!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK){
                memberIGeneric.update(member);
                setClear();
                showTableMember();
            }


        }

    }
    public List<String> getCoachList(){
        List<String> list = new ArrayList<>();
        for (Coach coach : CoachDAO.getInstance().getList()){
            list.add(coach.getId()+"   name :"+coach.getName());

        }
        return list;
    }
    public String setCoachBox(Coach coach){
        if (coach==null){
            return "";
        }
        return coach.getId()+"   name :"+coach.getName();
    }

   public void setBtnPay(){
        Member newSelection = tableMember.getSelectionModel().getSelectedItem();
        if(newSelection==null){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning message");
            alert.setContentText("Please choose 1 row on the member table to pay");
            alert.showAndWait();
        }else if(newSelection.getStatus().equals("Paid")){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information message");
            alert.setContentText("This member has already paid.");
            alert.showAndWait();
        } else {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/aptech/project2/Payment/Payment.fxml"));
            try {
                AnchorPane pane = loader.load();
                PaymentController paymentController = loader.getController();
                long daysBetween = ChronoUnit.DAYS.between(newSelection.getStartDate(), newSelection.getEndDate());
                double PricePerDay = 2;
                double Discount = 0;
                String CoachID = newSelection.getCoach().getId();
                Coach coach = CoachDAO.getInstance().findById(CoachID);
                if(coach.getRank().equals("Grade A")){
                    PricePerDay+=0.5;
                }else if(coach.getRank().equals("Grade B")){
                    PricePerDay+=0.3;
                }else if(coach.getRank().equals("Grade C")){
                    PricePerDay+=0.2;
                }
                if(daysBetween>=60 && daysBetween<90){
                    Discount+=((PricePerDay*daysBetween)*5/100);
                }else if(daysBetween>=90 && daysBetween<120){
                    Discount+=((PricePerDay*daysBetween)*10/100);
                }else if(daysBetween>=120){
                    Discount+=((PricePerDay*daysBetween)*20/100);
                }
                paymentController.setData(newSelection.getId(), daysBetween, PricePerDay, Discount);
                Stage dialogStage = new Stage();
                Scene scene = new Scene(pane);
                Stage stage = (Stage) btnPay.getScene().getWindow();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(stage);
                dialogStage.setScene(scene);
                dialogStage.setTitle("Payment");
                dialogStage.showAndWait();
                showTableMember();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}