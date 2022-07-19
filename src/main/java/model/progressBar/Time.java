package model.progressBar;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class Time
{
	private double millis = 0;
	private boolean visible = false;
	
	public Time(double millis)
	{
		this.millis = millis;
	}
	
	public Time()
	{
		this(0);
	}
	
	public Time(double hour, double minutes)
	{
		this.millis = (hour*3600 + minutes*60) * 1000;
	}
	
	public Time(double time, String type)
	{
		switch(type.toUpperCase().charAt(0))
		{
		case 'S':
			this.millis = time*1000;
			break;
		case 'M':
			this.millis = time*60*1000;
			break;
		case 'H':
			this.millis = time*3600*1000;
			break;
		default:
			this.millis = time;
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
		return toMinutesSeconds(0, millis / 1000);
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
		return millis;
	}
	
	public double toSeconds()
	{
		return millis / 1000;
	}
	
	public void setTime(double millis)
	{
		this.millis = millis;
	}

	public void setTime(Time tm)
	{
		this.millis = tm.toMillis();
	}
	
	public void remove(double millis)
	{
		System.out.println(this.millis+" "+millis);
		this.millis -= millis;
		if(this.millis < 0)
			this.millis = 0;
	}
	
	public void remove(Time time) {
		remove(time.toMillis());
	}

	public void add(double millis)
	{
		this.millis += millis;
	}
	
	public void add(Time time) {
		add(time.toMillis());
	}

}
