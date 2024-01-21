package bombermanlk.graphíc;

import bombermanlk.GamePanel;
import bombermanlk.entities.Bomb;
import bombermanlk.entities.bombFrame;
import bombermanlk.entities.entities;

import javax.imageio.ImageIO;
import java.awt.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TileManagement extends Tile{
     GamePanel gp;
    public Tile[] tiles;
    public Bomb[] bombs;
    public int mapNum;
    int maxMap =3;

    public int countArrBomb;
    int countVisible = 0;
    public boolean checkBrick,checkBrickLeft,checkBrickRight,checkBrickUp,checkBrickDown;

    String mapPath;

    public int[][][] map;

    public TileManagement(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        bombs = new Bomb[100];
        countArrBomb = 0;
        mapNum = 1;
        map = new int[maxMap+1][gp.maxScreenRow][gp.maxScreenCol];
        getTileImage();
        loadMap("/map/map1.txt",1);
        loadMap("/map/map2.txt",2);
        loadMap("/map/map3.txt",3);
    }
    public void getTileImage(){
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Wall.png"));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Brick1.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Brick2.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Brick3.png"));

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Brick4.png"));

            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Powerup_flames.png"));

            tiles[7] = new Tile();
            tiles[7].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Powerup_speed.png"));

            tiles[8] = new Tile();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Powerup_flamepass.png"));

            tiles[9] = new Tile();
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("/Tile/portal.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String mapPath,int mapNum){
        /*for(int i = 0;i< gp.maxScreenRow;i++){
            for(int j = 0;j < gp.maxScreenCol;j++){
                map[i][j] = singleMap2[i* gp.maxScreenCol+j];
            }
        }*/
        try{
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            String line = br.readLine();
            while(col<gp.maxScreenCol && row < gp.maxScreenRow){
                line = br.readLine();
                while(col < gp.maxScreenCol){
                    String number[] = line.split(" ");

                    int num  = Integer.parseInt(number[col]);
                    map[mapNum][row][col] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void update(entities entity, Bomb bomb, bombFrame frame) {
        if (bomb.keyH.spacePressed == true)
            if (bomb.bombCount == 1) {
                countVisible = 0;
            }
        if(bomb.bombCount == 0){
            // Check entities die
            for(int i = 0;i<entity.Entities.size();i++) {
                //Up
                if(entity.Entities.get(i).Alive) {
                    if (frame.x / gp.tileSize == (entity.Entities.get(i).x + gp.tileSize / 2) / gp.tileSize
                            && frame.y / gp.tileSize - frame.frameUp <= (entity.Entities.get(i).y + gp.tileSize / 2) / gp.tileSize
                            && frame.y / gp.tileSize >= (entity.Entities.get(i).y + gp.tileSize / 2) / gp.tileSize) {
                        entity.Entities.get(i).countDie = 0;
                        entity.Entities.get(i).Alive = false;
                    }
                    // Down
                    if (frame.x / gp.tileSize == (entity.Entities.get(i).x + gp.tileSize / 2) / gp.tileSize
                            && frame.y / gp.tileSize + frame.frameDown >= (entity.Entities.get(i).y + gp.tileSize / 2) / gp.tileSize
                            && frame.y / gp.tileSize <= (entity.Entities.get(i).y + gp.tileSize / 2) / gp.tileSize) {
                        entity.Entities.get(i).countDie = 0;
                        entity.Entities.get(i).Alive = false;
                    }
                    // Left
                    if (frame.y / gp.tileSize == (entity.Entities.get(i).y + gp.tileSize / 2) / gp.tileSize
                            && frame.x / gp.tileSize - frame.frameLeft <= (entity.Entities.get(i).x + gp.tileSize / 2) / gp.tileSize
                            && frame.x / gp.tileSize >= (entity.Entities.get(i).x + gp.tileSize / 2) / gp.tileSize) {
                        entity.Entities.get(i).countDie = 0;
                        entity.Entities.get(i).Alive = false;
                    }
                    // Right
                    if (frame.y / gp.tileSize == (entity.Entities.get(i).y + gp.tileSize / 2) / gp.tileSize
                            && frame.x / gp.tileSize + frame.frameRight >= (entity.Entities.get(i).x + gp.tileSize / 2) / gp.tileSize
                            && frame.x / gp.tileSize <= (entity.Entities.get(i).x + gp.tileSize / 2) / gp.tileSize) {
                        entity.Entities.get(i).countDie = 0;
                        entity.Entities.get(i).Alive = false;
                    }
                }
            }
        }
        try {
            // Up Check Brick
            if (map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] == 2 && bomb.bombCount == 0) {
                checkBrickUp = true;
                map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 3;
            }
            if (map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] == 3 && countVisible > time - 50) {
                map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 4;
            }
            if (map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] == 4 && countVisible > time * 2 - 100) {
                map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 5;
            }
            if (map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] == 5 && countVisible > time * 3 - 190) {
                if(bomb.x / gp.tileSize == gp.power_frame.x && bomb.y / gp.tileSize - frame.frameUp == gp.power_frame.y){
                    map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 6;
                }
                else if(bomb.x / gp.tileSize == gp.power_frame1.x && bomb.y / gp.tileSize - frame.frameUp == gp.power_frame1.y){
                    map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 6;
                }
                else if(bomb.x / gp.tileSize == gp.power_speed.x && bomb.y / gp.tileSize - frame.frameUp == gp.power_speed.y){
                    map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 7;
                }
                else if(bomb.x / gp.tileSize == gp.power_speed1.x && bomb.y / gp.tileSize - frame.frameUp == gp.power_speed1.y){
                    map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 7;
                }
                else if(bomb.x / gp.tileSize == gp.power_flamePass.x && bomb.y / gp.tileSize - frame.frameUp == gp.power_flamePass.y){
                    map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 8;
                }
                else if(bomb.x / gp.tileSize == gp.portal.x && bomb.y / gp.tileSize - frame.frameUp == gp.portal.y){
                    map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 9;
                }
                else {
                    map[mapNum][bomb.y / gp.tileSize - frame.frameUp][bomb.x / gp.tileSize] = 0;
                }
                checkBrickUp = false;
            }
        } catch (RuntimeException e){
            e.printStackTrace();
        }

        try {
            // Down Check Brick
            if (map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] == 2 && bomb.bombCount == 0) {
                checkBrickDown = true;
                map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 3;
            }
            if (map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] == 3 && countVisible > time - 50) {
                map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 4;
            }
            if (map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] == 4 && countVisible > time * 2 - 100) {
                map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 5;
            }
            if (map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] == 5 && countVisible > time * 3 - 190) {
                if(bomb.x / gp.tileSize == gp.power_frame.x && bomb.y / gp.tileSize + frame.frameDown == gp.power_frame.y){
                    map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 6;
                }
                else if(bomb.x / gp.tileSize == gp.power_frame1.x && bomb.y / gp.tileSize + frame.frameDown == gp.power_frame1.y){
                    map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 6;
                }
                else if(bomb.x / gp.tileSize == gp.power_speed.x && bomb.y / gp.tileSize + frame.frameDown == gp.power_speed.y){
                    map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 7;
                }
                else if(bomb.x / gp.tileSize == gp.power_speed1.x && bomb.y / gp.tileSize + frame.frameDown == gp.power_speed1.y){
                    map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 7;
                }
                else if(bomb.x / gp.tileSize == gp.power_flamePass.x && bomb.y / gp.tileSize + frame.frameDown == gp.power_flamePass.y){
                    map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 8;
                }
                else if(bomb.x / gp.tileSize == gp.portal.x && bomb.y / gp.tileSize + frame.frameDown == gp.portal.y){
                    map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 9;
                }
                else {
                    map[mapNum][bomb.y / gp.tileSize + frame.frameDown][bomb.x / gp.tileSize] = 0;
                }
                checkBrickDown = false;
            }
        } catch(RuntimeException e){
            e.printStackTrace();
        }
        // Left Check Brick
        try {
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] == 2 && bomb.bombCount == 0) {
                checkBrickLeft = true;
                map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 3;
            }
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] == 3 && countVisible > time - 50) {
                map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 4;
            }
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] == 4 && countVisible > time * 2 - 100) {
                map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 5;
            }
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] == 5 && countVisible > time * 3 - 190) {
                if(bomb.y / gp.tileSize == gp.power_frame.y && bomb.x / gp.tileSize - frame.frameLeft == gp.power_frame.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 6;
                }
                else if(bomb.y / gp.tileSize == gp.power_frame1.y && bomb.x / gp.tileSize - frame.frameLeft == gp.power_frame1.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 6;
                }
                else if(bomb.y / gp.tileSize == gp.power_speed.y && bomb.x / gp.tileSize - frame.frameLeft == gp.power_speed.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 7;
                }
                else if(bomb.y / gp.tileSize == gp.power_speed1.y && bomb.x / gp.tileSize - frame.frameLeft == gp.power_speed1.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 7;
                }
                else if(bomb.y / gp.tileSize == gp.power_flamePass.y && bomb.x / gp.tileSize - frame.frameLeft == gp.power_flamePass.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 8;
                }
                else if(bomb.y / gp.tileSize == gp.portal.y && bomb.x / gp.tileSize - frame.frameLeft == gp.portal.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 9;
                }
                else {
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize - frame.frameLeft] = 0;
                }
                checkBrickLeft = false;
            }
        } catch(RuntimeException e){
            e.printStackTrace();
        }

        // Right Check Brick
        try {
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] == 2 && bomb.bombCount == 0) {
                checkBrickRight = true;
                map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 3;
            }
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] == 3 && countVisible > time - 50) {
                map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 4;
            }
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] == 4 && countVisible > time * 2 - 100) {
                map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 5;
            }
            if (map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] == 5 && countVisible > time * 3 - 190) {
                if(bomb.y / gp.tileSize == gp.power_frame.y && bomb.x / gp.tileSize + frame.frameRight == gp.power_frame.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 6;
                }
                else if(bomb.y / gp.tileSize == gp.power_frame1.y && bomb.x / gp.tileSize + frame.frameRight == gp.power_frame1.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 6;
                }
                else if(bomb.y / gp.tileSize == gp.power_speed.y && bomb.x / gp.tileSize + frame.frameRight == gp.power_speed.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 7;
                }
                else if(bomb.y / gp.tileSize == gp.power_speed1.y && bomb.x / gp.tileSize + frame.frameRight == gp.power_speed1.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 7;
                }
                else if(bomb.y / gp.tileSize == gp.power_flamePass.y && bomb.x / gp.tileSize + frame.frameRight == gp.power_flamePass.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 8;
                }
                else if(bomb.y / gp.tileSize == gp.portal.y && bomb.x / gp.tileSize + frame.frameRight == gp.portal.x){
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 9;
                }
                else {
                    map[mapNum][bomb.y / gp.tileSize][bomb.x / gp.tileSize + frame.frameRight] = 0;
                }
                checkBrickRight = false;
            }
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        checkBrick = checkBrickLeft || checkBrickRight || checkBrickUp || checkBrickDown;
        // Xu li Items

    }
    public void draw(Graphics2D g2, Bomb bomb){
        for(int i=0;i<gp.maxScreenRow;i++){
            for(int j=0;j<gp.maxScreenCol;j++){
                g2.drawImage(tiles[map[mapNum][i][j]].image,j*gp.tileSize,i* gp.tileSize,gp.tileSize,gp.tileSize,null);
            }
        }
        if(gp.gameState != gp.pauseState) countVisible++;
    }
}
