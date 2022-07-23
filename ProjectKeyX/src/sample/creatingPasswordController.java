package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class creatingPasswordController {
    @FXML
    private ComboBox<String> categoriesBox;

    @FXML
    private TextField nazwaText;

    @FXML
    private TextField loginText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField wwwText;

    @FXML
    private Button createPasswordButton;

    @FXML
    public void createPassword(ActionEvent event) throws Exception{
        if (!categoriesBox.getValue().equals("") && !nazwaText.getText().equals("") && !loginText.getText().equals("") && !passwordText.getText().equals("") && !wwwText.getText().equals("")){
        ManagingController.passwordsToColumns.add(new Password(categoriesBox.getValue(), nazwaText.getText(), loginText.getText(), passwordText.getText(), wwwText.getText()));
        Controller.osnowa.getPasswordsList().add(ManagingController.passwordsToColumns.get(ManagingController.passwordsToColumns.size()-1).toString());
        Stage stage = (Stage) createPasswordButton.getScene().getWindow();
        stage.close();
        }
    }
    @FXML
    void initialize(){
        for (int i = 1; i < ManagingController.categoriesToColumns.size(); i++) {
            categoriesBox.getItems().add(ManagingController.categoriesToColumns.get(i));
        }

    }
}
