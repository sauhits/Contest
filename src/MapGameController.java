import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    static int limitSecond;
    @FXML
    private Label labelTime;
    static final int SECOND = 30;
    private Timer timer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapData = new MapData(21, 15);
        chara = new MoveChara(1, 1, mapData);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        drawMap(chara, mapData);
        timeAction();
    }

    // Draw the map
    public void drawMap(MoveChara c, MapData m) {
        int cx = c.getPosX();
        int cy = c.getPosY();
        mapGrid.getChildren().clear();
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                if (x == cx && y == cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    // Get users' key actions
    public void keyAction(KeyEvent event) {
        KeyCode key = event.getCode();
        System.out.println("keycode:" + key);
        if (ItemDB.moveFISHGain == 1) {
            // 通常
            if (key == KeyCode.A) {
                leftButtonAction();
            } else if (key == KeyCode.S) {
                downButtonAction();
            } else if (key == KeyCode.W) {
                upButtonAction();
            } else if (key == KeyCode.D) {
                rightButtonAction();
            }
        } else {
            // sakeOn
            if (key == KeyCode.A) {
                rightButtonAction();
            } else if (key == KeyCode.S) {
                upButtonAction();
            } else if (key == KeyCode.W) {
                downButtonAction();
            } else if (key == KeyCode.D) {
                leftButtonAction();
            }
        }
    }

    // Operations for going the cat up
    public void upButtonAction() {
        printAction("UP");
        chara.setCharaDirection(MoveChara.TYPE_UP);
        chara.move(0, -1);
        drawMap(chara, mapData);
    }

    // Operations for going the cat down
    public void downButtonAction() {
        printAction("DOWN");
        chara.setCharaDirection(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void leftButtonAction() {
        printAction("LEFT");
        chara.setCharaDirection(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void rightButtonAction() {
        printAction("RIGHT");
        chara.setCharaDirection(MoveChara.TYPE_RIGHT);
        chara.move(1, 0);
        drawMap(chara, mapData);
    }

    @FXML
    public void func1ButtonAction(ActionEvent event) {
        try {
            System.out.println("func1");
            StageDB.getMainStage().hide();
            // StageDB.getMainSound().stop();
            StageDB.getGameOverStage().show();
            // StageDB.getGameOverSound().play();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void func2ButtonAction(ActionEvent event) {
        System.out.println("Remap");
        initialize(null, null);
    }

    @FXML
    public void func3ButtonAction(ActionEvent event) {
        System.out.println("func3: Nothing to do");
    }

    @FXML
    public void func4ButtonAction(ActionEvent event) {
        System.out.println("func4: Nothing to do");
    }

    // Print actions of user inputs
    public void printAction(String actionString) {
        System.out.println("Action: " + actionString);
    }

    @FXML
    public void timeAction() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        if (limitSecond <= SECOND) {
                            int limit = SECOND - limitSecond;
                            labelTime.setText("あと " + limit + " 秒");
                            limitSecond++;
                        } else {
                            // 30秒経過
                            timer.cancel();
                            // タイマーが発火したときに実行される処理
                            StageDB.getMainStage().hide();
                            // StageDB.getMainSound().stop();
                            StageDB.getGameOverStage().show();
                            // StageDB.getGameOverSound().play();
                            System.out.println("タイマーが動作しました");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
        };
        timer.schedule(task, 0, 1000);
    }
}
