import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_SPACE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_GOAL = 16;
    private static final String mapImageFiles[] = {
            "png/SPACE.png", // 0
            "png/WALL-1.png", // 1
            "png/WALL-2.png", // 2
            "png/WALL-3.png", // 3
            "png/WALL-4.png", // 4
            "png/WALL-5.png", // 5
            "png/WALL-6.png", // 6
            "png/WALL-7.png", // 7
            "png/WALL-8.png", // 8
            "png/WALL-9.png", // 9
            "png/WALL-10.png", // 10
            "png/WALL-11.png", // 11
            "png/WALL.png", // 12
            "png/bell.png", // 13 BANANA
            "png/apple.png", // 14 FISH
            "png/pear.png", // 15 SAKE
            "png/GOAL.png"// 16 GOAL
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private static int[][] maps;
    private static int width; // width of the map
    private static int height; // height of the map

    MapData(int x, int y) {
        mapImages = new Image[mapImageFiles.length];
        mapImageViews = new ImageView[y][x];
        for (int i = 0; i < mapImageFiles.length; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }
        width = x;
        height = y;
        maps = new int[y][x];
        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        ItemDB.setItem(ItemDB.BANANA);
        ItemDB.setItem(ItemDB.FISH);
        ItemDB.setItem(ItemDB.SAKE);
        int[] goal = getGoalCoordinate();
        setMap(goal[0], goal[1], TYPE_GOAL);
        setImageViews();
    }

    // fill two-dimensional arrays with a given number (maps[y][x])
    private void fillMap(int type) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maps[y][x] = type;
            }
        }
    }

    // dig walls for making roads
    private void digMap(int x, int y) {
        setMap(x, y, MapData.TYPE_SPACE);
        int[][] dl = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
        int[] tmp;

        for (int i = 0; i < dl.length; i++) {
            int r = (int) (Math.random() * dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i = 0; i < dl.length; i++) {
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x + dx * 2, y + dy * 2) == MapData.TYPE_WALL) {
                setMap(x + dx, y + dy, MapData.TYPE_SPACE);
                digMap(x + dx * 2, y + dy * 2);
            }
        }
    }

    public static int[] getGoalCoordinate() {
        int[] goal = new int[2];
        int localCount = 0;
        // 変更の余地あり
        int[][] candidate = new int[30][2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 方向指定
                int top = 0;
                int under = 0;
                int right = 0;
                int left = 0;
                int enter = maps[y][x];
                if ((x - 1) >= 0) {
                    top = maps[y][x - 1];
                }
                if ((x + 1) < width) {
                    under = maps[y][x + 1];
                }
                if ((y - 1) >= 0) {
                    left = maps[y - 1][x];
                }
                if ((y + 1) < height) {
                    right = maps[y + 1][x];
                }
                // 条件の精査
                boolean localTerm1 = (top == TYPE_WALL && right == TYPE_WALL && left == TYPE_WALL
                        && under != TYPE_WALL);
                boolean localTerm2 = (top == TYPE_WALL && right == TYPE_WALL && left != TYPE_WALL
                        && under == TYPE_WALL);
                boolean localTerm3 = (top == TYPE_WALL && right != TYPE_WALL && left == TYPE_WALL
                        && under == TYPE_WALL);
                boolean localTerm4 = (top != TYPE_WALL && right == TYPE_WALL && left == TYPE_WALL
                        && under == TYPE_WALL);
                if ((localTerm1 || localTerm2 || localTerm3 || localTerm4) && enter != TYPE_WALL) {
                    if (!(x == 1 && y == 1)) {
                        candidate[localCount][0] = x;
                        candidate[localCount][1] = y;
                        localCount++;
                    }

                }
            }
        }
        localCount--;

        int randomGoal = (int) (Math.random() * localCount);
        goal[0] = candidate[randomGoal][0];
        goal[1] = candidate[randomGoal][1];
        System.out.println(goal[0] + "," + goal[1]);
        return goal;
    }

    public static int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public static void setMap(int x, int y, int type) {
        if (x < 1 || width <= x - 1 || y < 1 || height <= y - 1) {
            return;
        }
        maps[y][x] = type;
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    // public void setImageViews() {
    // for (int y = 0; y < height; y++) {
    // for (int x = 0; x < width; x++) {
    // mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
    // }
    // }
    // }

    public void setImageViews() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[getDetailImage(x, y)]);
            }
        }
    }

    public int getDetailImage(int x, int y) {
        int top = 0;
        int under = 0;
        int right = 0;
        int left = 0;
        if ((x - 1) >= 0) {
            top = maps[y][x - 1];
        }
        if ((x + 1) < width) {
            under = maps[y][x + 1];
        }
        if ((y - 1) >= 0) {
            left = maps[y - 1][x];
        }
        if ((y + 1) < height) {
            right = maps[y + 1][x];
        }

        int enter = maps[y][x];

        // Item
        if (enter == ItemDB.BANANA) {
            return 13;
        }
        if (enter == ItemDB.FISH) {
            return 14;
        }
        if (enter == ItemDB.SAKE) {
            return 15;
        }

        // GOAL
        if (enter == TYPE_GOAL) {
            return TYPE_GOAL;
        }
        // SPACE
        if (enter == 0) {
            return 0;
        }

        // 縦一
        else if (top == TYPE_WALL && under == TYPE_WALL && right != TYPE_WALL && left != TYPE_WALL) {
            return 2;
        }
        // 横一
        else if (top != TYPE_WALL && under != TYPE_WALL && right == TYPE_WALL && left == TYPE_WALL) {
            return 1;
        }

        // 上-右
        else if (top == TYPE_WALL && right == TYPE_WALL && under != TYPE_WALL && left != TYPE_WALL) {
            return 6;
        }
        // 上-左 (y-1)(x-1)=1 & (x+1)+(y+1)=0
        else if (top == TYPE_WALL && left == TYPE_WALL && under != TYPE_WALL && right != TYPE_WALL) {
            return 4;
        }
        // 下-右 (y+1)(x+1)=1 & (x-1)+(y-1)=0
        else if (under == TYPE_WALL && right == TYPE_WALL && top != TYPE_WALL && left != TYPE_WALL) {
            return 5;
        }
        // 下-左 (y+1)(x-1)=1 & (x+1)+(y-1)=0
        else if (under == TYPE_WALL && left == TYPE_WALL && top != TYPE_WALL && right != TYPE_WALL) {
            return 3;
        }

        // 下以外 (y-1)(x+1)(x-1)=1 & (y+1)=0
        else if (top == TYPE_WALL && right == TYPE_WALL && left == TYPE_WALL && under != TYPE_WALL) {
            return 10;
        }
        // 左以外 (y-1)(y+1)(x+1)=1 & (x-1)=0
        else if (top == TYPE_WALL && right == TYPE_WALL && under == TYPE_WALL && left != TYPE_WALL) {
            return 9;
        }
        // 上以外 (y+1)(x+1)(x-1)=1 & (y-1)=0
        else if (under == TYPE_WALL && right == TYPE_WALL && left == TYPE_WALL && top != TYPE_WALL) {
            return 8;
        }
        // 右以外 (y-1)(y+1)(x-1)=1 & (x+1)=0
        else if (top == TYPE_WALL && under == TYPE_WALL && left == TYPE_WALL && right != TYPE_WALL) {
            return 7;
        }
        // 十字 (x+1)(x-1)(y+1)(y-1)=1
        else if (top == TYPE_WALL && right == TYPE_WALL && left == TYPE_WALL && under == TYPE_WALL) {
            return 11;
        }
        // 上右左以外 下右左以外
        else {
            boolean localBoolean1 = (top != TYPE_WALL && right != TYPE_WALL && left != TYPE_WALL && under == TYPE_WALL);
            boolean localBoolean2 = (under != TYPE_WALL && right != TYPE_WALL && left != TYPE_WALL && top == TYPE_WALL);
            if (localBoolean1 || localBoolean2) {
                return 2;
            }
            return 1;
        }
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }
}
