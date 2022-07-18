package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.ImageObj;
import model.Map;
import model.MovableObject;
import model.TextObj;

public class MapView extends JPanel {
    
    private MapPlayer mapPlayer;
    private Map map;
    private ArrayList<MovableObjView> objectsView;

    private int[] initial  = new int[2],
                  initTemp = new int[2],
                  lastTemp = new int[2];

    public MapView(MapPlayer mapPlayer, Map map) {
        this(map);
        this.mapPlayer = mapPlayer;
    }

    public MapView(Map map) {
        this.map = map;
        setBackground(map.getBackground());
        this.objectsView = new ArrayList<MovableObjView>();
        refreshObjListFromMap();

        this.setLayout(null);
        addViewsFromList();
        
        this.addMouseListener(new MouseAdapter() {
        
            public void mousePressed(MouseEvent e)
            {  
                //if(e.getClickCount() == 2)
                
                MovableObjView actualObj = getObjViewByPosition(e.getX(), e.getY());
                
                if(actualObj != null && !map.areObjLocked()) { // Si les objets ont le droit de bouger
                    MovableObjView.setSelectedObj(actualObj);
                    initial[0]  = (int)actualObj.getMovableObject().getX();
                    initial[1]  = (int)actualObj.getMovableObject().getY();
                    initTemp[0] = e.getX();
                    initTemp[1] = e.getY();
                    lastTemp[0] = e.getX();
                    lastTemp[1] = e.getY();
                    return;
                }
                MovableObjView.setSelectedObj(null);

                if(map.isMapLocked())
                    return;

                initial[0]  = (int)map.getX();
                initial[1]  = (int)map.getY();
                initTemp[0] = e.getX();
                initTemp[1] = e.getY();
                lastTemp[0] = e.getX();
                lastTemp[1] = e.getY();
            }
            
            public void mouseReleased(MouseEvent e)
            {
                repaint();
            }
            
        });
        
        this.addMouseMotionListener(new MouseAdapter() {
            
            public void mouseDragged(MouseEvent e)
            {
                MovableObjView actualObj = MovableObjView.selectObj;
                if(actualObj != null && !map.areObjLocked()) {
                    actualObj.getMovableObject().setX(initial[0] - initTemp[0] + lastTemp[0]);
                    actualObj.getMovableObject().setY(initial[1] - initTemp[1] + lastTemp[1]);
                    actualObj.refreshLocation(map);
                }
                else if(!map.isMapLocked()) {
                    int x = (initial[0]+(initTemp[0] - lastTemp[0]));
                    int y = (initial[1]+(initTemp[1] - lastTemp[1]));
                    map.setLocation(x, y);
                    refreshView();
                }
                lastTemp[0] = e.getX();
                lastTemp[1] = e.getY();
            }
            
        });
        /*
        this.addMouseWheelListener(new MouseAdapter() {
            
            public void mouseWheelMoved(MouseWheelEvent e) {
                    int oldSize = size, coeff = 3;
                    System.out.println("Scale : "+size);
                    
                    if (e.getWheelRotation() > 0) 
                    {
                        if(size > 10)
                            size -= coeff;
                    } 
                    else 
                    {
                        if(size < 100)
                            size += coeff;
                    }
                    
                    refreshCoord(elt, oldSize);
                    refreshCoord(lines, oldSize);
                    
                    repaint();
            }
            
        });
        */
    }        

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!map.getSize().equals(getSize()))
            map.setSize(getSize());
    }

    public void refreshObjectsView() {
        for(MovableObjView obj : objectsView) {
            if(obj.isOnMap(map)) {
                obj.refreshLocation(map);
            }
        }
    }

    public void refreshView() {
        refreshObjectsView();
        refreshToolBar();
        revalidate();
        repaint();
    }

    public void refreshToolBar() {
        if(mapPlayer == null)
            return;
        mapPlayer.refreshToolBar();
    }

    public void refreshObjListFromMap() {
        objectsView.clear();
        for(MovableObject obj : map.getObjects()) {
            if(obj instanceof TextObj)
                objectsView.add(new TextView((TextObj)obj));
            else if(obj instanceof ImageObj)
                objectsView.add(new ImageView((ImageObj)obj));
        }
    }

    public void addViewsFromList() {
        this.removeAll();
        for(MovableObjView obj : objectsView)
            this.add(obj);
        revalidate();
        repaint();
    }

    public void addTextView(String text) {
        addTextView(text, new Point((int)map.getX() + (int)getPreferredSize().getWidth()/2,
                                    (int)map.getY() + (int)getPreferredSize().getHeight()/2));
    }

    public void addTextView(String text, Point position) {
        objectsView.add(new TextView(map.addText(text, position)));
        revalidate();
        repaint(); // On rafraichit l'affichage pour voir le nouvel objet.
    }

    public void addImageView(String path, Point point) {
        objectsView.add(new ImageView(map.addImageObj(path, point)));
        revalidate();
        repaint();
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        if(map != null)
            map.setBackground(color);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<MovableObjView> getObjectsView() {
        return this.objectsView;
    }

    public void setObjectsView(ArrayList<MovableObjView> objectsView) {
        this.objectsView = objectsView;
    }

    public MovableObjView getObjViewByPosition(int x, int y) {
        for(MovableObjView obj : objectsView) {
            if(obj.isOnMe(x, y)) {
                return obj;
            }
        }
        return null;
    }

}
