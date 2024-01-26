import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameGoalController {

	@FXML
	void onGameGoalAction(ActionEvent event) {
		try {
			StageDB.getGameGoalStage().hide();
			StageDB.getMainSound().stop();
			// 後にマップ再生成の要素を追加
			MapGameController mapGameController = new MapGameController();
			mapGameController.initialize(null, null);
			StageDB.getMainStage().show();
			StageDB.getMainSound().play();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
