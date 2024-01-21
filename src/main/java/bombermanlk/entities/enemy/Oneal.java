package bombermanlk.entities.enemy;

import com.example.bombermanlk.GamePanel;
import com.example.bombermanlk.entities.Bomber;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Oneal extends enemy {
    GamePanel gp;

    int onealNum = 1;
    int onealCounter = 0;
    public BufferedImage onealRight1, onealRight2, onealRight3, onealLeft1, onealLeft2, onealLeft3, onealDead;

    public void getBalloomImage() {
        try {
            onealRight1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/oneal_right1.png"));
            onealRight2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/oneal_right2.png"));
            onealRight3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/oneal_right3.png"));
            onealLeft1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/oneal_left1.png"));
            onealLeft2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/oneal_left2.png"));
            onealLeft3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/oneal_left3.png"));
            onealDead = ImageIO.read(getClass().getResourceAsStream("/Sprite/oneal_dead.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = gp.tileSize * 10;
        y = gp.tileSize * 11;
        speed = 1;
    }
    public void setDefaultMap2() {
        this.x = gp.tileSize ;
        this.y = gp.tileSize * 13;
        this.Alive = true;
    }
    public void setDefaultMap3() {
        this.x = gp.tileSize ;
        this.y = gp.tileSize * 13;
        this.Alive = true;
    }

    public Oneal(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
        getBalloomImage();
        this.Alive = true;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;

        solidArea.height = 36;
        direction = "right";
    }
    public Oneal(GamePanel gp,int x, int y) {
        this.gp = gp;
        setDefaultValues();
        getBalloomImage();
        this.Alive = true;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 36;
        direction = "right";
        this.x = x * gp.tileSize;
        this.y = y * gp.tileSize;
    }
    public void setXY(int x,int y){
        this.x = x*gp.tileSize;
        this.y = y*gp.tileSize;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up": {
            }
            case "down": {
            }
            case "left": {
                if (onealNum == 1) {
                    image = onealLeft1;
                }
                if (onealNum == 2) {
                    image = onealLeft2;
                }
                if (onealNum == 3) {
                    image = onealLeft3;
                }
                break;
            }
            case "right": {
                if (onealNum == 1) {
                    image = onealRight1;
                }
                if (onealNum == 2) {
                    image = onealRight2;
                }
                if (onealNum == 3) {
                    image = onealRight3;
                }
                break;
            }
            case "nope": {
                if(this.countDie < timeVisible){
                    image = onealDead;
                }
                if(this.countDie > timeVisible){
                    image = null;
                }
            }
        }
        this.countDie ++;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

    @Override
    public void setAction() {
        if(this.Alive) {
            actionLockCounter++;
            if (actionLockCounter == 60) {
                Random random = new Random();
                int i = random.nextInt(4) + 1;
                if (i == 1) direction = "down";
                if (i == 4) direction = "right";
                if (i == 3) direction = "left";
                if (i == 2) direction = "up";
                actionLockCounter = 0;
            }
        }
        else{
            direction = "nope";
        }
    }


    public void update() {
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

            onealCounter++;
            if (onealCounter > 6) {
                if (onealNum == 1) {
                    onealNum = 2;
                } else if (onealNum == 2) {
                    onealNum = 3;
                } else if (onealNum == 3) {
                    onealNum = 1;
                }
                onealCounter = 0;
            }
        }
    }
}
