package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Map;
import model.MovableObject;

public abstract class MovableObjView extends JPanel {
    
    public static MovableObjView selectObj;
    
    private MovableObject movableObject;
    private int[] initialPos = new int[2], initialMouse = new int[2];

    public MovableObjView(MovableObject movableObject) {
        this.setOpaque(false);
        this.setBorder(null);
        setMovableObject(movableObject);
    }

    public MovableObject getMovableObject() {
        return this.movableObject;
    }

    public void setMovableObject(MovableObject obj) {
        this.movableObject = obj;
        this.setLocation(obj.getLocation());
        this.setSize(new Dimension(obj.getSize()));
        this.setPreferredSize(new Dimension(obj.getSize()));
    }

    public void refreshSettings(Map map) {
        refreshLocation(map);
        refreshSize();
    }

    public void refreshLocation(Map map) {
        if(map == null)
            return;
        this.setLocation((int)(-map.getX() + movableObject.getX()),
                         (int)(-map.getY() + movableObject.getY()));
    }

    public void refreshSize() {
        this.setPreferredSize(movableObject.getSize());
        this.setSize(movableObject.getSize());
    }

    public boolean isOnMap(Map map) {
        return movableObject.isOnMap(map);
    }

    public void addX(int nb) {
        movableObject.addX(nb);
        this.setLocation(getX() + nb, getY());
    }

    public void addY(int nb) {
        movableObject.addY(nb);
        this.setLocation(getX(), getY() + nb);
    }

    public boolean isOnMe(int x, int y) {
        return x >= getX() && x <= getX()+getWidth() && y >= getY() && y <= getY()+getHeight();
    }

    public static void setSelectedObj(MovableObjView obj) {
        if(selectObj != null) {
            selectObj.setBorder(null);
            selectObj.repaint();
        }
        selectObj = obj;
        if(obj == null)
            return;
        obj.setBorder(BorderFactory.createLineBorder(Color.RED));
        obj.repaint();
    }

}
