package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class creatingController {
    @FXML
    private TextField nameCategoryText;

    @FXML
    private Button nameCreatingButton;

    @FXML
    void nameCreating(ActionEvent event) {
        if (!nameCategoryText.getText().equals("")){
            ManagingController.categoriesToColumns.add(nameCategoryText.getText());
        }
        Stage stage = (Stage) nameCreatingButton.getScene().getWindow();
        stage.close();
    }


}
