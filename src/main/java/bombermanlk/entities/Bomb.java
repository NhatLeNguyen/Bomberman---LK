package bombermanlk.entities;

import com.example.bombermanlk.GamePanel;
import com.example.bombermanlk.KeyHandler;
import com.example.bombermanlk.graphics.TileManagement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bomb extends entities{
    GamePanel gp;

    public KeyHandler keyH;
    public boolean visible;
    public BufferedImage bomb1,bomb2,bomb3;

    public int bombExplosion = 0;

    public int bombCount = 0;

    public int count = 0;

    public Bomb(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        this.x = (gp.maxScreenCol-1)*gp.tileSize;
        this.y = (gp.maxScreenRow-1)*gp.tileSize;
        getBombImage();
    }
    void getBombImage(){
        try {
            bomb1 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomb1.png"));
            bomb2 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomb2.png"));
            bomb3 = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomb3.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(Bomber bomber, TileManagement tile){
        if(keyH.spacePressed == true && bomber.Alive && this.bombCount == 0 && !tile.checkBrick){
            gp.playSE(2);
            int aliRow = (bomber.x+gp.tileSize/2)/gp.tileSize * gp.tileSize;
            int aliCol = (bomber.y+gp.tileSize/2)/ gp.tileSize * gp.tileSize;
            this.x = aliRow;
            this.y = aliCol;
            visible = true;
            this.bombCount = 1;
        }
    }
    public void draw(Graphics2D g2){
        if(visible == true){
            BufferedImage image = null;
            if(count <= this.timeVisible/3)
                image = bomb1;
            if(count > this.timeVisible/3){
                image = bomb2;
            }
            if(count > this.timeVisible*2/3){
                image = bomb1;
            }
            if(count > this.timeVisible){
                image = bomb2;
            }
            if(count > this.timeVisible*4/3){
                image = bomb1;
            }
            if(count > this.timeVisible*5/3){
                image = bomb2;
            }
            if(count > this.timeVisible*2){
                image = bomb1;
            }
            if(count > this.timeVisible*7/3){
                image = bomb2;
            }
            if(count > this.timeVisible * 8/3){
                image = bomb3;
            }
            if(count > this.timeVisible * 3){
                image = null;
                this.visible = false;
                this.bombCount = 0;
                this.count = 0;
                gp.playSE(3);
            }
            if(gp.gameState != gp.pauseState) count++;
            g2.drawImage(image,this.x,this.y,gp.tileSize,gp.tileSize,null);

        }
    }
}
