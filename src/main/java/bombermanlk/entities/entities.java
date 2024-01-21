package bombermanlk.entities;

import com.example.bombermanlk.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class entities {
    int countEntity = 0;
    public ArrayList<entities> Entities = new ArrayList<>();
    public int x, y;
    public int speed;
        GamePanel gp;
    public final static int timeVisible = 30;

    public int countDie ;

    public Rectangle solidArea;

    public String direction;

    public boolean Alive;
    public boolean collisionOn = false;

    public entities() {
        this.gp = gp;
        countDie = 0;
    }

    public void update() {
    }
}
