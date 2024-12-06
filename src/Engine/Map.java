package Engine;
import java.awt.Graphics;

public class Map {
    private final static int[][][] testMaps = {
        {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        }
    };

    private static String bg = "";

    public Map(String bg) {
        Map.bg = "assets/images/backgrounds/" + bg + ".png";
    }

    public static void setBG(String bg) {
        Map.bg = bg;
    }

    public static String getBG() {
        return bg;
    }

    public static void drawMap(int SCREENWIDTH, int SCREENHEIGHT, int map, Graphics g) {
        int[][] curMap = testMaps[map - 1];
        int numRows = curMap.length;
        int numCols = curMap[0].length;

        int tileWidth = (SCREENWIDTH / numCols);
        int tileHeight = (SCREENHEIGHT / numRows);

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (curMap[r][c] == 1) {
                    g.fillRect(c * tileWidth, r * tileHeight, tileWidth, tileHeight);
                }
            }
        }
    }
}