import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;

public class MoveChara {
    public static final int TYPE_DOWN = 0;
    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_UP = 3;
    private final String[] directions = { "Down", "Left", "Right", "Up" };
    private final String[] animationNumbers = { "1", "2", "3" };
    private final String pngPathPre = "png/packman";
    private final String pngPathSuf = ".png";
    private int posX;
    private int posY;
    private Image[][] charaImages;
    private ImageView[] charaImageViews;
    private ImageAnimation[] charaImageAnimations;
    private int charaDirection;

    ////////////////////////////////////////////////////////////////

    MoveChara(int startX, int startY, MapData mapData) {
        charaImages = new Image[4][2];
        charaImageViews = new ImageView[4];
        charaImageAnimations = new ImageAnimation[4];

        for (int i = 0; i < 4; i++) {
            charaImages[i] = new Image[2];
            for (int j = 0; j < 2; j++) {
                charaImages[i][j] = new Image(
                        pngPathPre + directions[i] + animationNumbers[j] + pngPathSuf);
            }
            charaImageViews[i] = new ImageView(charaImages[i][0]);
            charaImageAnimations[i] = new ImageAnimation(
                    charaImageViews[i], charaImages[i]);
        }

        posX = startX;
        posY = startY;

        setCharaDirection(TYPE_RIGHT); // start with right-direction
    }

    // set the cat's direction
    public void setCharaDirection(int cd) {
        charaDirection = cd;
        for (int i = 0; i < 4; i++) {
            if (i == charaDirection) {
                charaImageAnimations[i].start();
            } else {
                charaImageAnimations[i].stop();
            }
        }
    }

    // check whether the cat can move on
    private boolean isMovable(int dx, int dy) {
        ItemDB.startAction(posX + dx, posY + dy);
        if (MapData.getMap(posX + dx, posY + dy) == MapData.TYPE_WALL) {
            return false;
        } else if (ItemDB.moveBANANAGain != 1) {
            return false;
        } else if (MapData.getMap(posX + dx, posY + dy) != MapData.TYPE_WALL) {
            return true;
        }
        return false;
    }

    // move the cat
    public boolean move(int dx, int dy) {
        if (isMovable(dx, dy)) {
            posX += dx;
            posY += dy;
            System.out.println("chara[X,Y]:" + posX + "," + posY);
            // アイテム条件を後に追加する
            if (MapData.getMap(posX, posY) == MapData.TYPE_GOAL) {
                if (ItemDB.IsGetAllItems()) {
                    System.out.println("GOAL");
                    StageDB.getMainStage().hide();
                    StageDB.getMainSound().stop();
                    StageDB.getGameGoalStage().show();
                } else {
                    System.out.println("You must get all items!");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // getter: direction of the cat
    public ImageView getCharaImageView() {
        return charaImageViews[charaDirection];
    }

    // getter: x-position of the cat
    public int getPosX() {
        return posX;
    }

    // getter: y-position of the cat
    public int getPosY() {
        return posY;
    }

    // Show the cat animation
    private class ImageAnimation extends AnimationTimer {

        private ImageView charaView = null;
        private Image[] charaImages = null;
        private int index = 0;

        private long duration = 500 * 1000000L; // 500[ms]
        private long startTime = 0;

        private long count = 0L;
        private long preCount;
        private boolean isPlus = true;

        public ImageAnimation(ImageView charaView, Image[] images) {
            this.charaView = charaView;
            this.charaImages = images;
            this.index = 0;
        }

        @Override
        public void handle(long now) {
            if (startTime == 0) {
                startTime = now;
            }

            preCount = count;
            count = (now - startTime) / duration;
            if (preCount != count) {
                if (isPlus) {
                    index++;
                } else {
                    index--;
                }
                if (index < 0 || 1 < index) {
                    index = 1;
                    isPlus = !isPlus; // true == !false, false == !true
                }
                charaView.setImage(charaImages[index]);
            }
        }
    }
}
