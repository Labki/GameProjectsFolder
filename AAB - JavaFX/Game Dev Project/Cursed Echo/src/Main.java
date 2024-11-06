import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Image backgroundImage = new Image(getClass().getResource("/Images/menu-bg.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);

        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);
        backgroundView.setPreserveRatio(true);


        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: lightblue;");
        Label testLabel = new Label("Testing Display");
        root.getChildren().addAll(backgroundView, testLabel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cursed Echo");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
