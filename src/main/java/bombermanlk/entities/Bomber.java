package bombermanlk.entities;

import com.example.bombermanlk.GamePanel;
import com.example.bombermanlk.KeyHandler;
import com.example.bombermanlk.graphics.TileManagement;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bomber extends entities {
    public int speedBoost = 1;
    GamePanel gp;
    KeyHandler keyH;
    public BufferedImage bomberUp1,bomberUp2,bomberUp3,bomberDown1,bomberDown2,bomberDown3,
            bomberLeft1,bomberLeft2,bomberLeft3,bomberRight1,bomberRight2,bomberRight3,
            bomberDead1, bomberDead2, bomberDead3;

    int bomberCounter = 0;
    int bomberNum = 1;
    public boolean bombPass;

    public Bomber(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        this.Alive = true;
        bombPass = false;
        setDefaultValues();
        getBomberImage();
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 36;
        direction = "right";
    }

    public void setDefaultValues(){
        x = gp.tileSize;
        y = gp.tileSize;
        speed = 2;
    }
    public void setDefaultMap2() {
        this.x = gp.tileSize;
        this.y = gp.tileSize;
        this.Alive = true;
    }
    public void setDefaultMap3() {
        this.x = gp.tileSize;
        this.y = gp.tileSize;
        this.Alive = true;
    }
    public void setDefaultPositions(){
        x = gp.tileSize;
        y = gp.tileSize;
        direction = "right";
    }
    public void restoreLifeAndMan(){
        this.Alive = true;
        bombPass = false;

    }
    public void getBomberImage(){
        try{
            bomberRight1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberRight1.png"));
            bomberRight2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberRight2.png"));
            bomberRight3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberRight3.png"));
            bomberLeft1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberLeft1.png"));
            bomberLeft2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberLeft2.png"));
            bomberLeft3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberLeft3.png"));
            bomberUp1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberUp1.png"));
            bomberUp2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberUp2.png"));
            bomberUp3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberUp3.png"));
            bomberDown1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberDown1.png"));
            bomberDown2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberDown2.png"));
            bomberDown3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberDown3.png"));
            bomberDead1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberDead1.png"));
            bomberDead2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberDead2.png"));
            bomberDead3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomberDead3.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update(entities entity,TileManagement tile, bombFrame frame){
        if(bombPass){
            this.Alive = true;
        }
        if(this.Alive) {
            if (keyH.rightPressed == true || keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true) {
                if (keyH.upPressed == true) {
                    direction = "up";
                } else if (keyH.downPressed == true) {
                    direction = "down";
                } else if (keyH.leftPressed == true) {
                    direction = "left";
                } else if (keyH.rightPressed == true) {
                    direction = "right";
                }
                collisionOn = false;
                gp.checker.checkTile(this);
                if (collisionOn == false) {
                    boolean next_level = true; // Check xem tat ca entities da die chua ???
                    for(int i=1;i<entity.Entities.size();i++){
                        if(entity.Entities.get(i).Alive == true){
                            next_level = false;
                            break;
                        }
                    }
                    switch (direction) {
                        case "up": {
                            y -= speed;
                            // An items
                            if (tile.map[tile.mapNum][(y) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 6) {
                                gp.playSE(1);
                                tile.map[tile.mapNum][(y) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] = 0;
                                frame.powerFrame+=frame.frameBoost;
                            }
                            if (tile.map[tile.mapNum][(y) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 7) {
                                gp.playSE(1);
                                tile.map[tile.mapNum][(y) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] = 0;
                                this.speed+=speedBoost;
                            }
                            if (tile.map[tile.mapNum][(y) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 8) {
                                gp.playSE(1);
                                tile.map[tile.mapNum][(y) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] = 0;
                                this.bombPass = true;
                            }
                            if (tile.map[tile.mapNum][(y) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 9
                                   && next_level ) {
                                if(tile.mapNum == 3){
                                    gp.gameState = gp.gameWinState;
                                    gp.playSE(1);
                                }
                                if(tile.mapNum == 2){
                                    gp.playSE(1);
                                    tile.mapNum = 3;
                                    gp.bomber.setDefaultMap3();
                                    gp.setDefaultMap3();
                                    next_level = false;
                                }
                                if(tile.mapNum == 1){
                                    gp.playSE(1);
                                    tile.mapNum = 2;
                                    gp.bomber.setDefaultMap2();
                                    gp.setDefaultMap2();
                                    next_level = false;
                                }
                            }
                            //
                            break;
                        }
                        case "down": {
                            y += speed;
                            if (tile.map[tile.mapNum][(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 6) {
                                tile.map[tile.mapNum][(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] = 0;
                                gp.playSE(1);
                                frame.powerFrame+=frame.frameBoost;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 7) {
                                tile.map[tile.mapNum][(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] = 0;
                                gp.playSE(1);
                                this.speed+=speedBoost;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 8) {
                                tile.map[tile.mapNum][(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] = 0;
                                gp.playSE(1);
                                this.bombPass = true;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize / 2) / gp.tileSize] == 9
                                   && next_level ) {
                                if(tile.mapNum == 3){
                                    gp.gameState = gp.gameWinState;
                                    gp.playSE(1);
                                }
                                if(tile.mapNum == 2){
                                    gp.playSE(1);
                                    tile.mapNum = 3;
                                    gp.bomber.setDefaultMap3();
                                    gp.setDefaultMap3();
                                    next_level = false;
                                }
                                if(tile.mapNum == 1){
                                    gp.playSE(1);
                                    tile.mapNum = 2;
                                    gp.bomber.setDefaultMap2();
                                    gp.setDefaultMap2();
                                    next_level = false;
                                }
                            }
                            break;
                        }
                        case "left": {
                            x -= speed;
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x) / gp.tileSize] == 6) {
                                tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x) / gp.tileSize] = 0;
                                gp.playSE(1);
                                frame.powerFrame+=frame.frameBoost;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x) / gp.tileSize] == 7) {
                                tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x) / gp.tileSize] = 0;
                                gp.playSE(1);
                                this.speed+=speedBoost;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x) / gp.tileSize] == 8) {
                                tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x) / gp.tileSize] = 0;
                                gp.playSE(1);
                                this.bombPass = true;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x) / gp.tileSize] == 9
                                   && next_level ) {
                                if(tile.mapNum == 3){
                                    gp.gameState = gp.gameWinState;
                                    gp.playSE(1);
                                }
                                if(tile.mapNum == 2){
                                    gp.playSE(1);
                                    tile.mapNum = 3;
                                    gp.bomber.setDefaultMap3();
                                    gp.setDefaultMap3();
                                    next_level = false;
                                }
                                if(tile.mapNum == 1){
                                    gp.playSE(1);
                                    tile.mapNum = 2;
                                    gp.bomber.setDefaultMap2();
                                    gp.setDefaultMap2();
                                    next_level = false;
                                }
                            }
                            break;
                        }
                        case "right": {
                            x += speed;
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x + gp.tileSize) / gp.tileSize] == 6) {
                                tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x + gp.tileSize) / gp.tileSize] = 0;
                                gp.playSE(1);
                                frame.powerFrame+=frame.frameBoost;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x + gp.tileSize) / gp.tileSize] == 7) {
                                tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x + gp.tileSize) / gp.tileSize] = 0;
                                gp.playSE(1);
                                this.speed+=speedBoost;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x + gp.tileSize) / gp.tileSize] == 8) {
                                tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x + gp.tileSize) / gp.tileSize] = 0;
                                gp.playSE(1);
                                this.bombPass = true;
                            }
                            if (tile.map[tile.mapNum][(y + gp.tileSize / 2) / gp.tileSize][(x + gp.tileSize) / gp.tileSize] == 9
                                   && next_level ) {
                                if(tile.mapNum == 3){
                                    gp.gameState = gp.gameWinState;
                                    gp.playSE(1);
                                }
                                if(tile.mapNum == 2){
                                    gp.playSE(1);
                                    tile.mapNum = 3;
                                    gp.bomber.setDefaultMap3();
                                    gp.setDefaultMap3();
                                    next_level = false;
                                }
                                if(tile.mapNum == 1){
                                    gp.playSE(1);
                                    tile.mapNum = 2;
                                    gp.bomber.setDefaultMap2();
                                    gp.setDefaultMap2();
                                    next_level = false;
                                }
                            }
                            break;
                        }
                    }
                }

                bomberCounter++;
                if (bomberCounter > 6) {
                    if (bomberNum == 1) {
                        bomberNum = 2;
                    } else if (bomberNum == 2) {
                        bomberNum = 3;
                    } else if (bomberNum == 3) {
                        bomberNum = 1;
                    }
                    bomberCounter = 0;
                }
            }
        }
        else{
            direction = "nope";
            this.speed = 2;
            frame.powerFrame = 1;
            gp.playSE(4);
            gp.gameState = gp.gameOverState;

        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch(direction){
            case "up":{
                if(bomberNum == 1){
                    image = bomberUp1;
                }
                if(bomberNum == 2){
                    image = bomberUp2;
                }
                if(bomberNum == 3){
                    image = bomberUp3;
                }
                break;
            }
            case "down":{
                if(bomberNum == 1){
                    image = bomberDown1;
                }
                if(bomberNum == 2){
                    image = bomberDown2;
                }
                if(bomberNum == 3){
                    image = bomberDown3;
                }
                break;
            }
            case "left":{
                if(bomberNum == 1){
                    image = bomberLeft1;
                }
                if(bomberNum == 2){
                    image = bomberLeft2;
                }
                if(bomberNum == 3){
                    image = bomberLeft3;
                }
                break;
            }
            case "right":{
                if(bomberNum == 1){
                    image = bomberRight1;

                }
                if(bomberNum == 2){
                    image = bomberRight2;
                }
                if(bomberNum == 3){
                    image = bomberRight3;
                }
                break;
            }
            case "nope":{

                if(this.countDie < timeVisible){
                    image = bomberDead1;
                }
                if(this.countDie > timeVisible){
                    image = bomberDead2;
                }
                if(this.countDie > timeVisible *2){
                    image = bomberDead3;
                }
                if(this.countDie > timeVisible * 3){
                    image =null;

                }

            }
        }
        this.countDie++;
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }
}
