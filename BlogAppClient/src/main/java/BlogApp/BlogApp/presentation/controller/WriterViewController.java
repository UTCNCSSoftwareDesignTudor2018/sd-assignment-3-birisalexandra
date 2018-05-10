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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WriterViewController implements Initializable {

    @FXML
    private Button insert;

    @FXML
    private TextField title;

    @FXML
    private TextArea abstractPart;

    @FXML
    private TextArea body;

    @FXML
    private Button delete;

    @FXML
    private Button update;

    @FXML
    private Button back;

    @FXML
    private ComboBox<String> comboBoxDelete;

    @FXML
    private ComboBox<String> comboBoxUpdate;

    public static Writers writer;
    public List<Article> articles;
    private Client client;

    public void getWriter(String username) throws IOException {
        Client.connection.sendRequestToServer("GetWriter", username);
    }

    public void handleButtonInsert(javafx.event.ActionEvent event) throws IOException {
        Article article = new Article(0, title.getText(), abstractPart.getText(), body.getText(), writer);
        Client.connection.sendArticleToServer("NewArticle", article);
    }

    public void handleButtonBack(javafx.event.ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/MainPage.fxml"));
        Scene scene = new Scene(root2, 600, 400);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleButtonDelete() throws IOException {
        String title = comboBoxDelete.getSelectionModel().getSelectedItem().toString();
        Client.connection.sendRequestToServer("DeleteArticle", title);
    }

    public void handleButtonUpdate() {
        String title = comboBoxUpdate.getSelectionModel().getSelectedItem().toString();
    }

    public void fillComboBox(List<Article> list) {
        this.articles = list;
        List<String> titles = new ArrayList<>();
        for(Article a: articles)
            titles.add(a.getTitle());
        comboBoxDelete.getItems().addAll(titles);
        comboBoxUpdate.getItems().addAll(titles);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        articles = new ArrayList<>();
        try {
            Client.connection.sendRequestToServer("GetAllArticles", "");
            fillComboBox(articles);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
