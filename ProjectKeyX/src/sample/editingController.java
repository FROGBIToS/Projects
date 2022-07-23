package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editingController {
    private String notEdited;
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
    private Button editingPasswordButton;
    @FXML
    void editingPassword(ActionEvent event) {
        if (!categoriesBox.getValue().equals("") && !nazwaText.getText().equals("") && !loginText.getText().equals("") && !passwordText.getText().equals("") && !wwwText.getText().equals("")){
            ManagingController.passwordsToColumns.add(new Password(categoriesBox.getValue(), nazwaText.getText(), loginText.getText(), passwordText.getText(), wwwText.getText()));
            for (int i = 0; i < Controller.osnowa.getPasswordsList().size(); i++) {
                if (Controller.osnowa.getPasswordsList().get(i).equals(notEdited)){
                    Controller.osnowa.getPasswordsList().remove(i);
                }
            }
            Controller.osnowa.getPasswordsList().add(ManagingController.passwordsToColumns.get(ManagingController.passwordsToColumns.size()-1).toString());
            Stage stage = (Stage) editingPasswordButton.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    void initialize(){
        for (int i = 1; i < ManagingController.categoriesToColumns.size(); i++) {
            categoriesBox.getItems().add(ManagingController.categoriesToColumns.get(i));
        }
        categoriesBox.setPromptText(ManagingController.toEditing.getCategory());
        categoriesBox.setValue(ManagingController.toEditing.getCategory());
        nazwaText.setText(ManagingController.toEditing.getName());
        loginText.setText(ManagingController.toEditing.getLogin());
        passwordText.setText(ManagingController.toEditing.getPassword());
        wwwText.setText(ManagingController.toEditing.getWww());
        notEdited = ManagingController.toEditing.toString();
    }
}
