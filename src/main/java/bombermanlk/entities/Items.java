package bombermanlk.entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Items extends entities{
    public Items(){
    }
    public Items(int x,int y){
        this.x = x;
        this.y = y;
    }
    public void setXY(int x,int y){
        this.x = x;
        this.y = y;
    }
}
