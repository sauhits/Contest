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
        for (int y = 0; y < MapData.getHeight(); y++) {
            for (int x = 0; x < MapData.getWidth(); x++) {
                if (MapData.getMap(x,y) == MapData.TYPE_SPACE) {
                    listMapSpaceCoordinate[SpaceCount][0] = x;
                    listMapSpaceCoordinate[SpaceCount][1] = y;
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
        MapData.setMap(setCoordinate[0],setCoordinate[1],Item);
    }

    // BANANA_STOP秒間停止する
    static void actionBANANA() {
        try {
            Thread.sleep(BANANA_STOP);
        } catch (InterruptedException e) {
            // 例外処理
            System.out.println("Error: actionBANANA");
        }
    }

    // 移動量が2倍になる
    public void actionFISH() {
        moveFISHGain = 2;
    }

    // 入力が逆になる
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
