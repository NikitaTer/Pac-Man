package Pacman;

import Pacman.Controller.MainMenuController;
import Pacman.Controller.SignInWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInWindow {

    private ControllersManager CManager;

    public SignInWindow(ControllersManager CManager) throws Exception {
        Stage prStage = new Stage();
        prStage.setTitle("Вход");

        this.CManager = CManager;

        AnchorPane rootP = new AnchorPane();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View/SignInWindow.fxml"));
            rootP = (AnchorPane) loader.load();
            CManager.addSignInWindowController(loader.getController(), prStage);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        Scene scene = new Scene(rootP);
        prStage.setScene(scene);

        prStage.show();
    }
}
