package model.progressBar;

import java.awt.Color;
import java.awt.Graphics;

public class Cursor
{
	private boolean visible = false;
	private int x, y, size;
	private Color color;

	public Cursor(int x, int y, int size, Color color)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
	}
	
	public Cursor(int size)
	{
		this(0, 0, size, Color.RED);
	}
	
	public void draw(Graphics g)
	{
		if(!visible)
			return;
		g.setColor(color);
		g.fillOval(x, y, size, size);
	}
	
	public boolean isOnMe(int x, int y)
	{
		return (x >= this.x && x <= this.x+size) &&
			   (y >= this.y && y <= this.y+size);
	}
	
	public void setVisible(boolean bool)
	{
		this.visible = bool;
	}
	
	public boolean isVisible()
	{
		return this.visible;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}
	
}
