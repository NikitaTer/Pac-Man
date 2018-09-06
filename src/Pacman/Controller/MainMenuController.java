package Pacman.Controller;

import Pacman.ControllersManager;
import Pacman.Data;
import Pacman.SignInWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private ControllersManager CManager;
    private Stage prStage;

    @FXML private Label WelcomeLabel;
    @FXML private Label NicknameLabel;
    @FXML private TableView RecordsTable;
    @FXML private TableColumn nicknamesTableCol;
    @FXML private TableColumn recordsTableCol;
    @FXML private Button RecordsButton;
    @FXML private VBox ButtonsVBox;
    @FXML private Button CloseButton;
    @FXML private AnchorPane rootPane;
    @FXML private Button SignButton;
    @FXML private Button StartButton;
    @FXML private Button replayButton;

    private ObservableList<Data> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        WelcomeLabel.setVisible(false);
        NicknameLabel.setVisible(false);
        RecordsTable.setVisible(false);
        nicknamesTableCol.setResizable(false);
        recordsTableCol.setResizable(false);

        nicknamesTableCol.setCellValueFactory(new PropertyValueFactory<Data, String>("nickname"));
        recordsTableCol.setCellValueFactory(new PropertyValueFactory<Data, String>("score"));

        data = FXCollections.observableArrayList();
        RecordsTable.setItems(data);

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
                prStage.close();
                CManager.stopApp();
            }
        });

        SignButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SignButton.getText() == "Выйти") {
                    WelcomeLabel.setVisible(false);
                    NicknameLabel.setVisible(false);
                    SignButton.setText("Войти");
                    CManager.offNickname();
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

        replayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CManager.StartReplay();
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

    public ObservableList<Data> getData() {
        return data;
    }
}
