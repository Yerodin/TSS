package tss_GUI.views.FilterAndTimetableView;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import tss_GUI.GUIDefs;
import tss_core.TSSCore;
import tss_timetableProcessor.Timetable;

public class TimeTablePane extends JPanel
{
	private Timetable[] timetables;
	private int currentlySelected;
	private Image prevArrow, nextArrow;
	public TimeTablePane(ArrayList<Timetable> timetables)
	{
		try
		{
			prevArrow = ImageIO.read(TSSCore.class.getClassLoader().getResourceAsStream("tss_GUI\\res\\scrollArrowl.png"));
			nextArrow = ImageIO.read(TSSCore.class.getClassLoader().getResourceAsStream("tss_GUI\\res\\scrollArrowr.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.timetables = new Timetable[timetables.size()];
		int midpoint = (int) Math.ceil(timetables.size()/2);
		this.timetables[midpoint] =  timetables.get(0);
		int j = 1;
		for(int i = 1; i<timetables.size(); i = i+2)
		{
			this.timetables[midpoint-j] = timetables.get(i);
			++j;
		}
		j = 1;
		for(int i = 2; i<timetables.size(); i = i+2)
		{
			this.timetables[midpoint+j] = timetables.get(i);
			++j;
		}
		currentlySelected = midpoint;
		
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(currentlySelected-1 >= 0 && e.getX() < prevArrow.getWidth(TimeTablePane.this))
				{
					currentlySelected -= 1;
					TimeTablePane.this.repaint();
				}
				else if(currentlySelected+1 < timetables.size() && e.getX() > getWidth()-nextArrow.getWidth(TimeTablePane.this))
				{
					currentlySelected += 1;
					TimeTablePane.this.repaint();
				}
			}
		}
		);
	}
	
	public void paint(Graphics g)
	{
		g.setColor(GUIDefs.BACKDROP_COLOR);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		Image current = timetables[currentlySelected].generateImage();
		g.drawImage(current, (int) (.5F*this.getWidth()-.5*current.getWidth(this)), 0, this);
		g.setColor(Color.BLUE);
		g.drawRect((int) (.5F*this.getWidth()-.5*current.getWidth(this)), 0, current.getWidth(this), current.getHeight(this));
		if(currentlySelected-1 >= 0 )
		{
			Image previous = timetables[currentlySelected-1].generateImage();
			g.drawImage(previous, (int) (.5F*this.getWidth()-.5*current.getWidth(this))-previous.getWidth(this)-20, 0, this);
			g.drawImage(prevArrow, 0, (int) (.5F*this.getHeight()-.5F*prevArrow.getHeight(this)), this);
		}
		if(currentlySelected+1 < timetables.length)
		{
			Image next = timetables[currentlySelected+1].generateImage();
			g.drawImage(next, (int) (.5F*this.getWidth()-.5*current.getWidth(this))+current.getWidth(this)+20, 0, this);
			g.drawImage(nextArrow, this.getWidth()-nextArrow.getWidth(this), (int) (.5F*this.getHeight()-.5F*nextArrow.getHeight(this)), this);
		}

	}


}
