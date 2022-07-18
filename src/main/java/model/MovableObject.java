package model;

import java.awt.Dimension;
import java.awt.Point;

public abstract class MovableObject extends Point {
    
    protected Dimension size;

    public MovableObject(Point point) {
        this(point, new Dimension(50, 50));
    }

    public MovableObject(Point point, Dimension size) {
        super(point);
        this.size = size;
    }

    public Dimension getSize() {
        return this.size;
    }

    public int getWidth() {
        return (int)size.getWidth();
    }

    public int getHeight() {
        return (int)size.getHeight();
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public boolean isOnMap(Map map) {
        if(map == null)
            return false;
        if(getX() + getWidth()  < map.getX() || getX() > map.getX() + map.getWidth())
            return false;
        if(getY() + getHeight() < map.getY() || getY() > map.getY() + map.getHeight())
            return false;
        return true;
    }

    public void setX(int x) {
        this.setLocation(x, getY());
    }

    public void setY(int y) {
        this.setLocation(getX(), y);
    }

    public void addX(int nb) {
        this.setLocation(getX() + nb, getY());
    }

    public void addY(int nb) {
        this.setLocation(getX(), getY() + nb);
    }

}
