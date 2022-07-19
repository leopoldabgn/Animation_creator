package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.ObjectAction.GotoAction;
import model.progressBar.Time;

public class Animation {
    
    private Time duration;
    private Map map;
    private HashMap<Double, ArrayList<ObjectAction>> actions;
    private long framRate = 50;

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

    public void addDistanceAction(Time endTime, MovableObject obj, Point endPoint) {
        Pair<Double, GotoAction> action = getLastGotoAction(obj);
        System.out.println(action.getKey()+" "+action.getValue().getGotoPos());
        if(action == null || action.getKey() == -1.0D)
            return;
        Double beginTime = action.getKey();
        Point beginPoint = action.getValue().getGotoPos();
        addDistanceAction(new Time(beginTime), endTime, obj, beginPoint, endPoint);
    }

    public void addDistanceAction(Time begin, Time endTime, MovableObject obj, Point beginPoint, Point endPoint) {
        if(begin.toMillis() == endTime.toMillis()) {
            addGotoAction(begin, obj, endPoint);
            return;
        }
        long timeBtw = (long)(endTime.toMillis() - begin.toMillis());
        double distX = endPoint.getX() - beginPoint.getX();
        double distY = endPoint.getY() - beginPoint.getY();
        long coeffX = (long)(timeBtw / distX);
        long coeffY = (long)(timeBtw / distY);
        long timeX  = timeBtw / (long)Math.abs(distX);
        long timeY  = timeBtw / (long)Math.abs(distY);

        System.out.println();

        // Deux listes avec des Paires Temps -> character ('X', 'Y')
        // Troisieme liste ou je mixe les deux
        ArrayList<Pair<Character, Long>> timeXL = new ArrayList<Pair<Character, Long>>();
        ArrayList<Pair<Character, Long>> timeYL = new ArrayList<Pair<Character, Long>>();

        for(long i=(long)begin.toMillis() + timeX;i <= endTime.toMillis();i += timeX) {
            timeXL.add(new Pair<Character, Long>('X', i));
        }

        for(long i=(long)begin.toMillis() + timeY;i <= endTime.toMillis();i += timeY)
            timeYL.add(new Pair<Character, Long>('Y', i));

        timeXL.addAll(timeYL);
        ArrayList<Pair<Character, Long>> finalList = triFusion(timeXL);
        int x = (int)(beginPoint.getX()), y = (int)(beginPoint.getY());
        for(Pair<Character, Long> p : finalList) {
            x += p.getKey() == 'X' ? coeffX : 0;
            y += p.getKey() == 'Y' ? coeffY : 0;
            addGotoAction(new Time(p.getValue()), obj, new Point(x, y));
            System.out.println(new Point(x, y));
        }

    }

    public Pair<Double, GotoAction> getLastGotoAction(MovableObject obj) {
        ArrayList<ArrayList<ObjectAction>> list = new ArrayList<ArrayList<ObjectAction>>(actions.values());
        Collections.reverse(list);
        int index = 0;
        for(ArrayList<ObjectAction> l2 : list) {
            for(ObjectAction obj2 : l2) {
                if((obj2 instanceof GotoAction) && (((GotoAction)obj2).getObject() == obj)) {
                    return new Pair<Double, GotoAction>(getKey(actions.size()-index-1), (GotoAction)obj2);
                }
            }
            index++;
        }
        return null;
    }

        public static ArrayList<Pair<Character, Long>> triFusion(ArrayList<Pair<Character, Long>> T)
        {
            if(T == null || T.size() <= 1) // Dans ces deux cas il n'y a rien à trier.
                return T;
            // Le tableau T a au moins 2 élements à partir d'ici.
            int border = 0, pos1 = 0, pos2 = 0;
            
            for(int i=0;i<T.size()-1;i++) // On cherche la frontière
            {
                border++;
                if(T.get(i+1).getValue() < T.get(i).getValue())
                    break;
            }
            
            // On crée les deux tableaux ordonnés avec la bonne taille.
            ArrayList<Pair<Character, Long>> t1 = new ArrayList<Pair<Character, Long>>();
            ArrayList<Pair<Character, Long>>t2 = new ArrayList<Pair<Character, Long>>();
            ArrayList<Pair<Character, Long>> tFinal = new ArrayList<Pair<Character, Long>>();
            
            // On remplit les deux tableaux ordonnés.
            for(int i=0;i<T.size();i++)
            {
                if(i < border)
                    t1.add(T.get(i));
                else
                    t2.add(T.get(i)); // equivaut à : t2[pos1] = T.get(i).getValue() suivit de pos1++;
            }
            
            // pos1 correspond à l'élément actuel de t1
            // pos2 à celui de t2
            // On compare alors t1[pos1] avec t2[pos2]
            // si t1[pos1] est plus petit alors on ajoute t1[pos1] et on avance de une case dans le tableau t1 (pos1++)
            // si t2[pos2] est plus petit alors on ajoute t2[pos2] et on avance de une case dans le tableau t2 (pos2++)
            pos1 = 0; // pos2 est déjà à 0.
            
            while(pos1 + pos2 != T.size())
            {
                if(pos1 >= t1.size()) // Si on sort du tableau t1
                {
                    tFinal.add(t2.get(pos2++)); // Alors on ajoute le prochain élément de t2. Puis on augmente pos2.
                }
                else if(pos2 >= t2.size()) // Si on sort du tableau t2
                {
                    tFinal.add(t1.get(pos1++)); // Alors on ajoute le prochain élément de t1. Puis on augmente pos1.
                }
                else if(t1.get(pos1).getValue() < t2.get(pos2).getValue()) // Sinon, si l'element actuel de t1 est plus petit que l'element actuel de t2.
                {
                    tFinal.add(t1.get(pos1++)); // Alors on ajoute l'element actuel de t1, et on augmente pos1.
                }
                else // Sinon, si l'element actuel de t2 est plus petit que l'element actuel de t1.
                {
                    tFinal.add(t2.get(pos2++)); // Alors on ajoute l'element actuel de t2, et on augmente pos2.
                }
            }
            
            return tFinal;
        }    

    public Double getKey(int index) {
        for(Double key : actions.keySet()) {
            if(index == 0)
                return key;
            index--;
        }
        return -1.0D;
    }

    public ArrayList<ObjectAction> getActionsList(Time time) {
        return actions.getOrDefault(time.toMillis(), null);
    }

    public Time getDurationTime() {
        return duration;
    }

    public long getFrameRate() {
        return framRate;
    }

    public long getMillisBtwFrames() {
        return 1000 / framRate;
    }

}

