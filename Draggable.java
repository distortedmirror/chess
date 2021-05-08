import java.awt.*;

/*
  Represents a draggable item and is 
  extended to create DraggableImage
 */
public abstract class Draggable 
{
    Point now,then;
    boolean being_dragged;

    public abstract boolean isTouching(Point p);
    public abstract void draw(Graphics g, Point p);
    public abstract int height();
    public abstract int width();
    
    public Draggable()
    {
	being_dragged=false;
	now=new Point(0,0);
	then=new Point(0,0);
    }

    public int xpos()
    {
	return now.x;
    }

    public int ypos()
    {
	return now.y;
    }

    public void startDrag()
    {
	being_dragged=true;
    }
    
    public boolean inMidDrag() 
    {
	return being_dragged;
    }

    public void endDrag()
    {
	being_dragged=false;
    }
	
    public  void setPosition(Point p)
    {
	then.x=now.x;
	then.y=now.y;
	now.x=p.x;
	now.y=p.y;
    }

    public void paint(Graphics g)
    {
	draw(g,now);
    }
}
