package model;

import java.awt.Point;

public class TextObj extends MovableObject {
    
    private String text;

    public TextObj(String text, Point position) {
        super(position);
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
