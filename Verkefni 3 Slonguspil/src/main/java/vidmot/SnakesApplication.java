package vidmot;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Aðalklasinn fyrir Slönguspilið verkefni 3
 *
 *
 *****************************************************************************/
public class SnakesApplication extends Application {

    private String nafnLeikmanns1;
    private String nafnLeikmanns2;

    @Override
    public void start(Stage stage) throws IOException {
        TextInputDialog dialog1 = new TextInputDialog("Leikmaður 1");
        dialog1.setTitle("Nafn fyrsta leikmanns");
        dialog1.setHeaderText("Velkominn í slönguspilið!");
        dialog1.setContentText("Sláðu inn nafn fyrir fyrsta leikmann");
        Optional<String> dialogResult1 = dialog1.showAndWait();
        nafnLeikmanns1 = dialogResult1.orElse("Spilari 1");
        TextInputDialog dialog2 = new TextInputDialog("Leikmaður 2");
        dialog2.setTitle("Nafn næsta leikmanns");
        dialog2.setHeaderText("Velkominn í slönguspilið!");
        dialog2.setContentText("Sláðu inn nafn fyrir næsta leikmann");
        Optional<String> dialogResult2 = dialog2.showAndWait();
        nafnLeikmanns2 = dialogResult2.orElse("Spilari 2");
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