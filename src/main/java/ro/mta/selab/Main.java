package ro.mta.selab;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ro.mta.selab.model.Model;

import java.io.IOException;

public class Main extends Application {



    public static void main(String[] args) throws IOException {
       launch(args);

    }


    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(this.getClass().getResource("/view/View.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/photo/sun.png")));
            primaryStage.setTitle("EmiMeteo");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


