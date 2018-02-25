package Pacman.Controller;

import Pacman.MainApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML private TableView RecordsTable;
    @FXML private Button RecordsButton;
    @FXML private VBox ButtonsVBox;
    @FXML private Button CloseButton;
    @FXML private AnchorPane rootPane;
    @FXML private Button SignButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        RecordsTable.setVisible(false);
        rootPane.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ESCAPE && RecordsTable.isVisible()) {
                    RecordsTable.setVisible(false);
                    ButtonsVBox.setVisible(true);
                }
            }
        });

        RecordsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ButtonsVBox.setVisible(false);
                RecordsTable.setVisible(true);
            }
        });

        CloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainApp.close();
            }
        });

        SignButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SignButton.getText() == "Выйти") {

                }
                else {
                    SignInWindow();
                }
            }
        });
    }

    private void SignInWindow() {
        Stage scStage = new Stage();
        scStage.setTitle("Вход");

        AnchorPane rootP = new AnchorPane();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View/SignInWindow.fxml"));
            rootP = (AnchorPane) loader.load();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        Scene scene = new Scene(rootP);
        scStage.setScene(scene);

        scStage.show();
    }
}
