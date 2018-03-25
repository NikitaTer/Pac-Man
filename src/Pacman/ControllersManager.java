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

    public void addSignInWindowController(SignInWindowController SIWController, Stage stage) {
        this.SIWController = SIWController;
        this.SIWController.setStage(stage);
        this.SIWController.setCManager(this);
    }

    public void setNickname(String Nickname) {
        MMController.setNickname(Nickname);
    }

    public void StartGame() {
        mainApp.StartGame();
    }
}
