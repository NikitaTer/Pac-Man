package Pacman.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInWindow implements Initializable {

    @FXML private Button CloseButton;
    @FXML private Button OkButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        OkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
