package model;

import java.awt.Dimension;
import java.awt.Point;

public abstract class ObjectAction {

    protected MovableObject object;

    public ObjectAction(MovableObject object) {
        this.object = object;
    }

    public abstract void apply();

    public static class GotoAction extends ObjectAction {

        private Point pos;

        public GotoAction(MovableObject object, Point pos) {
            super(object);
            this.pos = pos;
        }

        public void apply() {
            object.setLocation(pos);
        }

    }

    public class GrowAction extends ObjectAction {

        private Dimension size;

        public GrowAction(MovableObject object, Dimension size) {
            super(object);
            this.size = size;
        }
        
        public void apply() {
            object.setSize(size);
        }

    }

    public MovableObject getObject() {
        return object;
    }

    public void setObject(MovableObject obj) {
        this.object = obj;
    }

}
