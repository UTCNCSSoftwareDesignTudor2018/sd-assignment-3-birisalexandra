package BlogApp.BlogApp;
import BlogApp.BlogApp.communication.Mapper;
import BlogApp.BlogApp.controller.ArticleController;
import BlogApp.BlogApp.controller.WriterController;
import BlogApp.BlogApp.data.entity.Article;
import BlogApp.BlogApp.data.entity.Writers;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.List;

@SpringBootApplication
public class Server {

    static class Connection extends Thread {

        private final Socket clientSocket;

        public Connection(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run()
        {
            try(ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream()))
            {
                while (clientSocket.isConnected())
                {

                    boolean clientHasData = clientSocket.getInputStream().available() > 0;
                    if (clientHasData) {
                        String received = (String) input.readObject();
                        Gson gson = new Gson();
                        Mapper mapper = gson.fromJson(received, Mapper.class);
                        String message = gson.fromJson(mapper.getMessage(), String.class);

                        if (message.equals("NewArticle")) {
                            Article article = gson.fromJson(mapper.getObject(), Article.class);
                            ArticleController articleController = ApplicationContextHolder.getContext().getBean(ArticleController.class);
                            articleController.insertArticle(article);
                        }
                        if (message.equals("GetWriter")) {
                            String username = gson.fromJson(mapper.getObject(), String.class);
                            WriterController controller = ApplicationContextHolder.getContext().getBean(WriterController.class);
                            Writers writer = controller.findWriter(username);
                            String tag = "CurrentWriter";

                            String toSend1 = gson.toJson(tag);
                            String toSend2 = gson.toJson(writer);

                            Mapper mapper2 = new Mapper(toSend1, toSend2);
                            String toSend = gson.toJson(mapper2);
                            output.writeObject(toSend);
                        }
                        if (message.equals("GetAllArticles")) {
                            ArticleController articleController = ApplicationContextHolder.getContext().getBean(ArticleController.class);
                            List<Article> list = articleController.getAllArticles();
                            String tag = "AllArticles";

                            String toSend1 = gson.toJson(tag);
                            String toSend2 = gson.toJson(list);

                            Mapper mapper2 = new Mapper(toSend1, toSend2);
                            String toSend = gson.toJson(mapper2);
                            output.writeObject(toSend);
                        }
                        if (message.equals("DeleteArticle")) {
                            String title = gson.fromJson(mapper.getObject(), String.class);
                            ArticleController articleController = ApplicationContextHolder.getContext().getBean(ArticleController.class);
                            Article toDelete = articleController.findByTitle(title);
                            articleController.deleteArticle(toDelete);
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
                System.out.println(Instant.now() + " Client disconnected?");
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            // cleanup
            try
            {
                clientSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void sendResponseToClient(String type, Object object) {

        }
    }
    public static void main(String[] args) throws IOException
    {

        SpringApplication.run(BlogAppApplication.class, args);
        try (ServerSocket socket = new ServerSocket(3000))
        {
            while (true)
            {
                System.out.println(Instant.now() + " Waiting for client...");
                Socket clientSocket = socket.accept();
                new Connection(clientSocket).start();
            }
        }
    }
}