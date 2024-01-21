import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class MapGame extends Application {
  Stage stage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    
    stage = primaryStage;
    stage.hide();
    StageDB.setMainClass(getClass());
    StageDB.getMainStage().show();
    // StageDB.getMainSound().play();
    
    
  }

  public static void main(String[] args) {
    launch(args);
  }
}
