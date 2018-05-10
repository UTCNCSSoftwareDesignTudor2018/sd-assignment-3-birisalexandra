package BlogApp.BlogApp.communication;

import BlogApp.BlogApp.presentation.controller.WriterLoginController;
import BlogApp.BlogApp.presentation.controller.WriterViewController;
import BlogApp.BlogApp.presentation.model.Article;
import BlogApp.BlogApp.presentation.model.Writers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Client extends Application
{
    public static Connection connection;

    public static class Connection extends Thread
    {
        private final Socket socket;
        private final ObjectOutputStream output;
        private final ObjectInputStream input;

        public Connection(Socket socket) throws IOException
        {
            this.socket = socket;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run()
        {
            try
            {
                while (socket.isConnected())
                {
                    // Seems that input.available() is not reliable?
                    boolean serverHasData = socket.getInputStream().available() > 0;
                    if (serverHasData) {
                        String received = (String) input.readObject();
                        Gson gson = new Gson();
                        Mapper mapper = gson.fromJson(received, Mapper.class);
                        String message = gson.fromJson(mapper.getMessage(), String.class);
                        if(message.equals("AllArticles")) {
                            Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
                            List<Article> list = gson.fromJson(mapper.getObject(), listType);

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WriterView.fxml"));
                            AnchorPane anchorPane = loader.load();
                            WriterViewController controller = loader.getController();
                            controller.fillComboBox(list);
                        }
                        if(message.equals("CurrentWriter")) {
                            Writers writer = gson.fromJson(received, Writers.class);
                            WriterViewController.writer = writer;
                        }
                    }

                    try
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.println(Instant.now() + " Server disconnected");
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        public void sendArticleToServer(String message, Article article) throws IOException {
            Gson gson = new Gson();
            String toSend1 = gson.toJson(message);
            String toSend2 = gson.toJson(article);

            Mapper mapper = new Mapper(toSend1, toSend2);
            String toSend = gson.toJson(mapper);
            output.writeObject(toSend);
        }

        public void sendRequestToServer(String message, String content) throws IOException {
            Gson gson = new Gson();
            String toSend1 = gson.toJson(message);
            String toSend2 = gson.toJson(content);

            Mapper mapper = new Mapper(toSend1, toSend2);
            String toSend = gson.toJson(mapper);
            output.writeObject(toSend);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainPage.fxml"));
        primaryStage.setTitle("Main page");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException
    {
        SpringApplication.run(BlogAppApplication.class, args);
        connection = new Connection(new Socket("localhost", 3000));
        connection.start();

        launch(args);
    }
}