package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import model.ImageObj;

public class ImageView extends MovableObjView {
    
    private ImageObj imageObj;

    public ImageView(ImageObj imageObj) {
        super(imageObj);
        this.imageObj = imageObj;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = imageObj.getImage();
        if(img == null) {
            g.drawString("X", 0, 0);
            return;
        }
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }

    public ImageObj getImageObj() {
        return this.imageObj;
    }

    public void setImageObj(ImageObj imageObj) {
        setMovableObject(imageObj);
        this.imageObj = imageObj;
    }

}
