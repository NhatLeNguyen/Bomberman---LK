package bombermanlk;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {

    GamePanel gp;
    Font arial_40;
    Graphics2D g2;
    public BufferedImage theme;
    public Rectangle pauseButton = new Rectangle(620, 300, 200, 80);
    public Rectangle playButton = new Rectangle(595, 410, 275, 45);
    public Rectangle helpButton = new Rectangle(595, 480, 275, 45);
    public Rectangle exitButton = new Rectangle(595, 550, 275, 45);

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 50);
    }

    public void getThemeImage() {
        try {
            theme = ImageIO.read(getClass().getResourceAsStream("/Tile/Theme_Menu.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.BLACK);

        if (gp.gameState == gp.titleState) {

            getThemeImage();
            g2.drawImage(theme, 0, 0, gp.tileSize * 32, gp.tileSize * 16, null);
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {

        }
        if (gp.gameState == gp.pauseState) {
            g2.draw(pauseButton);
            int length = (int) g2.getFontMetrics().getStringBounds("PAUSE", g2).getWidth();
            g2.drawString("PAUSE", gp.screenWidth / 2 - length / 2, gp.screenHeight / 2);

        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();

        }
        if( gp.gameState == gp.gameWinState){
            drawGameWinScreen();
        }
    }

    private void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int x, y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        // Game over
        text = "Game Over :((";
        g2.setColor(Color.BLACK);
        int length1 = (int) g2.getFontMetrics().getStringBounds(" Game Over :((", g2).getWidth();
        x = gp.screenWidth / 2 - length1 / 2;
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Replay
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        text = "Retry";
        int length2 = (int) g2.getFontMetrics().getStringBounds("Retry", g2).getWidth();
        g2.setColor(Color.white);
        x = gp.screenWidth / 2 - length2 / 2;
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

        // Quit
        text = "Quit";
        x = gp.screenWidth / 2 - length2 / 2;
        y += 70;
        g2.drawString(text, x, y);


    }
    private void drawGameWinScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int x, y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        // Game over
        text = "You Win !";
        g2.setColor(Color.BLACK);
        int length1 = (int) g2.getFontMetrics().getStringBounds("You Win !", g2).getWidth();
        x = gp.screenWidth / 2 - length1 / 2;
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Replay
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        text = "Replay";
        int length2 = (int) g2.getFontMetrics().getStringBounds("Retry", g2).getWidth();
        g2.setColor(Color.white);
        x = gp.screenWidth / 2 - length2 / 2;
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

        // Quit
        text = "Quit";
        x = gp.screenWidth / 2 - length2 / 2;
        y += 70;
        g2.drawString(text, x, y);


    }



    public void drawTitleScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));

        String text = "BOMBERMAN";

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        int y = gp.screenHeight / 2;

        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 6, y + 6);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

//        x = gp.screenWidth / 2;
//        y -= gp.tileSize * 2;
//        g2.drawImage(gp.bomber.bomberLeft1, x + 350, y, gp.tileSize * 2, gp.tileSize * 2, null);
//        g2.drawImage(gp.bomber.bomberRight1, x - 450, y, gp.tileSize * 2, gp.tileSize * 2, null);

        //MENU

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "NEW GAME";
        x = 600;
        y = 450;
        g2.drawString(text, x, y);

        text = "HELP";
        x = 670;
        y = 520;
        g2.drawString(text, x, y);

        text = "QUIT";
        x = 670;
        y = 590;
        g2.drawString(text, x, y);

        g2.draw(playButton);
        g2.draw(helpButton);
        g2.draw(exitButton);
    }


}

