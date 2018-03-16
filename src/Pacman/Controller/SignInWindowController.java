package Pacman.Controller;

import Pacman.ControllersManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInWindowController implements Initializable {

    private ControllersManager CManager;
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
                    CManager.setNickname(EnterField.getText());
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

    public void setCManager(ControllersManager CManager) {
        this.CManager = CManager;
    }
}
