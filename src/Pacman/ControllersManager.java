package Pacman;

import Pacman.Controller.MainMenuController;
import Pacman.Controller.SignInWindowController;
import javafx.stage.Stage;

public class ControllersManager {

    private MainMenuController MMController;
    private SignInWindowController SIWController;
    private MainApp mainApp;

    ControllersManager(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void addMainMenuController(MainMenuController MMController, Stage stage) {
        this.MMController = MMController;
        this.MMController.setStage(stage);
        this.MMController.setCManager(this);
    }

    public MainMenuController getMMController() {
        return MMController;
    }

    public void addSignInWindowController(SignInWindowController SIWController, Stage stage) {
        this.SIWController = SIWController;
        this.SIWController.setStage(stage);
        this.SIWController.setCManager(this);
    }

    public SignInWindowController getSIWController() {
        return SIWController;
    }

    public void setNickname(String Nickname) {
        MMController.setNickname(Nickname);
        mainApp.setNickname(true, Nickname);
    }

    public void offNickname() {
        mainApp.setNickname(false, "");
    }

    public void StartGame() {
        mainApp.startGame();
    }

    public void StartReplay() {
        mainApp.startReplay();
    }

    public void stopApp() {
        try {
            //mainApp.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
