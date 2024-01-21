package bombermanlk;

import com.example.bombermanlk.entities.*;
import com.example.bombermanlk.entities.enemy.Balloom;
import com.example.bombermanlk.entities.enemy.Doll;
import com.example.bombermanlk.entities.enemy.Kondoria;
import com.example.bombermanlk.entities.enemy.Oneal;
import com.example.bombermanlk.graphics.TileManagement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTING
    final int originalTileSize = 16; //16*16 tiles
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48*48 tiles

    public final int maxScreenCol = 30;  //20
    public final int maxScreenRow = 15;  //12
    final int screenWidth = tileSize * maxScreenCol;  //768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    KeyHandler keyH = new KeyHandler(this);
    MouseInput mInput = new MouseInput(this);
    Thread gameThread;

    public entities entity = new entities();

    public Bomber bomber = new Bomber(this, keyH);
    /*public Balloom balloom = new Balloom(this);
    public Oneal oneal = new Oneal(this);*/
    public UI ui = new UI(this);
    public static int gameState = 0;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

    public final int gameOverState = 3;
    public final int gameWinState = 4;


    public ArrayList<Balloom> ballooms = new ArrayList<>();
    public ArrayList<Oneal> oneals = new ArrayList<>();

    public ArrayList<Doll> dolls = new ArrayList<>();
    public ArrayList<Kondoria> kondorias = new ArrayList<>();
    public Items power_speed = new Items();
    public Items power_speed1 = new Items();
    public Items power_frame = new Items();
    public Items power_frame1 = new Items();
    public Items power_flamePass = new Items();
    public Items portal = new Items();
    Bomb bomb = new Bomb(this, keyH);
    public TileManagement tile = new TileManagement(this);
    public CollisionChecker checker = new CollisionChecker(this);

    public bombFrame frame = new bombFrame(this, keyH, tile);

    public Sound sound = new Sound();
    //FPS
    int FPS = 60;

    public void addBalloom(){
        ballooms.add(new Balloom(this,3,6));
        ballooms.add(new Balloom(this,4,10));
        ballooms.add(new Balloom(this,25,1));
        ballooms.add(new Balloom(this,22,4));
        ballooms.add(new Balloom(this,24,8));
        ballooms.add(new Balloom(this,27,12));
    }
    public void addOneal(){
        oneals.add(new Oneal(this,7,7));
        oneals.add(new Oneal(this,12,5));
        oneals.add(new Oneal(this,17,10));
        oneals.add(new Oneal(this,15,13));
        oneals.add(new Oneal(this,21,12));
    }
    public void addDoll(){
        dolls.add(new Doll(this,9,1));
        dolls.add(new Doll(this,13,5));
        dolls.add(new Doll(this,17,10));
    }
    public void addKondoria(){
        kondorias.add(new Kondoria(this,3,6));
        kondorias.add(new Kondoria(this,4,1));
        kondorias.add(new Kondoria(this,7,2));
        kondorias.add(new Kondoria(this,15,1));
        kondorias.add(new Kondoria(this,17,2));
    }

    public void setEntity() {
        ballooms = new ArrayList<>();
        oneals = new ArrayList<>();
        dolls = new ArrayList<>();
        kondorias = new ArrayList<>();
        entity.Entities = new ArrayList<>();
        addBalloom();
        addOneal();
        addDoll();
        addKondoria();
        portal.setXY(7,4);
        power_frame.setXY(3,1);
        power_speed.setXY(2,2);
        power_speed1.setXY(16,8);
        entity.Entities.add(bomber);
        entity.Entities.addAll(ballooms);
        entity.Entities.addAll(oneals);
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mInput);
        this.setFocusable(true);
        this.setEntity();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        playMusic(0);
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                // Update
                update();
                //Draw
                repaint();  // Goi phuong thuc paintComponent ke thua tu lop Jpanel
                delta--;
                drawCount++;
            }
        }
    }
    public void setDefaultMap2(){
        bomber.x = tileSize;
        bomber.y = tileSize*2;
        power_frame.setXY(1,1);
        power_frame1.setXY(22,5);
        power_speed.setXY(5,9);
        power_speed1.setXY(18,10);
        power_flamePass.setXY(17,4);
        portal.setXY(13,13);
        ballooms = new ArrayList<>();
        oneals = new ArrayList<>();
        ballooms.add(new Balloom(this,7,6));
        ballooms.add(new Balloom(this,6,9));
        ballooms.add(new Balloom(this,15,8));
        ballooms.add(new Balloom(this,18,7));
        ballooms.add(new Balloom(this,21,11));
        ballooms.add(new Balloom(this,20,13));
        ballooms.add(new Balloom(this,23,13));
        oneals.add(new Oneal(this,6,12));
        oneals.add(new Oneal(this,13,9));
        oneals.add(new Oneal(this,14,6));
        oneals.add(new Oneal(this,21,6));
        oneals.add(new Oneal(this,18,12));
        entity.Entities.addAll(ballooms);
        entity.Entities.addAll(oneals);
        entity.Entities.addAll(dolls);
        entity.Entities.addAll(kondorias);

    }

    public void setDefaultMap3() {
        bomber.x = tileSize;
        bomber.y = tileSize;
        power_speed.setXY(14, 1);
        power_frame.setXY(15, 1);
        power_flamePass.setXY(16, 1);
        portal.setXY(28, 1);
        ballooms = new ArrayList<>();
        oneals = new ArrayList<>();
        dolls = new ArrayList<>();
        kondorias = new ArrayList<>();
        ballooms.add(new Balloom(this, 1, 4));
        ballooms.add(new Balloom(this, 5, 3));
        ballooms.add(new Balloom(this, 8, 4));
        ballooms.add(new Balloom(this, 11, 3));
        ballooms.add(new Balloom(this, 15, 4));
        ballooms.add(new Balloom(this, 17, 3));
        ballooms.add(new Balloom(this, 19, 4));
        ballooms.add(new Balloom(this, 23, 3));
        ballooms.add(new Balloom(this, 26, 4));
        ballooms.add(new Balloom(this, 28, 3));
        oneals.add(new Oneal(this, 1, 6));
        oneals.add(new Oneal(this, 4, 7));
        oneals.add(new Oneal(this, 7, 7));
        oneals.add(new Oneal(this, 10, 6));
        oneals.add(new Oneal(this, 13, 6));
        oneals.add(new Oneal(this, 17, 7));
        oneals.add(new Oneal(this, 22, 6));
        oneals.add(new Oneal(this, 27, 7));
        dolls.add(new Doll(this, 4, 9));
        dolls.add(new Doll(this, 9, 10));
        dolls.add(new Doll(this, 14, 9));
        dolls.add(new Doll(this, 19, 10));
        dolls.add(new Doll(this, 23, 9));
        dolls.add(new Doll(this, 26, 10));
        kondorias.add(new Kondoria(this, 3, 12));
        kondorias.add(new Kondoria(this, 7, 13));
        kondorias.add(new Kondoria(this, 20, 13));
        kondorias.add(new Kondoria(this, 24, 12));
        kondorias.add(new Kondoria(this, 28, 13));
        entity.Entities.addAll(ballooms);
        entity.Entities.addAll(oneals);
        entity.Entities.addAll(dolls);
        entity.Entities.addAll(kondorias);
    }
    public void update() {
        if (gameState == playState) {
            bomber.update(entity, tile, frame);
            for (Balloom balloom : ballooms) {
                balloom.update();
            }
            for (Oneal oneal : oneals) {
                oneal.update();
            }
            if (tile.mapNum == 2 || tile.mapNum == 3) {
                for (Doll doll : dolls) {
                    doll.update();
                }
            }
            if (tile.mapNum == 2 || tile.mapNum == 3){
                for (Kondoria kondoria : kondorias){
                    kondoria.update();
                }
            }
            bomb.update(bomber, tile);
            frame.update(bomb, bomber);
            tile.update(entity, bomb, frame);
        } else if (gameState == pauseState) {
        } else if (gameState == gameOverState) {
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameState == titleState) {
            ui.draw(g2);

        } else {

            if (gameState == pauseState) {
                tile.draw(g2, bomb);
                bomb.draw(g2);
                for (Balloom balloom : ballooms) {
                    balloom.draw(g2);
                }
                for (Oneal oneal : oneals) {
                    oneal.draw(g2);
                }
                if (tile.mapNum == 2 || tile.mapNum == 3){
                    for (Doll doll : dolls){
                        doll.draw(g2);
                    }
                }
                if (tile.mapNum == 2 || tile.mapNum == 3){
                    for (Kondoria kondoria : kondorias){
                        kondoria.draw(g2);
                    }
                }
                frame.draw(g2, bomb);
                bomber.draw(g2);
                ui.draw(g2);
            } else {
                tile.draw(g2, bomb);
                bomb.draw(g2);
                for (Balloom balloom : ballooms) {
                    balloom.draw(g2);
                }
                for (Oneal oneal : oneals) {
                    oneal.draw(g2);
                }
                if (tile.mapNum == 2 || tile.mapNum == 3){
                    for (Doll doll : dolls){
                        doll.draw(g2);
                    }
                }
                if (tile.mapNum == 2 || tile.mapNum == 3){
                    for (Kondoria kondoria : kondorias){
                        kondoria.draw(g2);
                    }
                }
                frame.draw(g2, bomb);
                bomber.draw(g2);
            }
        }
        if (gameState == gameOverState || gameState == gameWinState) {
            ui.draw(g2);
        }

        g2.dispose();
    }

    public void retry() {
        bomber.setDefaultPositions();
        bomber.restoreLifeAndMan();
        tile.mapNum = 1;
        frame.frameUp = 0;
        frame.frameRight = 0;
        frame.frameLeft = 0;
        frame.frameDown = 0;
        bomb.bombCount = 0;
        tile.checkBrickLeft = false;
        tile.checkBrickRight = false;
        tile.checkBrickDown = false;
        tile.checkBrickUp = false;
        bomb.x = (maxScreenCol-2)*tileSize;
        bomb.y = (maxScreenRow-2)*tileSize;
        tile.loadMap("/map/map1.txt",1);
        tile.loadMap("/map/map2.txt",2);
        tile.loadMap("/map/map3.txt",3);
        setEntity();
        gameState = playState;
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}
