import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

// BANANA->bell
// FISH->apple
// SAKE->pear

public class ItemDB {
    public static int itemCount = 0;
    public static final int BANANA = 10;
    public static final int FISH = 11;
    public static final int SAKE = 12;
    public static final int BANANA_STOP = 1000;
    private static int spaceCount;
    public static int moveBANANAGain;
    public static int moveFISHGain;
    public static int moveSAKEGain;
    private static int[][] listMapSpaceCoordinate = new int[150][2];

    ItemDB() {
        ItemDB.spaceCount = 0;
        ItemDB.moveFISHGain = 1;
        ItemDB.moveSAKEGain = 1;
        ItemDB.moveBANANAGain = 1;
        updateSpaceList();
    }

    //
    public static void updateSpaceList() {
        spaceCount = 0;
        for (int y = 0; y < MapData.getHeight(); y++) {
            for (int x = 0; x < MapData.getWidth(); x++) {
                if (MapData.getMap(x, y) == MapData.TYPE_SPACE) {
                    listMapSpaceCoordinate[spaceCount][0] = x;
                    listMapSpaceCoordinate[spaceCount][1] = y;
                    spaceCount++;
                }
            }
        }
        spaceCount--;
    }

    // randomに設置可能な座標を返す
    public static int[] getItemCoordinate() {
        updateSpaceList();
        int[] coordinate = new int[2];
        int randomNum = (int) (Math.random() * spaceCount);
        coordinate[0] = listMapSpaceCoordinate[randomNum][0];
        coordinate[1] = listMapSpaceCoordinate[randomNum][1];
        return coordinate;
    }

    public static void setItem(int Item) {
        int[] setCoordinate = getItemCoordinate();
        System.out.println(setCoordinate[0] + "," + setCoordinate[1]);
        MapData.setMap(setCoordinate[0], setCoordinate[1], Item);
    }

    // BANANA_STOP秒間停止する
    static void actionBANANA() {

        moveBANANAGain = 0;
        Timer timerBANANA = new Timer();
        TimerTask taskBANANA = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        moveBANANAGain = 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        timerBANANA.schedule(taskBANANA, BANANA_STOP);
    }

    // 移動量が2倍になる
    public static void actionFISH() {
        moveFISHGain = 2;
    }

    // 入力が逆になる
    public static void actionSAKE() {
        moveSAKEGain = -1;
    }

    public static void startAction(int x,int y) {
        int Item;
        Item=MapData.getMap(x, y);
        System.out.println("Item: " + Item);
        switch (Item) {
            case 0:
                // SPACE
                break;
            case 1:
                // Wall
                break;
            case 10:
                // BANANA
                addItemCount(1);
                System.out.println("itemCount:" + itemCount);
                actionBANANA();
                break;
            case 11:
                // FISH
                addItemCount(1);
                System.out.println("itemCount:" + itemCount);
                actionFISH();
                break;
            case 12:
                // SAKE
                addItemCount(1);
                System.out.println("itemCount:" + itemCount);
                actionSAKE();
                break;

            default:
                System.out.println("Error: startAction");
                break;
        }
    }

    static void addItemCount(int gain) {
        itemCount += gain;
    }

    static int getItemCount() {
        return itemCount;
    }

    static boolean IsGetAllItems() {

        int mustGetNoOfItem = 3;

        if (getItemCount() >= mustGetNoOfItem) {
            return true;
        } else {
            return false;
        }
    }
}
