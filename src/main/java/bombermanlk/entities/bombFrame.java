package bombermanlk.entities;

import com.example.bombermanlk.GamePanel;
import com.example.bombermanlk.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.example.bombermanlk.graphics.TileManagement;
public class bombFrame extends entities{
    GamePanel gp;
    public KeyHandler keyH ;
    TileManagement tile;
    public int powerFrame = 1;
    public int frameBoost = 1;
    public int frameUp;
    public int frameDown;
    public int frameLeft;
    public int frameRight;
    int time;
    boolean visible;
    public BufferedImage bombExploded,explosionHorLeft,explosionHorRight,explosionVerDown,explosionVerTop,
            explosionHor,explosionVer;
    public bombFrame(GamePanel gp, KeyHandler keyH, TileManagement tile){
        this.gp = gp;
        this.keyH = keyH;
        this.time = 0;
        this.tile = tile;
        getExplosionImage();
    }
    public void getExplosionImage(){
        try {
            bombExploded = ImageIO.read(getClass().getResourceAsStream("/Sprite/bomb_exploded.png"));
            explosionHorLeft = ImageIO.read(getClass().getResourceAsStream("/Sprite/explosion_horizontal_left_last.png"));
            explosionHorRight = ImageIO.read(getClass().getResourceAsStream("/Sprite/explosion_horizontal_right_last.png"));
            explosionVerDown = ImageIO.read(getClass().getResourceAsStream("/Sprite/explosion_vertical_down_last.png"));
            explosionVerTop = ImageIO.read(getClass().getResourceAsStream("/Sprite/explosion_vertical_top_last.png"));
            explosionHor = ImageIO.read(getClass().getResourceAsStream("/Sprite/explosion_horizontal.png"));
            explosionVer = ImageIO.read(getClass().getResourceAsStream("/Sprite/explosion_vertical.png"));
        } catch(IOException e){
            e.printStackTrace();
        }

    }
    public void update(Bomb bomb,Bomber bomber){
        if(bomb.keyH.spacePressed == true && bomber.Alive){
            this.x = bomb.x;
            this.y = bomb.y;
            if(bomb.bombCount == 1){
                time = 0;
                visible = true;
                frameDown = 0;
                frameUp = 0;
                frameLeft = 0;
                frameRight = 0;
                while(tile.map[tile.mapNum][this.y/gp.tileSize+frameDown][this.x/gp.tileSize] != 1 &&  frameDown <= powerFrame){
                    if(tile.map[tile.mapNum][this.y/gp.tileSize+frameDown][this.x/gp.tileSize] == 2){
                        frameDown++;
                        break;
                    }
                    frameDown++;
                }
                frameDown--;
                while(tile.map[tile.mapNum][this.y/gp.tileSize-frameUp][this.x/gp.tileSize] != 1 && frameUp <= powerFrame){
                    if(tile.map[tile.mapNum][this.y/gp.tileSize-frameUp][this.x/gp.tileSize] == 2){
                        frameUp++;
                        break;
                    }
                    frameUp++;
                }
                frameUp--;
                while(tile.map[tile.mapNum][this.y/gp.tileSize][this.x/gp.tileSize-frameLeft] != 1 && frameLeft <= powerFrame){
                    if(tile.map[tile.mapNum][this.y/gp.tileSize][this.x/gp.tileSize-frameLeft] == 2){
                        frameLeft++;
                        break;
                    }
                    frameLeft++;
                }
                frameLeft--;
                while(tile.map[tile.mapNum][this.y/gp.tileSize][this.x/gp.tileSize+frameRight] != 1 && frameRight <= powerFrame){
                    if(tile.map[tile.mapNum][this.y/gp.tileSize][this.x/gp.tileSize+frameRight] == 2){
                        frameRight ++;
                        break;
                    }
                    frameRight++;
                }
                frameRight--;
            }
        }
    }
    public void draw(Graphics2D g2,Bomb bomb){
        if(visible && bomb.bombCount==0){
            g2.drawImage(bombExploded,this.x,this.y,gp.tileSize,gp.tileSize,null);
            // Left
            for(int i = 1;i < frameLeft;i++){
                g2.drawImage(explosionHor, this.x - gp.tileSize * i, this.y, gp.tileSize, gp.tileSize, null);
            }
            if(frameLeft>=1) {
                g2.drawImage(explosionHorLeft, this.x - gp.tileSize * frameLeft, this.y, gp.tileSize, gp.tileSize, null);
            }
            // Right
            for(int i = 1;i < frameRight;i++){
                g2.drawImage(explosionHor, this.x + gp.tileSize * i, this.y, gp.tileSize, gp.tileSize, null);
            }
            if(frameRight>=1) {
                g2.drawImage(explosionHorRight, this.x + gp.tileSize * frameRight, this.y, gp.tileSize, gp.tileSize, null);
            }
            //Down
            for(int i = 1;i < frameDown;i++){
                g2.drawImage(explosionVer, this.x, this.y + gp.tileSize*i, gp.tileSize, gp.tileSize, null);
            }
            if(frameDown>=1) {
                g2.drawImage(explosionVerDown, this.x, this.y + gp.tileSize * frameDown, gp.tileSize, gp.tileSize, null);
            }
            // Up
            for(int i = 1;i < frameUp;i++){
                g2.drawImage(explosionVer, this.x, this.y - gp.tileSize * i, gp.tileSize, gp.tileSize, null);
            }
            if(frameUp>=1) {
                g2.drawImage(explosionVerTop, this.x, this.y - gp.tileSize * frameUp, gp.tileSize, gp.tileSize, null);
            }
            if(time > timeVisible * 3) {
                bomb.bombCount = 0;
                this.x = (gp.maxScreenCol-1) * gp.tileSize;
                this.y = (gp.maxScreenRow-1)* gp.tileSize;
                visible = false;
                time = 0;
            }
        }
        if(gp.gameState != gp.pauseState) time++;
    }

}