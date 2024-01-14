import java.util.Timer;
import java.util.TimerTask;

public class ItemDB {
    // MapのSPACEを入れるリストの作成
    // static List<Integer>[] listMapSpaceCoordinate=new ArrayList<>();
    public static final int BANANA = 10;
    public static final int FISH = 11;
    public static final int SAKE = 12;
    public static final int BANANA_STOP = 100;
    private int SpaceCount;
    private int moveFISHGain;
    private int moveSakeGain;
    private int[][] listMapSpaceCoordinate = new int[150][2];

    public ItemDB() {
        this.SpaceCount = 0;
        this.moveFISHGain = 1;
        this.moveSakeGain = 1;
        updateSpaceList();
    }

    //
    public void updateSpaceList() {
        for (int i = 0; i < MapData.maps.length; i++) {
            for (int j = 0; j < MapDB.toShowMaze.length; j++) {
                if (MapDB.coordinate[i][j] == MapDB.Map_TYPE_SPACE) {
                    listMapSpaceCoordinate[SpaceCount][0] = i;
                    listMapSpaceCoordinate[SpaceCount][1] = j;
                    SpaceCount++;
                }
            }
        }
        SpaceCount--;
    }

    // randomに設置可能な座標を返す
    public int[] getItemCoordinate() {
        int[] coordinate = new int[2];
        int randomNum = (int) (Math.random() * SpaceCount);
        coordinate[0] = listMapSpaceCoordinate[randomNum][0];
        coordinate[1] = listMapSpaceCoordinate[randomNum][1];
        return coordinate;
    }

    public void setItem(int Item) {
        int[] setCoordinate = getItemCoordinate();
        switch (Item) {
            case 10:
                // BANANA
                MapDB.coordinate[setCoordinate[0]][setCoordinate[1]] = BANANA;
                break;
            case 11:
                // FISH
                MapDB.coordinate[setCoordinate[0]][setCoordinate[1]] = FISH;
                break;
            case 12:
                // SAKE
                MapDB.coordinate[setCoordinate[0]][setCoordinate[1]] = SAKE;
                break;
            default:
                System.out.println("Error: setItem");
                break;
        }
    }

    static void actionBANANA() {
        try {
            Thread.sleep(BANANA_STOP);
        } catch (InterruptedException e) {
            // 例外処理
            System.out.println("Error: actionBANANA");
        }
    }

    public void actionFISH() {
        moveFISHGain = 2;
    }

    public void actionSAKE() {
        moveSakeGain = -1;
        TimerTask task = new TimerTask() {
            public void run() {
                moveSakeGain = 1;
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 100);
    }

    public void startAction(int Item) {
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
                actionBANANA();
                break;
            case 11:
                // FISH
                actionFISH();
                break;
            case 12:
                // SAKE
                actionSAKE();
                break;

            default:
                System.out.println("Error: startAction");
                break;
        }
    }
}
