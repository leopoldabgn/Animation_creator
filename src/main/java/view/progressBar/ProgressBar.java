package view.progressBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.progressBar.Cursor;
import model.progressBar.Time;
import view.MapPlayer;

public class ProgressBar extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private MapPlayer mapPlayer;

	private Timer tm = new Timer(1, this);
	private double framRate = 50, // 40, 50, 100...
				   millisBetweenFrames = 1000 / framRate,
				   prevTime = System.currentTimeMillis();
	private Time preview, time, duration;
	private int mouseX = 0;
	private Cursor cursor;
	private boolean pause = true, startOnBar = false;
	private Color color;
	private Rectangle bar;
	
	public ProgressBar(MapPlayer mapPlayer, int cursorX, Color color) {
		this(mapPlayer.getAnimation().getDurationTime(), cursorX, color);
		this.mapPlayer = mapPlayer;
		this.setFrameRate(mapPlayer.getAnimation().getFrameRate());
	}

	public ProgressBar(Time duration, int cursorX, Color color)
	{
		this.duration = duration;
		this.color = color == null ? Color.RED : color;
		this.time = new Time();
		this.preview = new Time();
		this.setPreferredSize(new Dimension(1000, 120));
		this.bar = new Rectangle();
		int rectH = 40/3;
		bar.setSize((int)getPreferredSize().getWidth() - 2*rectH, rectH);
		this.cursor = new Cursor(cursorX, 0, rectH*2 + rectH/3, color);
		cursor.setY((int)getPreferredSize().getHeight()/2 - cursor.getSize()/2);
		bar.setLocation(cursor.getSize()/2, (int)getPreferredSize().getHeight()/2 - rectH/2);
		//this.setOpaque(false);
		
		this.setLayout(null);
		
		String[] t = new String[] {"play", "speedLeft", "left", "stop", "right", "speedRight"};
		
		Button temp;
		int k = 20, size = 45;
		
		for(String str : t)
		{
			temp = new Button(this, str, size, color);
			temp.setLocation(k, (int)getPreferredSize().getHeight()-temp.getHeight());
			this.add(temp);
			k += size+15;
		}
		
		//this.setBackground(Color.yellow);
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					pause = !pause;
				}
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(bar.contains(e.getX(), e.getY()))
				{
					//pause = true;
					startOnBar = true;
					cursor.setX(e.getX()-cursor.getSize()/2);
					repaint();
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if(pause || startOnBar)
				{
					//pause = false;
					startOnBar = false;
					time.setTime(evalTime());
				}
				if(!bar.contains(e.getX(), e.getY()))
				{
					cursor.setVisible(false);
					preview.setVisible(false);
					repaint();
				}
			}
			
		});

		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e){
				mouseX = e.getX();
				if(bar.contains(e.getX(), e.getY()))
				{
					if(!cursor.isVisible() || !preview.isVisible())
					{
						cursor.setVisible(true);
						preview.setVisible(true);
					}
					repaint();
				}
				else
				{
					if(cursor.isVisible() || preview.isVisible())
					{
						cursor.setVisible(false);
						preview.setVisible(false);
						repaint();
					}
				}
			}
			
			public void mouseDragged(MouseEvent e)
			{
				if(startOnBar)
				{
					//if(e.getX() < bar.getX() || e.getX() > getWidth()-bar.getX())
					if(!bar.contains(e.getX(), bar.getY()))
						return;
					mouseX = e.getX();
					cursor.setX(e.getX()-cursor.getSize()/2);
					time.setTime(evalTime());
					mapPlayer.applyObjectsActions(time);
					repaint();
				}
			}
			
		});
		
		tm.start();
	}
	
	public ProgressBar(Time duration)
	{
		this(duration, 0, Color.RED);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.fillRect((int)bar.getX(), (int)bar.getY(), (int)bar.getWidth(), (int)bar.getHeight());
		g.setColor(color);

		g.fillRect((int)bar.getX(), (int)bar.getY(), cursor.getX(), (int)bar.getHeight());
		
		cursor.draw(g);
		preview.setTime(evalTime(mouseX-(int)bar.getX(), (int)bar.getWidth(), duration, millisBetweenFrames));
		preview.draw(mouseX, (int)bar.getY()-15, g);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if((System.currentTimeMillis() - prevTime) < millisBetweenFrames)
			return;
		prevTime = System.currentTimeMillis();
		if(time.toMillis() >= duration.toMillis())
			pause = true;
		if(pause)
			return;
		time.add(millisBetweenFrames); // Example : 50fps -> 20 millis, 40fps -> 25 millis
		if(mapPlayer != null) {
			mapPlayer.applyObjectsActions(time);
		}
		cursor.setX(evalPosition());
		this.repaint();
	}
	
	public static int evalPosition(int w, Time time, Time duration)
	{

		double coeff = time.toMillis() / duration.toMillis();
		return (int)(w*coeff);
	}
	
	public int evalPosition()
	{
		return evalPosition((int)bar.getWidth(), time, duration);
	}
	
	public static int evalTime(int position, int w, Time duration, double millisBetweenFrames)
	{
		double coeff = (double)position/(double)w;
		int tm = (int)(coeff * duration.toMillis());
		int modulo = tm % (int)millisBetweenFrames;
		return tm - modulo;
	}
	
	public int evalTime()
	{
		return evalTime(cursor.getX(), (int)bar.getWidth(), duration, millisBetweenFrames);
	}
	
	public void pause()
	{
		pause = true;
	}
	
	public void resume()
	{
		pause = false;
	}
	
	public void moveBack(double millis) {
		moveBack(new Time(millis));
	}

	public void moveBack(Time tm)
	{
		time.remove(tm);
		cursor.setX(evalPosition());
		repaint();
	}
	
	public void moveForward(double millis) {
		moveForward(new Time(millis));
	}

	public void moveForward(Time tm)
	{
		if(time.toMillis() + tm.toMillis() > duration.toMillis())
			time.setTime(duration);
		else
			time.add(tm);
		cursor.setX(evalPosition());
		repaint();
	}
	
	public void reset()
	{
		((Button)(this.getComponent(0))).setType("play");
		time.setTime(0);
		cursor.setX(evalPosition());
		repaint();
	}

	public Time getTime() {
		return time;
	}

	public void setFrameRate(long framRate) {
		this.framRate = framRate;
		this.millisBetweenFrames = 1000 / framRate;
	}
	
}
