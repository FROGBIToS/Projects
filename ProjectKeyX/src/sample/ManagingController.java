package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.*;
import javafx.scene.input.MouseEvent;

public class ManagingController {
    public static SceneController sc = new SceneController();
    @FXML
    private TableView<Password> mainTable;
    @FXML
    private TableColumn<Password, String> categoryColumn;
    @FXML
    private TableColumn<Password, String> nazwaColumn;
    @FXML
    private TableColumn<Password, String> loginColumn;
    @FXML
    private TableColumn<Password, String> passwordColumn;
    @FXML
    private TableColumn<Password, String> wwwColumn;
    @FXML
    private ListView<String> categoriesList;
    static ObservableList<Password> passwordsToColumns = FXCollections.observableArrayList();
    static ObservableList<String> categoriesToColumns = FXCollections.observableArrayList();
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private Button createCategoryButton;
    @FXML
    private Button deletePasswordButton;
    @FXML
    private Button createPasswordButton;
    private String nowCategory;
    public static Password toEditing;

    private void updateTable(String category){
        nazwaColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        wwwColumn.setCellValueFactory(new PropertyValueFactory<>("www"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        if (!category.equals("All")){
            while (passwordsToColumns.size() != 0){
                passwordsToColumns.remove(0);
            }
            for (int i = 0; i <Controller.osnowa.getPasswordsList().size(); i++) {
                String[] tmp = Controller.osnowa.getPasswordsList().get(i).split(":");
                if (tmp[0].equals(category)){
                    passwordsToColumns.add(new Password(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]));
                }
            }
        }
        else{
            while (passwordsToColumns.size() != 0){
                passwordsToColumns.remove(0);
            }
            for (int i = 0; i <Controller.osnowa.getPasswordsList().size(); i++) {
                String[] tmp = Controller.osnowa.getPasswordsList().get(i).split(":");
                passwordsToColumns.add(new Password(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]));
            }
        }
        mainTable.setItems(passwordsToColumns);
    }
    private void updateCategory(){
        categoriesToColumns.add("All");
        String tmpPorownywanie = "All";

        for (int i = 0; i < Controller.osnowa.getPasswordsList().size(); i++) {

            String[] tmp = Controller.osnowa.getPasswordsList().get(i).split(":");

            if (!tmpPorownywanie.equals(tmp[0])) {
                categoriesToColumns.add(tmp[0]);
                tmpPorownywanie = tmp[0];
            }
        }
        categoriesList.setItems(categoriesToColumns);
    }
    @FXML
    public void createCategory(ActionEvent event)throws Exception{
        sc.creatingCategory();
    }
    @FXML
    public void creatingPassword(ActionEvent event)throws Exception{
        sc.creatingPassword();
    }
    @FXML
    public void deletingCategory(ActionEvent event)throws Exception{
        for (int j = 0; j < Controller.osnowa.getPasswordsList().size(); j++) {
            String[] tmp = Controller.osnowa.getPasswordsList().get(j).split(":");
            if (tmp[0].equals(nowCategory)){
                Controller.osnowa.getPasswordsList().remove(j);
                j--;
            }
        }
        for (int i = 0; i < categoriesToColumns.size(); i++) {
            if (categoriesToColumns.get(i).equals(nowCategory)){
                categoriesToColumns.remove(i);
            }
        }
    }
    @FXML
    public void deletingPassword(ActionEvent event)throws Exception{
        Password ps = mainTable.getSelectionModel().getSelectedItem();
        for (int i = 0; i < Controller.osnowa.getPasswordsList().size(); i++) {
            if (Controller.osnowa.getPasswordsList().get(i).equals(ps.toString())){
                Controller.osnowa.getPasswordsList().remove(i);
                break;
            }
        }
        updateTable("All");
    }
    @FXML
    public void goToEditing(MouseEvent mouseEvent)throws Exception {
        if (mouseEvent.getClickCount() == 2 && mainTable.getSelectionModel().getSelectedItem() != null) {
            toEditing = mainTable.getSelectionModel().getSelectedItem();
            sc.editingPassword();
        }
    }
    @FXML
    void initialize(){
        updateCategory();
        updateTable("All");
        categoriesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                nowCategory = newValue;
                updateTable(newValue);
            }
        });
    }
}