// JavaFX DrawPad main driver class for GUI

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class Assignment7 extends Application {
    
    public static final int WINSIZE_X = 800, WINSIZE_Y = 800; // Set window size

    private final String WINTITLE = "Sketchy"; // Set window title

    @Override
    public void start(Stage stage) throws Exception
    {

        SketchPane rootPane = new SketchPane();

        rootPane.setPrefSize(WINSIZE_X, WINSIZE_Y);
        Scene scene = new Scene(rootPane, WINSIZE_X, WINSIZE_Y);

        stage.setTitle(WINTITLE);

        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args)
    {

        launch(args); // Launch the JavaFX application

    }
}