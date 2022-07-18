package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Map;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;

	public Window(int w, int h)
	{
		this.setMinimumSize(new Dimension(w, h));
		this.setTitle("Maps");
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(w, h));
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Map demoMap = new Map(new Color(30, 30, 30));
		//demoMap.setMapLocked(true);
		//demoMap.setObjLocked(true);

		demoMap.addImageObj("icons/spacecraft.png", new Point(100, 100));
		demoMap.addImageObj("icons/spacecraft.png", new Point(300, 200));

		setMapPlayer(demoMap);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(MovableObjView.selectObj == null)
                    return;
                MovableObjView obj = MovableObjView.selectObj;
                int speed = 22;
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        obj.addX(-speed);
                        break;
                    case KeyEvent.VK_RIGHT:
                        obj.addX(speed);
                        break;
                    case KeyEvent.VK_UP:
                        obj.addY(-speed);
                        break;
                    case KeyEvent.VK_DOWN:
                        obj.addY(speed);
                        break;
					case KeyEvent.VK_ENTER:
					case KeyEvent.VK_ESCAPE:
						MovableObjView.setSelectedObj(null);
						break;
                }
                revalidate();
                repaint();
            }   
        });
		this.requestFocus();

		this.setVisible(true);
	}

	public void setMapPlayer(Map map) {
		MapPlayer mapPlayer = new MapPlayer(map);
		this.setContentPane(mapPlayer);
		mapPlayer.refresh();
		revalidate();
		repaint();
	}

	public void setMapView(Map map) {
		this.setContentPane(new MapView(map));
		revalidate();
		repaint();
	}

    public void setupPan(JPanel pan) {
		pan.setLayout(new GridLayout());
    	Dimension screen = this.getSize();
        int w = 1920, h = 1010;
        int top = ((int)screen.getHeight() * 170) / h;
        int left = ((int)screen.getWidth() * 500) / w;
        pan.setBorder(new EmptyBorder(top, left, top, left));
	}

	public void quit() {
		this.dispose();
		System.exit(0);
	}

}
