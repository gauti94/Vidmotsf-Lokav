package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Aðalklasinn fyrir Slönguspilið verkefni 3
 *
 *
 *****************************************************************************/
public class SnakesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakesApplication.class.getResource("snakes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 510);
        stage.setTitle("Snakes and ladders");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Ræsir forritið
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}