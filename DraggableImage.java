import java.awt.*;
import java.awt.image.*;

/*
  Represents a image that is draggable and often used 
  for game board pieces
*/
public class DraggableImage extends Draggable
{
    ImageObserver observer;
    Image image;
    
    public DraggableImage(ImageObserver o,Image i)
    {
	super();
	observer=o;
	image=i;
    }

    public void setImage(Image i)
    {
	image=i;
    }

    public int width()
    {
	return image.getWidth(observer);
    }
	
    public int height()
    {
	return image.getHeight(observer);
    }
	
    public boolean isTouching(Point p)
    {
	return (now.x<=p.x &&
		p.x<=now.x+image.getWidth(observer) &&
		now.y<=p.y &&
		p.y<=now.y+image.getHeight(observer));
    }

    public void draw(Graphics g,Point p)
    {
	g.drawImage(image, p.x,p.y,observer);
    }
}
