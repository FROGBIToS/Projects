package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class Controller{
    @FXML
    private Button toLoginButton;
    @FXML
    private TextField createText;
    @FXML
    private Button createButton;
    @FXML
    private Button choosingFileButton;
    @FXML
    private Label chooseFileLabel;
    @FXML
    private Button loginningButton;
    @FXML
    public Label welcomUser;
    @FXML
    public Label lastDate;
    private static boolean forData = false;
    public static ManagingPassword osnowa;
    private static File selectedFile;
    SceneController sceneController = new SceneController();
    @FXML
    private TextField userText;
    @FXML
    private PasswordField passwordText;



    @FXML
    public void toLoginMethod(ActionEvent event) throws IOException {
        if (!createText.getText().equals("")){
            Error(event,1);
        }
        else if(chooseFileLabel.getText().equals("")){
            Error(event,2);
        }
        else if (createText.getText().equals("")){
            sceneController.toLogin(event);
            osnowa = new ManagingPassword(selectedFile);
        }
    }
    @FXML
    public void choosingFileMethod() throws IOException{
        FileChooser fc = new FileChooser();
        File recordsDir = new File("./src/Recources");
        if (! recordsDir.exists()) {
            recordsDir.mkdirs();
        }

        fc.setInitialDirectory(recordsDir);

        selectedFile = fc.showOpenDialog(null);
        chooseFileLabel.setText(selectedFile.getName());
    }
    @FXML
    public void creatingFileMethod(ActionEvent event)throws IOException{
        File newFile;
        String newFileString = createText.getText();
        boolean txtInName = false;

        if (!createText.getText().equals("")){
            if (newFileString.matches("(.*).txt")){
                newFileString = "./src/Recources\\" + newFileString;
                txtInName = true;
            }
            else{
                newFileString = "./src/Recources\\" + newFileString + ".txt";
            }

            newFile = new File(newFileString);

            if (!newFile.createNewFile()){
                Error(event,3);
            }else{
                if (txtInName){
                    chooseFileLabel.setText(createText.getText());
                }else{
                    chooseFileLabel.setText(createText.getText()+ ".txt");
                }
                createText.setText("");
                selectedFile = new File(newFileString);

            }
        }else{
            Error(event,1);
        }
    }
    public void Error(ActionEvent event, int indexErr) throws IOException{
        switch (indexErr){
            case 1:
                SceneController warnCreating = new SceneController();
                warnCreating.WarningCreating();
                break;
            case 2:
                SceneController warnChosing = new SceneController();
                warnChosing.WarningChoosing();
                break;
            case 3:
                SceneController warnExists = new SceneController();
                warnExists.WarningFileExists();
                break;
            case 4:
                SceneController warnNotLogined = new SceneController();
                warnNotLogined.NotLogined();
                break;


        }
    }

    @FXML
    public void loginning(ActionEvent event) throws Exception {
        if (osnowa.toCheckicng(userText.getText(),passwordText.getText())){
            forData = true;
            SceneController sc = new SceneController();
            sc.toManager(event);
            sc.Logined();
        }else{
            Error(event,4);
        }
    }



    @FXML
    public void initialize(){
        if (forData){
            if (welcomUser != null && lastDate != null){
                Platform.runLater(() -> welcomUser.setText("Welcom : " + ManagingPassword.username));
                Platform.runLater(()->lastDate.setText("You were last time : "+ ManagingPassword.data));
            }
        }
    }
}
