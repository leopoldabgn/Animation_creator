package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

public class Map extends Point {
    
    private Color background;
    private ArrayList<MovableObject> objects;
    private Point currentPos = new Point(0, 0);
    private Dimension size;

    private boolean mapLocked, objectsLocked;

    public Map(Color background) {
        super(0, 0);
        this.size = new Dimension(800, 800);
        this.background = background;
        this.objects = new ArrayList<MovableObject>();
    }

    public TextObj addText(String text, Point position) {
        TextObj textObj = new TextObj(text, position);
        objects.add(textObj);
        return textObj;
    }

    public ImageObj addImageObj(String path, Point point) {
        ImageObj imageObj = new ImageObj(path, point);
        objects.add(imageObj);
        return imageObj;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBackground() {
        return background;
    }

    public ArrayList<MovableObject> getObjects() {
        return objects;
    }

    public int getWidth() {
        return (int)size.getWidth();
    }

    public int getHeight() {
        return (int)size.getHeight();
    }

    public void setSize(Dimension dim) {
        this.size = dim;
    }

    public Dimension getSize() {
        return size;
    }

    public void setMapLocked(boolean locked) {
        this.mapLocked = locked;
    }

    public boolean isMapLocked() {
        return mapLocked;
    }

    public boolean areObjLocked() {
        return objectsLocked;
    }

    public void setObjLocked(boolean locked) {
        this.objectsLocked = locked;
    }

}
