package model.progressBar;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class Time
{
	private double time = 0;
	private boolean visible = false;
	
	public Time(double seconds)
	{
		this.time = seconds;
	}
	
	public Time()
	{
		this(0);
	}
	
	public Time(double hour, double seconds)
	{
		this.time = hour*3600 + seconds*60;
	}
	
	public Time(double time, String type)
	{
		switch(type.toUpperCase().charAt(0))
		{
		case 'S':
			this.time = time;
			break;
		case 'M':
			this.time = time*60;
			break;
		case 'H':
			this.time = time*3600;
			break;
		default:
			this.time = time;
			break;
		}
	}
	
	public void draw(int x, int y, Graphics g)
	{
		if(!visible)
			return;
		JLabel lbl = new JLabel(toMinutesSeconds());
		g.setColor(Color.BLACK);
		g.drawString(lbl.getText(), x-(int)lbl.getPreferredSize().getWidth()/2, y);
	}
	
	public String toMinutesSeconds()
	{
		return toMinutesSeconds(0, time);
	}
	
	public static String toMinutesSeconds(double hours, double seconds)
	{
		if(seconds < 0)
			seconds = 0;
		if(hours < 0)
			hours = 0;
		if(seconds < 60)
		{
			String str = hours+":";
			if(hours < 10)
				str = '0'+str;
			if(seconds < 10)
				str += '0';
			str += seconds;
			return str;
		}
		return toMinutesSeconds(hours+1, seconds-60);
	}
	
	public String toString()
	{
		return toMinutesSeconds();
	}
	
	public void setVisible(boolean bool)
	{
		this.visible = bool;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public double toMillis()
	{
		return time*1000;
	}
	
	public double toSeconds()
	{
		return time;
	}
	
	public void setTime(double seconds)
	{
		this.time = seconds;
	}
	
	public void remove(double seconds)
	{
		this.time -= seconds;
		if(this.time < 0)
			this.time = 0;
	}
	
	public void add(double seconds)
	{
		this.time += seconds;
	}
	
}
