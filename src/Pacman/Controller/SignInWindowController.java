package Pacman.Controller;

import Pacman.Controller.MainMenuController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInWindowController implements Initializable {

    private MainMenuController ForNick;
    private Stage prStage;
    @FXML private Button CloseButton;
    @FXML private Button OkButton;
    @FXML private TextField EnterField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                prStage.close();
            }
        });

        OkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!EnterField.getText().isEmpty()) {
                    ForNick.setNickname(EnterField.getText());
                    prStage.close();
                }
                else {

                }
            }
        });
    }

    public void setStage(Stage stage) {
        prStage = stage;
    }

    public void setForNick(MainMenuController cont) {
        ForNick = cont;
    }
}
