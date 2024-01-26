import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameOverController {

	@FXML
	void onGameOverAction(ActionEvent event) {
		try {
			StageDB.getGameOverStage().hide();
			StageDB.getMainSound().stop();
			StageDB.getMainStage().show();
			StageDB.getMainSound().play();
			MapGameController mapGameController =new MapGameController();
			mapGameController.initialize(null, null);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
