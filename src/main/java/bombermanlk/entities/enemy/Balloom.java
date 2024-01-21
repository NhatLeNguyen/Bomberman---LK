package bombermanlk.entities.enemy;

import com.example.bombermanlk.GamePanel;
import com.example.bombermanlk.entities.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Balloom extends enemy {
    GamePanel gp;

    int balloomNum = 1;
    int balloomCounter = 0;
    public BufferedImage balloomRight1, balloomRight2, balloomRight3, balloomLeft1, balloomLeft2, balloomLeft3, balloomDead;

    /**
     * Load Image.
     */
    public void getBalloomImage() {
        try {
            balloomRight1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/balloom_right1.png"));
            balloomRight2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/balloom_right2.png"));
            balloomRight3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/balloom_right3.png"));
            balloomLeft1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/balloom_left1.png"));
            balloomLeft2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/balloom_left2.png"));
            balloomLeft3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/balloom_left3.png"));
            balloomDead = ImageIO.read(getClass().getResourceAsStream("/Sprite/balloom_dead.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = gp.tileSize * 3;
        y = gp.tileSize * 2;
        speed = 1;
    }

    /**
     * Ham khoi tao.
     */
    public Balloom(GamePanel gp,int x,int y) {
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
        this.x = x * gp.tileSize;
        this.y = y * gp.tileSize;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up": {
            }
            case "down": {
            }
            case "left": {
                if (balloomNum == 1) {
                    image = balloomLeft1;
                }
                if (balloomNum == 2) {
                    image = balloomLeft2;
                }
                if (balloomNum == 3) {
                    image = balloomLeft3;
                }
                break;
            }
            case "right": {
                if (balloomNum == 1) {
                    image = balloomRight1;
                }
                if (balloomNum == 2) {
                    image = balloomRight2;
                }
                if (balloomNum == 3) {
                    image = balloomRight3;
                }
                break;
            }
            case "nope": {
                if(this.countDie < timeVisible){
                    image = balloomDead;
                }
                if(this.countDie > timeVisible){
                    image = null;
                }
            }
        }
        this.countDie ++;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Cap nhap huong di cua entity.
     */
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
        if(this.Alive) {
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
            }

            balloomCounter++;
            if (balloomCounter > 6) {
                if (balloomNum == 1) {
                    balloomNum = 2;
                } else if (balloomNum == 2) {
                    balloomNum = 3;
                } else if (balloomNum == 3) {
                    balloomNum = 1;
                }
                balloomCounter = 0;
            }
        }
    }
}
