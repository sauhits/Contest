import java.io.IOException;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameGoalController {

	@FXML
	void onGameGoalAction(ActionEvent event) {
		try {
			StageDB.getGameGoalStage().hide();
			// StageDB.getMainSound().stop();
			// 後にマップ再生成の要素を追加
			StageDB.getMainStage().show();
			// StageDB.getMainSound().play();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}