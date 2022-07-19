package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Animation;
import model.Map;
import model.MovableObject;
import model.ObjectAction;
import model.ObjectAction.GotoAction;
import model.ObjectAction.GrowAction;
import model.progressBar.Time;
import view.progressBar.ProgressBar;

public class MapPlayer extends JPanel {
    
    private MapToolBar mapToolBar;
    private MapView mapView;
    private ProgressBar progressBar;

    private Animation animation;

    public MapPlayer(Map map) {
        this.animation = new Animation(map, new Time(10, "s"));
        this.mapView = new MapView(this, map);
        this.mapToolBar = new MapToolBar();
        this.progressBar = new ProgressBar(this, 0, Color.BLUE);

        this.setLayout(new BorderLayout());
        this.add(mapToolBar, BorderLayout.NORTH);
        this.add(mapView, BorderLayout.CENTER);

        JPanel pan = new JPanel();
        pan.setOpaque(false);
        pan.add(progressBar);
        this.add(pan, BorderLayout.SOUTH);
    }

    private class MapToolBar extends JPanel {

        private JLabel x = new JLabel("0"),
                       y = new JLabel("0");

        private JButton addLocation  = new JButton("Add location"),
                        addDistance = new JButton("Add distance");

        public MapToolBar() {
            JPanel pan = new JPanel();
            pan.setOpaque(false);
            pan.add(new JLabel("x="));
            pan.add(x);
            pan.add(new JLabel(", y="));
            pan.add(y);

            setLayout(new FlowLayout());
            add(pan);
            add(addLocation);
            add(addDistance);

            addLocation.addActionListener(e -> {
                if(MovableObjView.selectObj == null)
                    return;
                MovableObject obj = MovableObjView.selectObj.getMovableObject();
                animation.addGotoAction(progressBar.getTime(), obj, obj.getLocation());
            });

            addDistance.addActionListener(e -> {
                if(MovableObjView.selectObj == null)
                    return;
                MovableObject obj = MovableObjView.selectObj.getMovableObject();
                animation.addDistanceAction(progressBar.getTime(), obj, obj.getLocation());
            });

        }

        public void refreshCoord() {
            x.setText(""+getMap().getX());
            y.setText(""+getMap().getY());
            repaint();
        }

    }

    public void refresh() {
        refreshToolBar();
        mapView.refreshView();
    }

    public void refreshToolBar() {
        mapToolBar.refreshCoord();
    }

    public void applyObjectsActions(Time time) {
        if(animation == null)
            return;
        List<ObjectAction> list = animation.getActionsList(time);
        if(list == null)
            return;
        
        for(ObjectAction action : list) {
            if(action instanceof GotoAction) {
                GotoAction gotoAct = (GotoAction)action;
                gotoAct.apply();
            }
            else if(action instanceof GrowAction) {

            }
        }
        mapView.refreshView();
    }

    public Map getMap() {
        return mapView.getMap();
    }

    public MapView getMapView() {
        return mapView;
    }

    public Animation getAnimation() {
        return animation;
    }

}
