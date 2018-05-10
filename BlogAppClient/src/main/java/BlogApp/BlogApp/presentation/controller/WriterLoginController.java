package BlogApp.BlogApp.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class WriterLoginController {

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public void handleButtonLogin(javafx.event.ActionEvent event) throws IOException, ClassNotFoundException {

        FXMLLoader loginFrame = new FXMLLoader(getClass().getResource("/WriterView.fxml"));
        Parent p = (Parent) loginFrame.load();
        WriterViewController controller = loginFrame.getController();
        controller.getWriter(username.getText());

        Scene scene = new Scene(p, 600, 400);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
