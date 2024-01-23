import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MapDB
 */
public class MapDB {
    // MapSizeの指定 奇数 5以上
    static int[][] coordinate = new int[13][13];
    static int[][] toShowMaze = new int[MapDB.coordinate.length - 2][MapDB.coordinate.length - 2];
    // 初期位置の設定
    static int xNow = 2;
    static int yNow = 2;
    static int Map_TYPE_SPACE=0;
    static int Map_TYPE_WALL=1;
    public static List<Integer> xPossible = new ArrayList<>();
    public static List<Integer> yPossible = new ArrayList<>();

    // 方向の設定
    static int[][] tempVectors = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    static int[] directionCheck = new int[4];
    static int randomDirection;
    static ArrayList<Integer> order = new ArrayList<>(Arrays.asList(0, 1, 2, 3));

    public static void digMaze() {
        // 初期位置の入力
        xPossible.add(xNow);
        yPossible.add(yNow);

        while (!xPossible.isEmpty()) {
            // orderをシャッフル
            Collections.shuffle(order);
            int stFinal = findAvailableDirection();
            // digの確認
            if (stFinal != -1) {
                // Dig可能なdirectionがある
                // possibleに入力
                xPossible.add(xNow);
                yPossible.add(yNow);
                // １マス先をspaceにする
                coordinate[xNow + tempVectors[stFinal][0]][yNow + tempVectors[stFinal][1]] = Map_TYPE_SPACE;
                // 2マス先をspaceにする
                coordinate[xNow + (2 * tempVectors[stFinal][0])][yNow + (2 * tempVectors[stFinal][1])] = Map_TYPE_SPACE;
                xNow += (2 * tempVectors[stFinal][0]);
                yNow += (2 * tempVectors[stFinal][1]);
                // showCoordinate(coordinate);
            } else {
                // Dig不可能
                // 再探索をする
                xNow = xPossible.get(0);
                yNow = yPossible.get(0);
                xPossible.remove(0);
                yPossible.remove(0);
            }
        }
    }

    public static int findAvailableDirection() {
        for (int i : order) {
            if (checkDigTF(xNow, yNow, i)) {
                return i;
            }
        }
        return -1;
    }

    public static Boolean checkDigTF(int x, int y, int order) {
        
        int xGo = x + tempVectors[order][0];
        int yGo = y + tempVectors[order][1];
        // 範囲の精査
        // xの1マス先が範囲内である
        boolean term1 = xGo < coordinate.length && 0 < xGo;
        // yの1マス先が範囲内である
        boolean term2 = yGo < coordinate.length && 0 < yGo;
        boolean term3 = false;
        // １セル先が壁∧２セル先が壁
        if (term1 && term2) {
            term3 = coordinate[xGo][yGo] == Map_TYPE_WALL;
        }
        xGo += tempVectors[order][0];
        yGo += tempVectors[order][1];
        term1 = xGo < coordinate.length && 0 < xGo;
        term2 = yGo < coordinate.length && 0 < yGo;
        boolean term4 = false;
        if (term1 && term2) {
            term4 = coordinate[xGo][yGo] == Map_TYPE_WALL;
        }
        return term3 && term4;
    }

    public static void showCoordinate(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[i][j]);
            }
            System.err.print("\n");
        }
        System.err.print("\n");
        System.err.print("\n");
    }

    public static void DigMap(int x,int y) {
        // wall->1,space->0
        // fill
        for (int i = 0; i < coordinate.length; i++) {
            for (int j = 0; j < coordinate.length; j++) {
                coordinate[i][j] = Map_TYPE_WALL;
            }
        }
        // 外周の作成
        for (int i = 0; i < coordinate.length; i++) {
            coordinate[0][i] = Map_TYPE_SPACE;
            coordinate[coordinate.length - 1][i] = Map_TYPE_SPACE;
            coordinate[i][0] = Map_TYPE_SPACE;
            coordinate[i][coordinate.length - 1] = Map_TYPE_SPACE;
        }
        // Spaceの作成開始
        // 初期位置をspaceにする
        coordinate[xNow][yNow] = Map_TYPE_SPACE;
        digMaze();
        // 表示用の配列を作成する
        for (int i = 0; i < coordinate.length - 2; i++) {
            for (int j = 0; j < coordinate.length - 2; j++) {
                toShowMaze[i][j] = coordinate[i + 1][j + 1];
            }
        }

    }
}
