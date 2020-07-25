package projFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MainProj extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("proj.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Rede GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {


        LocalDate d2 = LocalDate.now();
        LocalTime d3 = LocalTime.now();
        DayOfWeek d =  DayOfWeek.TUESDAY;
        LocalDateTime d4 = LocalDateTime.of(2019,11,26,14,0);
        System.out.println(d2);
        System.out.println(d4);
        launch(args);
    }
}
