package bombermanlk.entities.enemy;

import com.example.bombermanlk.GamePanel;
import com.example.bombermanlk.entities.Bomber;
import com.example.bombermanlk.graphics.TileManagement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.spec.ECPoint;
import java.util.ArrayList;
import java.util.Random;

public class Kondoria extends enemy {
    GamePanel gp;

    int kondoriaNum = 1;
    int kondoriaCounter = 0;

    int timeLoading = 0;
    public BufferedImage kondoriaRight1, kondoriaRight2, kondoriaRight3, kondoriaLeft1, kondoriaLeft2, kondoriaLeft3, kondoriaDead;

    public int test = 1;

    public String[][] findBomber;
    public boolean[][] visited;

    public void getKondoriaImage() {
        try {
            kondoriaRight1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/Kondoria/kondoria_right1.png"));
            kondoriaRight2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/Kondoria/kondoria_right2.png"));
            kondoriaRight3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/Kondoria/kondoria_right3.png"));
            kondoriaLeft1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/Kondoria/kondoria_left1.png"));
            kondoriaLeft2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/Kondoria/kondoria_left2.png"));
            kondoriaLeft3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/Kondoria/kondoria_left3.png"));
            kondoriaDead = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/Kondoria/kondoria_dead.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = gp.tileSize * 3;
        y = gp.tileSize * 2;
        speed = 1;
    }

    public Kondoria(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
        getKondoriaImage();
        this.Alive = true;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 36;
        direction = "right";
    }

    public Kondoria(GamePanel gp, int x, int y) {
        this.gp = gp;
        setDefaultValues();
        getKondoriaImage();
        this.Alive = true;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 36;
        direction = "right";
        this.x = x * gp.tileSize;
        this.y = y * gp.tileSize;
        this.speed = 2;
        timeLoading = 0;
    }

    public void setXY(int x, int y) {
        this.x = x * gp.tileSize;
        this.y = y * gp.tileSize;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (direction == null) direction = "right";
        switch (direction) {
            case "up": {
            }
            case "down": {
            }
            case "left": {
                if (kondoriaNum == 1) {
                    image = kondoriaLeft1;
                }
                if (kondoriaNum == 2) {
                    image = kondoriaLeft2;
                }
                if (kondoriaNum == 3) {
                    image = kondoriaLeft3;
                }
                break;
            }
            case "right": {
                if (kondoriaNum == 1) {
                    image = kondoriaRight1;
                }
                if (kondoriaNum == 2) {
                    image = kondoriaRight2;
                }
                if (kondoriaNum == 3) {
                    image = kondoriaRight3;
                }
                break;
            }
            case "nope": {
                if (this.countDie < timeVisible) {
                    image = kondoriaDead;
                }
                if (this.countDie > timeVisible) {
                    image = null;
                }
            }
        }
        this.countDie++;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

    @Override
    public void setAction() {
        try {
            if (this.Alive) {
                actionLockCounter++;
                if (gp.bomber.Alive) {
                    loading();
                    if (actionLockCounter == 24) {
                        direction = findBomber[(this.y + gp.tileSize / 2) / gp.tileSize][(this.x + gp.tileSize / 2) / gp.tileSize];
                        actionLockCounter = 0;
                    }
                } else if (actionLockCounter == 60) {
                    Random random = new Random();
                    int i = random.nextInt(4) + 1;
                    if (i == 1) direction = "down";
                    if (i == 4) direction = "right";
                    if (i == 3) direction = "left";
                    if (i == 2) direction = "up";
                    actionLockCounter = 0;
                }
                timeLoading++;
            } else {
                direction = "nope";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dung BFS tim duong di ngan nhat den bomber.
     */
    public void loading() {
        try {
            int bomberX = (gp.bomber.x + gp.tileSize / 2) / gp.tileSize;
            int bomberY = (gp.bomber.y + gp.tileSize / 2) / gp.tileSize;
            ArrayList<int[]> toado = new ArrayList<>();
            findBomber = new String[gp.maxScreenRow][gp.maxScreenCol];
            visited = new boolean[gp.maxScreenRow][gp.maxScreenCol];
            toado.add(new int[]{bomberY, bomberX});
            visited[bomberY][bomberX] = true;
            while (!toado.isEmpty() &&
                    (toado.get(0)[1] != (this.x + gp.tileSize / 2) / gp.tileSize
                            || toado.get(0)[0] != (this.y + gp.tileSize / 2) / gp.tileSize)) {
                if (gp.tile.map[gp.tile.mapNum][toado.get(0)[0] - 1][toado.get(0)[1]] != 1
                        && gp.tile.map[gp.tile.mapNum][toado.get(0)[0] - 1][toado.get(0)[1]] != 2
                        && !visited[toado.get(0)[0] - 1][toado.get(0)[1]]) {
                    findBomber[toado.get(0)[0] - 1][toado.get(0)[1]] = "down";
                    visited[toado.get(0)[0] - 1][toado.get(0)[1]] = true;
                    toado.add(new int[]{toado.get(0)[0] - 1, toado.get(0)[1]});
                }

                if (gp.tile.map[gp.tile.mapNum][toado.get(0)[0]][toado.get(0)[1] + 1] != 1
                        && gp.tile.map[gp.tile.mapNum][toado.get(0)[0]][toado.get(0)[1] + 1] != 2
                        && !visited[toado.get(0)[0]][toado.get(0)[1] + 1]) {
                    findBomber[toado.get(0)[0]][toado.get(0)[1] + 1] = "left";
                    visited[toado.get(0)[0]][toado.get(0)[1] + 1] = true;
                    toado.add(new int[]{toado.get(0)[0], toado.get(0)[1] + 1});
                }

                if (gp.tile.map[gp.tile.mapNum][toado.get(0)[0] + 1][toado.get(0)[1]] != 1
                        && gp.tile.map[gp.tile.mapNum][toado.get(0)[0] + 1][toado.get(0)[1]] != 2
                        && !visited[toado.get(0)[0] + 1][toado.get(0)[1]]) {
                    findBomber[toado.get(0)[0] + 1][toado.get(0)[1]] = "up";
                    visited[toado.get(0)[0] + 1][toado.get(0)[1]] = true;
                    toado.add(new int[]{toado.get(0)[0] + 1, toado.get(0)[1]});
                }

                if (gp.tile.map[gp.tile.mapNum][toado.get(0)[0]][toado.get(0)[1] - 1] != 1
                        && gp.tile.map[gp.tile.mapNum][toado.get(0)[0]][toado.get(0)[1] - 1] != 2
                        && !visited[toado.get(0)[0]][toado.get(0)[1] - 1]) {
                    findBomber[toado.get(0)[0]][toado.get(0)[1] - 1] = "right";
                    visited[toado.get(0)[0]][toado.get(0)[1] - 1] = true;
                    toado.add(new int[]{toado.get(0)[0], toado.get(0)[1] - 1});
                }
                toado.remove(0);
            }
        } catch (Exception e) {

        }
    }
    public void update() {
        try {
            setAction();
            if (this.Alive) {
                collisionOn = false;
                gp.checker.checkTile(this);
                if (collisionOn == false) {
                    switch (direction) {
                        case "up": {
                            y -= speed;
                            break;
                        }
                        case "down": {
                            y += speed;
                            break;
                        }
                        case "left": {
                            x -= speed;
                            break;
                        }
                        case "right": {
                            x += speed;
                            break;
                        }
                    }
                    if ((gp.bomber.y + gp.tileSize / 2) / gp.tileSize == (this.y + gp.tileSize / 2) / gp.tileSize
                            && (this.x - gp.tileSize < gp.bomber.x && this.x > gp.bomber.x
                            || this.x + gp.tileSize > gp.bomber.x && this.x < gp.bomber.x)
                            && gp.bomber.Alive) {
                        gp.bomber.countDie = 0;
                        gp.bomber.bombPass = false;
                        gp.bomber.Alive = false;
                    }
                    if ((gp.bomber.x + gp.tileSize / 2) / gp.tileSize == (this.x + gp.tileSize / 2) / gp.tileSize
                            && (this.y - gp.tileSize < gp.bomber.y && this.y > gp.bomber.y
                            || this.y + gp.tileSize > gp.bomber.y && this.y < gp.bomber.y)
                            && gp.bomber.Alive) {
                        gp.bomber.countDie = 0;
                        gp.bomber.bombPass = false;
                        gp.bomber.Alive = false;
                    }
                }

                kondoriaCounter++;
                if (kondoriaCounter > 6) {
                    if (kondoriaNum == 1) {
                        kondoriaNum = 2;
                    } else if (kondoriaNum == 2) {
                        kondoriaNum = 3;
                    } else if (kondoriaNum == 3) {
                        kondoriaNum = 1;
                    }
                    kondoriaCounter = 0;
                }
            }
        } catch (Exception e){
        }
    }
}
