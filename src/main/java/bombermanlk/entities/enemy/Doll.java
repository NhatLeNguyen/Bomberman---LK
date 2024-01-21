package bombermanlk.entities.enemy;

import com.example.bombermanlk.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Doll extends enemy{
    GamePanel gp;

    int dollNum = 1;
    int dollCounter = 0;

    public BufferedImage dollLeft1 ,dollLeft2 , dollLeft3, dollRight1, dollRight2, dollRight3, dollDead;

    public void getDollImage(){
        try {
            dollLeft1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/doll_left1.png"));
            dollLeft2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/doll_left2.png"));
            dollLeft3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/doll_left3.png"));
            dollRight1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/doll_right1.png"));
            dollRight2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/doll_right2.png"));
            dollRight3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/doll_right3.png"));
            dollDead = ImageIO.read(getClass().getResourceAsStream("/Sprite/Enemy/doll_dead.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = gp.tileSize * 3;
        y = gp.tileSize * 2;
        speed = 1;
    }

    public Doll(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
        getDollImage();
        this.Alive = true;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 36;
        direction = "right";
    }
    public Doll(GamePanel gp,int x,int y) {
        this.gp = gp;
        setDefaultValues();
        getDollImage();
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
                if (dollNum == 1) {
                    image = dollLeft1;
                }
                if (dollNum == 2) {
                    image = dollLeft2;
                }
                if (dollNum == 3) {
                    image = dollLeft3;
                }
                break;
            }
            case "right": {
                if (dollNum == 1) {
                    image = dollRight1;
                }
                if (dollNum == 2) {
                    image = dollRight2;
                }
                if (dollNum == 3) {
                    image = dollRight3;
                }
                break;
            }
            case "nope": {
                if(this.countDie < timeVisible){
                    image = dollDead;
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
                //actionLockCounter = 0;
            }
            if (actionLockCounter == 120) {
                this.speed += 1;  // Increasing speed
            }
            if (actionLockCounter == 180) {
                this.speed -= 1;
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
                if((gp.bomber.y+gp.tileSize/2)/gp.tileSize == (this.y+gp.tileSize/2)/ gp.tileSize
                        && (this.x-gp.tileSize < gp.bomber.x && this.x > gp.bomber.x
                            || this.x + gp.tileSize > gp.bomber.x && this.x < gp.bomber.x)
                        && gp.bomber.Alive){
                    gp.bomber.countDie = 0;
                    gp.bomber.bombPass = false;
                    gp.bomber.Alive = false;
                }
                if((gp.bomber.x+gp.tileSize/2)/gp.tileSize == (this.x+gp.tileSize/2)/ gp.tileSize
                        && (this.y-gp.tileSize < gp.bomber.y && this.y > gp.bomber.y
                        || this.y + gp.tileSize > gp.bomber.y && this.y < gp.bomber.y)
                        && gp.bomber.Alive){
                    gp.bomber.countDie = 0;
                    gp.bomber.bombPass = false;
                    gp.bomber.Alive = false;
                }
            }

            dollCounter++;
            if (dollCounter > 6) {
                if (dollNum == 1) {
                    dollNum = 2;
                } else if (dollNum == 2) {
                    dollNum = 3;
                } else if (dollNum == 3) {
                    dollNum = 1;
                }
                dollCounter = 0;
            }
        }
    }
}

