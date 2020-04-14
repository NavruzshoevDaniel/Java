package mvc.model.game.components;


import java.awt.*;
import java.util.ArrayList;

public class Wall {
    private ArrayList<Brick> bricks;
    private Rectangle wallArea;
    private int count;
    private int brickWidth;
    private int brickHeight;
    private int offsetWidth;
    private int offsetHeight;


    public Wall(Rectangle wallArea, int countWidth, int countHeight, int offsetWidth, int offsetHeight) {
        this.wallArea = wallArea;
        this.offsetWidth = offsetWidth;
        this.offsetHeight = offsetHeight;
        bricks = new ArrayList<>();
        count=countHeight*countWidth;
        init(countWidth, countHeight);
    }

    private void init(int countWidth, int countHeight) {
        int wallX = (int) wallArea.getX();
        int wallY = (int) wallArea.getY();

        brickWidth = (wallX / countWidth - offsetWidth);
        brickHeight = (wallY / countHeight - offsetHeight);

        int curBrickX;
        int curBrickY;

        for (int i = 0; i < countWidth; i++) {
            for (int j = 0; j < countHeight; j++) {
                curBrickX = wallX + j * (brickWidth + offsetWidth);
                curBrickY = wallY + i * (brickHeight + offsetHeight);
                bricks.add(i * countHeight + j, new Brick(curBrickX, curBrickY,brickWidth,brickHeight));
            }
        }
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }
}
