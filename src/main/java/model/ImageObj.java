package model;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

public class ImageObj extends MovableObject {
    
    private Image image;

    public ImageObj(String path, Point position) {
        super(position);
        this.image = getImage(path);
    }

    public void setImage(String path) {
        this.image = getImage(path);
    }

    public Image getImage() {
        return image;
    }

	public static Image getImage(final String pathAndFileName) {
		final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
		return Toolkit.getDefaultToolkit().getImage(url);
	}

}
