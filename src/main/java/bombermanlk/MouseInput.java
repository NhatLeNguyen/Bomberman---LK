package bombermanlk;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MouseInput implements MouseListener {
    GamePanel gp;


    public MouseInput(GamePanel gp) {
        this.gp = gp;
    }

    public void helpWindow() {
        JFrame frameHelp = new JFrame("Help!");

        try {
            frameHelp.setContentPane(new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Tile/Help.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameHelp.setLocationRelativeTo(null);
        frameHelp.pack();
        frameHelp.setVisible(true);

    }

    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
//        public Rectangle playButton = new Rectangle(595, 410, 275, 45);
//        public Rectangle helpButton = new Rectangle(595, 480, 275, 45);
//        public Rectangle exitButton = new Rectangle(595, 550, 275, 45);
        if (gp.gameState == gp.titleState) {
            if (mx >= 595 && mx <= 870) {
                if (my >= 410 && my <= 455) {
                    gp.gameState = gp.playState;
                    //gp.playMusic(0);
                }
            }

            if (mx >= 595 && mx <= 870) {
                if (my >= 480 && my <= 525) {
                    helpWindow();
                }
            }

            if (mx >= 595 && mx <= 870) {
                if (my >= 550 && my <= 595) {
                    System.exit(1);
                }
            }
        }
        else if (gp.gameState == gp.gameOverState|| gp.gameState == gp.gameWinState){
            if (mx >= 550 && mx <= 840) {
                if (my >= 410 && my <= 455) {
                    gp.retry();
                }
            }

            if (mx >= 550 && mx <= 840) {
                if (my >= 480 && my <= 525) {
                    System.exit(1);
                }
            }
        }
    }


    public void mouseReleased(MouseEvent e) {

    }


    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
