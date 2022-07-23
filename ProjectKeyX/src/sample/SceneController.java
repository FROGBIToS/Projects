package sample;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void toLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("KeyX");
        stage.setResizable(false);
        stage.show();
    }

    public void toManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ManagingPassword.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("KeyX");
        stage.setResizable(true);
        stage.show();
    }

    public void WarningCreating() throws IOException{
        Parent warnRoot = FXMLLoader.load(getClass().getResource("CreatingWarning.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("WARNING");
        warnStage.setScene(new Scene(warnRoot));
        warnStage.setResizable(false);
        warnStage.show();
    }

    public void WarningChoosing() throws IOException{
        Parent warnRoot = FXMLLoader.load(getClass().getResource("ChoosingWarning.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("WARNING");
        warnStage.setScene(new Scene(warnRoot));
        warnStage.setResizable(false);
        warnStage.show();
    }

    public void WarningFileExists() throws IOException{
        Parent warnRoot = FXMLLoader.load(getClass().getResource("CreatingNewFileWarning.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("WARNING");
        warnStage.setScene(new Scene(warnRoot));
        warnStage.setResizable(false);
        warnStage.show();
    }

    public void NotLogined() throws IOException{
        Parent warnRoot = FXMLLoader.load(getClass().getResource("NotLogined.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("WARNING");
        warnStage.setScene(new Scene(warnRoot));
        warnStage.setResizable(false);
        warnStage.show();
    }

    public void Logined() throws IOException{
        Parent loginedParent = FXMLLoader.load(getClass().getResource("Logined.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("WELCOM");
        warnStage.setScene(new Scene(loginedParent));
        warnStage.show();
    }

    public void creatingCategory() throws IOException{
        Parent warnRoot = FXMLLoader.load(getClass().getResource("creatingCategory.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("CREATING");
        warnStage.setScene(new Scene(warnRoot));
        warnStage.setResizable(false);
        warnStage.show();
    }

    public void creatingPassword() throws IOException{
        Parent warnRoot = FXMLLoader.load(getClass().getResource("creatingPassword.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("CREATING");
        warnStage.setScene(new Scene(warnRoot));
        warnStage.setResizable(false);
        warnStage.show();
    }

    public void editingPassword() throws IOException{
        Parent warnRoot = FXMLLoader.load(getClass().getResource("editingPassword.fxml"));
        Stage warnStage = new Stage();
        warnStage.setTitle("Editing");
        warnStage.setScene(new Scene(warnRoot));
        warnStage.setResizable(false);
        warnStage.show();
    }
}