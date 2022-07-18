package view;

import model.TextObj;

public class TextView extends MovableObjView {
    
    private TextObj textObj;

    public TextView(TextObj textObj) {
        super(textObj);
        this.textObj = textObj;
    }

    public TextObj getTextObj() {
        return this.textObj;
    }

    public void setTextObj(TextObj textObj) {
        setMovableObject(textObj);
        this.textObj = textObj;
    }



}
