package view.progressBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Button extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private ProgressBar pB;
	private String type; // play, pause, speedLeft, left, speedRight, right.
	private int size;
	private Color color;
	
	public Button(ProgressBar pB, String type, int size, Color color)
	{
		this.pB = pB;
		this.type = type;
		this.size = size;
		this.color = color;
		this.setSize(new Dimension(size, size));
		this.setOpaque(false);
		
		
		this.addListeners();
	}

	public void addListeners()
	{
		this.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e)
			{
				switch(type)
				{
				case "play":
					type = "pause";
					pB.resume();
					break;
				case "pause":
					type = "play";
					pB.pause();
					break;
				case "stop":
					pB.pause();
					pB.reset();
					break;
				case "left":
					pB.moveBack(20);
					break;
				case "right":
					pB.moveForward(20);
					break;
				case "speedLeft":
					pB.moveBack(60_000);
					break;
				case "speedRight":
					pB.moveForward(60_000);
					break;
				}
				repaint();
			}
			
		});
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(color);
		g.fillOval(0, 0, size, size);
		
		g.setColor(Color.WHITE);
		
		int s = size/2, k, kx, ky, w, space;
		int[] x, y;
		
		switch(type)
		{
		case "play":
			kx = (size/4)+2;
			ky = size/4;
	        x = new int[]{kx, kx+s, kx};
	        y = new int[]{ky, ky+s/2, ky+s};
	        g.fillPolygon(new Polygon(x, y, 3));
			break;
		case "pause":
			w = s/3;
			k = s/3;
			space = (s/2)+1;
	        g.fillRect(space, s/2, w, s);
	        g.fillRect(space+k+w, s/2, w, s);
			break;
		case "stop":
			g.fillRect((s/2)+1, (s/2)+1, s, s);
			break;
		case "left":
			w = s/3;
			space = (s/2)+1;
	        g.fillRect(space, s/2, w, s);
	        
	        s = (int)(size*0.45);
	        
			kx = (size/4)+6;
			ky = (size/4)+1;
	        x = new int[]{kx+s, kx, kx+s};
	        y = new int[]{ky, ky+s/2, ky+s};
	        g.fillPolygon(new Polygon(x, y, 3));
			break;
		case "right":
			w = s/3;
			space = (int)(size*0.65);
			g.setColor(Color.WHITE);
	        g.fillRect(space, s/2, w, s);
	        
	        s = (int)(size*0.45);
	        
			kx = (size/4)+3;
			ky = (size/4)+1;
	        x = new int[]{kx, kx+s, kx};
	        y = new int[]{ky, ky+s/2, ky+s};
	        g.fillPolygon(new Polygon(x, y, 3));
			break;
		case "speedLeft":
	        s = (int)(size*0.36);
	        
			kx = (int)(size*0.14);
			ky = (int)(0.32*size);
	        x = new int[]{kx+s, kx, kx+s};
	        y = new int[]{ky, ky+s/2, ky+s};
	        g.fillPolygon(new Polygon(x, y, 3));
	        
	        kx = (int)(size*0.5);
	        x = new int[]{kx+s, kx, kx+s};
	        g.fillPolygon(new Polygon(x, y, 3));
			break;
		case "speedRight":
	        s = (int)(size*0.36);
	        
			kx = (int)(size*0.14)+2;
			ky = (int)(0.32*size);
	        x = new int[]{kx, kx+s, kx};
	        y = new int[]{ky, ky+s/2, ky+s};
	        g.fillPolygon(new Polygon(x, y, 3));
	        
	        kx = (int)(size*0.5)+2;
	        x = new int[]{kx, kx+s, kx};
	        g.fillPolygon(new Polygon(x, y, 3));
			break;
		}
		
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
}