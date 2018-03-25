package Pacman.Controller;

import Pacman.Game.*;
import Pacman.ControllersManager;
import Pacman.Game.GameModel;
import Pacman.Game.GameView;
import Pacman.SignInWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private ControllersManager CManager;
    private Stage prStage;
    @FXML private Label WelcomeLabel;
    @FXML private Label NicknameLabel;
    @FXML private TableView RecordsTable;
    @FXML private Button RecordsButton;
    @FXML private VBox ButtonsVBox;
    @FXML private Button CloseButton;
    @FXML private AnchorPane rootPane;
    @FXML private Button SignButton;
    @FXML private Button StartButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        WelcomeLabel.setVisible(false);
        NicknameLabel.setVisible(false);
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
            public void handle(ActionEvent event) { prStage.close(); }
        });

        SignButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SignButton.getText() == "Выйти") {
                    WelcomeLabel.setVisible(false);
                    NicknameLabel.setVisible(false);
                    SignButton.setText("Войти");
                }
                else {
                    try {
                        new SignInWindow(CManager);
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        StartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CManager.StartGame();
            }
        });
    }

    public void setStage(Stage stage) {
        prStage = stage;
    }

    public void setCManager(ControllersManager CManager) {
        this.CManager = CManager;
    }

    public void setNickname(String Nickname) {
        SignButton.setText("Выйти");
        WelcomeLabel.setVisible(true);
        NicknameLabel.setText(Nickname);
        NicknameLabel.setVisible(true);
    }
}
