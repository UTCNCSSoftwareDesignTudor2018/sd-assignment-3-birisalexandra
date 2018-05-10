package BlogApp.BlogApp.presentation.controller;

import BlogApp.BlogApp.communication.Client;
import BlogApp.BlogApp.presentation.model.Article;
import BlogApp.BlogApp.presentation.model.Writers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReaderViewController implements Initializable {

    @FXML
    private Button back;

    @FXML
    private Accordion accordion;

    public void handleButtonBack(javafx.event.ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/MainPage.fxml"));
        Scene scene = new Scene(root2, 600, 400);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.accordion = new Accordion();
        Writers w =  new Writers(4, "ale", "1234");
        Article a1 = new Article(1, "fff", "ggg", "Ggg", w);
        Article a2 = new Article(2, "sss", "adsd", "Dddd", w);
        List<Article> list = new ArrayList<>();
        list.add(a1); list.add(a2);

        for(int i = 0; i < list.size(); i++) {
            TitledPane pane = new TitledPane();
            pane.setText(list.get(i).getTitle());

            VBox content = new VBox();
            content.getChildren().add(new Label(list.get(i).getAbstractPart()));
            content.getChildren().add(new Label(list.get(i).getBody()));
            content.getChildren().add(new Label(list.get(i).getWriter().getName()));

            pane.setContent(content);
            accordion.getPanes().add(pane);
        }
    }

}
