package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import model.progressBar.Time;

public class Animation {
    
    private Time duration;
    private Map map;
    private HashMap<Double, ArrayList<ObjectAction>> actions;

    public Animation(Map map, Time duration) {
        this.map = map;
        this.duration = duration;
        this.actions = new HashMap<Double, ArrayList<ObjectAction>>();

        for(MovableObject obj : map.getObjects())
            addGotoAction(new Time(0), obj, obj.getLocation());
    }

    public void addGotoAction(Time time, MovableObject obj, Point point) {
        actions.putIfAbsent(time.toMillis(), new ArrayList<>());
        ArrayList<ObjectAction> list = actions.get(time.toMillis());
        list.add(new ObjectAction.GotoAction(obj, point));
        // Ajouter des tests pour verifier si un gotoaction pour cet objet existe
        // deja. Si oui, on modifie sa valeur.
    }

    public ArrayList<ObjectAction> getActionsList(Time time) {
        return actions.getOrDefault(time.toMillis(), null);
    }

    public Time getDurationTime() {
        return duration;
    }

}
